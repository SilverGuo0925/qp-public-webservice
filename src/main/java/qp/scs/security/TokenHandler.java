package qp.scs.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.DatatypeConverter;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
public class TokenHandler {
	
	private static final String HMAC_ALGO = "HmacSHA256";

	private static final String SEPARATOR = ".";
	
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

}
