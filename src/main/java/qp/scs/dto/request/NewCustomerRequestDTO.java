package qp.scs.dto.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
public class NewCustomerRequestDTO {

	@NotBlank(message ="contactPerson can't be blank")
	public String contactPerson;
	
	@NotBlank(message ="companyName can't be blank")
	public String companyName;
	

	@NotNull(message = "phone can't be null")
	public Integer phone;
	
	public String email;
	
	@NotBlank(message ="customerId can't be blank")
	public String customerId;
	
	
	@NotBlank(message ="address can't be blank")
	public String address;
	
	@NotNull(message = "postcode can't be null")
	public Integer postcode;
	

	public String buidingFloorUnit;
	
	
}
