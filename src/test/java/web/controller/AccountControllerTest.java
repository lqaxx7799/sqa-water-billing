package web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import web.model.Account;
import web.model.Customer;
import web.model.WaterBill;
import web.repos.AccountRepository;
import web.repos.CustomerRepository;
import web.util.CommonUtils;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc
public class AccountControllerTest {

	@MockBean
	public AccountRepository accountRepository;
	@MockBean
	public CustomerRepository customerRepository;

	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void detail_test_1() throws Exception {
		// success
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("James");
        customer.setLastName("Bond");
        customer.setIsVerified(true);
        customer.setTblAccount(account);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(get("/account/detail")
    			.sessionAttrs(sessionattr))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("accountDetail"))
        		.andExpect(model().attribute("customer", Matchers.hasProperty("firstName", is("James"))));
	}
	
	@Test
    public void detail_test_2() throws Exception {
    	// not logged in
    	mockMvc.perform(get("/account/detail"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void detail_test_3() throws Exception {
    	// logged in account that is not customer
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
        
    	mockMvc.perform(get("/account/detail")
    			.sessionAttrs(sessionattr))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void resendEmail_test_1() throws Exception {
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("James");
        customer.setLastName("Bond");
        customer.setIsVerified(true);
        customer.setTblAccount(account);
        
        doReturn(Optional.of(customer)).when(customerRepository).findById(customer.getId());
    	
    	mockMvc.perform(get("/account/resendEmail")
    			.param("id", "1"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(jsonPath("$.success", is(true)));
    }
    
    @Test
    public void resendEmail_test_2() throws Exception {
    	// not logged in
    	mockMvc.perform(get("/account/resendEmail")
    			.param("id", "1"))
        		
        		.andExpect(status().is4xxClientError())
        		.andExpect(jsonPath("$.success", is(false)));
    }
}
