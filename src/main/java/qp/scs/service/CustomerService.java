package qp.scs.service;

import static com.google.common.base.Objects.equal;

import org.springframework.stereotype.Service;

import qp.scs.dto.request.LoginRequestDTO;
import qp.scs.dto.request.NewCustomerRequestDTO;
import qp.scs.dto.response.LoginResponseDTO;
import qp.scs.exception.LoginFailedException;
import qp.scs.exception.SessionExpiredException;
import qp.scs.model.Customer;
import qp.scs.model.SessionToken;
import qp.scs.model.User;
import qp.scs.model.api.Entity;
import qp.scs.repository.CustomerRepository;
import qp.scs.repository.UserRepository;
import qp.scs.security.TokenUser;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.Period;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import qp.scs.security.TokenHandler;

@Service
@Transactional
public class CustomerService extends BaseService {

	@Autowired
	private CustomerRepository customerRepository;



	/**
	 * Saves the specified customer application
	 * 
	 * @param customerApplication
	 */
	public void registerCustomer(NewCustomerRequestDTO request) {
	
		Customer customer=new Customer();
		customer.contactPerson=request.contactPerson;
		customer.companyName=request.companyName;
		customer.phone=request.phone;
		customer.email =request.email;
		customer.id=request.customerId;
		customer.address=request.address;
		customer.buidingFloorUnit=request.buidingFloorUnit;
		customer.postcode=request.postcode;
		
		save(customer);
		
	}

	public List<Customer> getCustomers() {
		
	
		return  customerRepository.getCustomers();		
	}

}
