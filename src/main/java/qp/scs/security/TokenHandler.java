package qp.scs.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.DatatypeConverter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

@Configuration
public class TokenHandler {
	
	private static final String HMAC_ALGO = "HmacSHA256";

	private static final String SEPARATOR = ".";
	
	private static final String SEPARATOR_SPLITTER = "\\.";

	private final Mac hmac;

	@Autowired
	private ObjectMapper serializingObjectMapper;
	
	public TokenHandler(@Value("${token.secret}") String secret) {
		try {
			byte[] secretKey = DatatypeConverter.parseBase64Binary(secret);
			hmac = Mac.getInstance(HMAC_ALGO);
			hmac.init(new SecretKeySpec(secretKey, HMAC_ALGO));
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new IllegalStateException("failed to initialize HMAC: " + e.getMessage(), e);
		}
	}
	public String createTokenForUser(TokenUser user) {
		byte[] userBytes = toJSON(user);
		byte[] hash = createHmac(userBytes);
		final StringBuilder sb = new StringBuilder(170);
		sb.append(toBase64(userBytes));
		sb.append(SEPARATOR);
		sb.append(toBase64(hash));
		return sb.toString();
	}
	
	private byte[] toJSON(TokenUser user) {
		try {
			return serializingObjectMapper.writeValueAsBytes(user);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	// synchronized to guard internal hmac object
	private synchronized byte[] createHmac(byte[] content) {
		return hmac.doFinal(content);
	}
	
	private String toBase64(byte[] content) {
		return DatatypeConverter.printBase64Binary(content);
	}

	private byte[] fromBase64(String content) {
		return DatatypeConverter.parseBase64Binary(content);
	}

	public TokenUser parseUserFromToken(String token) {
		final String[] parts = token.split(SEPARATOR_SPLITTER);
		if (parts.length == 2 && parts[0].length() > 0 && parts[1].length() > 0) {
			try {
				final byte[] userBytes = fromBase64(parts[0]);
				final byte[] hash = fromBase64(parts[1]);

				boolean validHash = Arrays.equals(createHmac(userBytes), hash);
				if (validHash) {
					final TokenUser user = fromJSON(userBytes);

					LocalDateTime expiryDate = Instant.ofEpochMilli(user.expires)
							.atZone(ZoneId.systemDefault()).toLocalDateTime();

					if (LocalDateTime.now().isBefore(expiryDate)) {
						return user;
					}
				}
			} catch (IllegalArgumentException e) {
				// log tempering attempt here
			}
		}
		return null;
	}
	private TokenUser fromJSON(final byte[] userBytes) {
		try {
			return serializingObjectMapper.readValue(new ByteArrayInputStream(userBytes),
					TokenUser.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
