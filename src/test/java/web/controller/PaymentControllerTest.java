package web.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

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
import web.repos.PaymentRepository;
import web.repos.PricingRepository;
import web.repos.WaterBillRepository;
import web.util.CommonUtils;

@WebMvcTest(PaymentController.class)
@AutoConfigureMockMvc
public class PaymentControllerTest {
	@MockBean
	public WaterBillRepository waterBillRepository;
	@MockBean
	public PaymentRepository paymentRepository;
	@MockBean
	public AccountRepository accountRepository;
	@MockBean
	public CustomerRepository customerRepository;
	@MockBean
	public PricingRepository pricingRepository;
	
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void paymentList_test_1() throws Exception {
    	// success
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setIsVerified(true);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	doReturn(Arrays.asList(new WaterBill(), new WaterBill())).when(waterBillRepository).findByCustomerId(customer.getId());
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(get("/payment/list")
    			.sessionAttrs(sessionattr))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("paymentList"))
        		.andExpect(model().attribute("bills", Matchers.hasSize(2)));
    }
    
    @Test
    public void paymentList_test_2() throws Exception {
    	// not logged in
    	mockMvc.perform(get("/payment/list"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void paymentList_test_3() throws Exception {
    	// logged in account that is not customer
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
        
    	mockMvc.perform(get("/payment/list")
    			.sessionAttrs(sessionattr))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void paymentList_test_4() throws Exception {
    	// logged in account that is customer but not verified
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setIsVerified(false);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
        
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
        
    	mockMvc.perform(get("/payment/list")
    			.sessionAttrs(sessionattr))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/account/detail"));
    }
}
