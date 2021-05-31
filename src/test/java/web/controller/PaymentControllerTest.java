package web.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import web.mail.EmailService;
import web.model.Account;
import web.model.Address;
import web.model.AddressType;
import web.model.Customer;
import web.model.Payment;
import web.model.Pricing;
import web.model.WaterBill;
import web.model.WaterMeter;
import web.model.WaterMeterReading;
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
    
    @Test
    public void paymentDetail_test_1() throws Exception {
    	// success, has payment already
    	Date currentDate = new Date();
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setIsVerified(true);
        customer.setTblAccount(account);
        
        AddressType addressType = new AddressType();
        addressType.setId(1);
        
        Address address = new Address();
        address.setId(1);
        address.setTblCustomer(customer);
        address.setTblAddressType(addressType);
        
        WaterMeter waterMeter = new WaterMeter();
        waterMeter.setId(1);
        waterMeter.setTblAddress(address);
        
        WaterMeterReading waterMeterReading = new WaterMeterReading();
        waterMeterReading.setId(1);
        waterMeterReading.setTblWaterMeter(waterMeter);
        
        WaterBill waterBill = new WaterBill();
        waterBill.setId(1);
        waterBill.setTblWaterMeterReading(waterMeterReading);
        waterBill.setAmount(100);
        waterBill.setCreatedAt(currentDate);
        waterBill.setIsPaid(true);
        
        Payment payment = new Payment();
        payment.setId(1);
        payment.setTblWaterBill(waterBill);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	doReturn(Optional.of(waterBill)).when(waterBillRepository).findById(waterBill.getId());
    	doReturn(payment).when(paymentRepository).findOneByWaterBill(waterBill.getId());
    	doReturn(new Pricing()).when(pricingRepository).findOneByTblAddressTypeAndPeriod(addressType.getId(), currentDate);
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(get("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("paymentDetail"))
        		.andExpect(model().attribute("bill", Matchers.hasProperty("totalPrice", is(100f))))
        		.andExpect(model().attribute("payment", Matchers.hasProperty("id", is(1))));
    }
    
    @Test
    public void paymentDetail_test_2() throws Exception {
    	// failed, not logged in
    	mockMvc.perform(get("/payment/detail")
    			.param("id", "1"))
		
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void paymentDetail_test_3() throws Exception {
    	// logged in account that is not customer
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
        
    	mockMvc.perform(get("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void paymentDetail_test_4() throws Exception {
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
        
    	mockMvc.perform(get("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/account/detail"));
    }
    
    @Test
    public void paymentDetail_test_5() throws Exception {
    	// failed, no water bill found
    	Date currentDate = new Date();
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setIsVerified(true);
        customer.setTblAccount(account);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	doReturn(Optional.empty()).when(waterBillRepository).findById(1);
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(get("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void paymentDetail_test_6() throws Exception {
    	// success, has no payment yet
    	Date currentDate = new Date();
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setIsVerified(true);
        customer.setTblAccount(account);
        
        AddressType addressType = new AddressType();
        addressType.setId(1);
        
        Address address = new Address();
        address.setId(1);
        address.setTblCustomer(customer);
        address.setTblAddressType(addressType);
        
        WaterMeter waterMeter = new WaterMeter();
        waterMeter.setId(1);
        waterMeter.setTblAddress(address);
        
        WaterMeterReading waterMeterReading = new WaterMeterReading();
        waterMeterReading.setId(1);
        waterMeterReading.setTblWaterMeter(waterMeter);
        
        WaterBill waterBill = new WaterBill();
        waterBill.setId(1);
        waterBill.setTblWaterMeterReading(waterMeterReading);
        waterBill.setAmount(100);
        waterBill.setCreatedAt(currentDate);
        waterBill.setIsPaid(false);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	doReturn(Optional.of(waterBill)).when(waterBillRepository).findById(waterBill.getId());
    	doReturn(new Pricing()).when(pricingRepository).findOneByTblAddressTypeAndPeriod(addressType.getId(), currentDate);
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(get("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("paymentDetail"))
        		.andExpect(model().attribute("bill", Matchers.hasProperty("totalPrice", is(100f))))
        		.andExpect(model().attribute("payment", Matchers.hasProperty("id", is(0))));
    }
    
    @Test
    public void submitPayment_test_1() throws Exception {
    	// success
    	Date currentDate = new Date();
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setIsVerified(true);
        customer.setTblAccount(account);
        
        AddressType addressType = new AddressType();
        addressType.setId(1);
        
        Address address = new Address();
        address.setId(1);
        address.setTblCustomer(customer);
        address.setTblAddressType(addressType);
        
        WaterMeter waterMeter = new WaterMeter();
        waterMeter.setId(1);
        waterMeter.setTblAddress(address);
        
        WaterMeterReading waterMeterReading = new WaterMeterReading();
        waterMeterReading.setId(1);
        waterMeterReading.setTblWaterMeter(waterMeter);
        
        WaterBill waterBill = new WaterBill();
        waterBill.setId(1);
        waterBill.setTblWaterMeterReading(waterMeterReading);
        waterBill.setAmount(100);
        waterBill.setCreatedAt(currentDate);
        waterBill.setIsPaid(true);
        
        Payment payment = new Payment();
        payment.setId(1);
        payment.setTblWaterBill(waterBill);
        payment.setPaymentCode("159026");
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	doReturn(Optional.of(waterBill)).when(waterBillRepository).findById(waterBill.getId());
//    	doReturn(payment).when(paymentRepository).findOneByWaterBill(waterBill.getId());
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(post("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1")
    			.flashAttr("payment", payment))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("paymentDetail"))
        		.andExpect(model().attribute("bill", Matchers.hasProperty("totalPrice", is(100f))));
    }
    
    @Test
    public void submitPayment_test_2() throws Exception {
    	// failed, not logged in
    	mockMvc.perform(post("/payment/detail")
    			.param("id", "1"))
		
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void submitPayment_test_3() throws Exception {
    	// logged in account that is not customer
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
        
    	mockMvc.perform(post("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void submitPayment_test_4() throws Exception {
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
        
    	mockMvc.perform(post("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/account/detail"));
    }
    
    @Test
    public void submitPayment_test_5() throws Exception {
    	// failed, no water bill found
    	Date currentDate = new Date();
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setIsVerified(true);
        customer.setTblAccount(account);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	doReturn(Optional.empty()).when(waterBillRepository).findById(1);
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(post("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void submitPayment_test_6() throws Exception {
    	// failed, no payment code
    	Date currentDate = new Date();
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setIsVerified(true);
        customer.setTblAccount(account);
        
        AddressType addressType = new AddressType();
        addressType.setId(1);
        
        Address address = new Address();
        address.setId(1);
        address.setTblCustomer(customer);
        address.setTblAddressType(addressType);
        
        WaterMeter waterMeter = new WaterMeter();
        waterMeter.setId(1);
        waterMeter.setTblAddress(address);
        
        WaterMeterReading waterMeterReading = new WaterMeterReading();
        waterMeterReading.setId(1);
        waterMeterReading.setTblWaterMeter(waterMeter);
        
        WaterBill waterBill = new WaterBill();
        waterBill.setId(1);
        waterBill.setTblWaterMeterReading(waterMeterReading);
        waterBill.setAmount(100);
        waterBill.setCreatedAt(currentDate);
        waterBill.setIsPaid(true);
        
        Payment payment = new Payment();
        payment.setId(1);
        payment.setTblWaterBill(waterBill);
        payment.setPaymentCode("");
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	doReturn(Optional.of(waterBill)).when(waterBillRepository).findById(waterBill.getId());
//    	doReturn(payment).when(paymentRepository).findOneByWaterBill(waterBill.getId());
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(post("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1")
    			.flashAttr("payment", payment))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("paymentDetail"))
        		.andExpect(model().attribute("bill", Matchers.hasProperty("totalPrice", is(100f))))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errPaymentCode", "Vui lòng nhập mã tài khoản của bạn")));
    }
    
    @Test
    public void submitPayment_test_7() throws Exception {
    	// failed, no payment code
    	Date currentDate = new Date();
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
        
        Customer customer = new Customer();
        customer.setId(1);
        customer.setIsVerified(true);
        customer.setTblAccount(account);
        
        AddressType addressType = new AddressType();
        addressType.setId(1);
        
        Address address = new Address();
        address.setId(1);
        address.setTblCustomer(customer);
        address.setTblAddressType(addressType);
        
        WaterMeter waterMeter = new WaterMeter();
        waterMeter.setId(1);
        waterMeter.setTblAddress(address);
        
        WaterMeterReading waterMeterReading = new WaterMeterReading();
        waterMeterReading.setId(1);
        waterMeterReading.setTblWaterMeter(waterMeter);
        
        WaterBill waterBill = new WaterBill();
        waterBill.setId(1);
        waterBill.setTblWaterMeterReading(waterMeterReading);
        waterBill.setAmount(100);
        waterBill.setCreatedAt(currentDate);
        waterBill.setIsPaid(true);
        
        Payment payment = new Payment();
        payment.setId(1);
        payment.setTblWaterBill(waterBill);
        payment.setPaymentCode("abcd");
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	doReturn(Optional.of(waterBill)).when(waterBillRepository).findById(waterBill.getId());
//    	doReturn(payment).when(paymentRepository).findOneByWaterBill(waterBill.getId());
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(post("/payment/detail")
    			.sessionAttrs(sessionattr)
    			.param("id", "1")
    			.flashAttr("payment", payment))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("paymentDetail"))
        		.andExpect(model().attribute("bill", Matchers.hasProperty("totalPrice", is(100f))))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errPaymentCode", "Mã tài khoản không hợp lệ")));
    }
    
    
    @Test
    public void resendOTP_test_1() throws Exception {
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
        customer.setTblAccount(account);
        
        Address address = new Address();
        address.setId(1);
        address.setTblCustomer(customer);
        
        WaterMeter waterMeter = new WaterMeter();
        waterMeter.setId(1);
        waterMeter.setTblAddress(address);
        
        WaterMeterReading waterMeterReading = new WaterMeterReading();
        waterMeterReading.setId(1);
        waterMeterReading.setTblWaterMeter(waterMeter);
        
        WaterBill waterBill = new WaterBill();
        waterBill.setId(1);
        waterBill.setTblWaterMeterReading(waterMeterReading);
        waterBill.setAmount(100);
        waterBill.setIsPaid(true);
        
        Payment payment = new Payment();
        payment.setId(1);
        payment.setTblWaterBill(waterBill);
        payment.setPaymentCode("abcd");
        
        doReturn(customer).when(customerRepository).findOneByAccountId(account.getId());
    	doReturn(Optional.of(payment)).when(paymentRepository).findById(waterBill.getId());
    	
    	mockMvc.perform(get("/payment/resendOTP")
    			.param("id", "1"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(jsonPath("$.success", is(true)));
    }
    
    @Test
    public void resendOTP_test_2() throws Exception {
    	// failed, found no bill
    	mockMvc.perform(get("/payment/resendOTP")
    			.param("id", "1"))
        		
        		.andExpect(status().is4xxClientError())
        		.andExpect(jsonPath("$.success", is(false)));
    }
    
    @Test
    public void resendOTP_test_3() throws Exception {
    	// failed, found no customer
    	Address address = new Address();
        address.setId(1);
        address.setTblCustomer(null);
        
        WaterMeter waterMeter = new WaterMeter();
        waterMeter.setId(1);
        waterMeter.setTblAddress(address);
        
        WaterMeterReading waterMeterReading = new WaterMeterReading();
        waterMeterReading.setId(1);
        waterMeterReading.setTblWaterMeter(waterMeter);
        
        WaterBill waterBill = new WaterBill();
        waterBill.setId(1);
        waterBill.setTblWaterMeterReading(waterMeterReading);

    	Payment payment = new Payment();
    	payment.setId(1);
    	payment.setTblWaterBill(waterBill);

      	doReturn(Optional.of(payment)).when(paymentRepository).findById(1);
    	mockMvc.perform(get("/payment/resendOTP")
    			.param("id", "1"))
        		
        		.andExpect(status().is4xxClientError())
        		.andExpect(jsonPath("$.success", is(false)));
    }
    
    @Test
    public void finishPayment_test_1() throws Exception {
    	// success
        Payment payment = new Payment();
        payment.setId(1);
        payment.setOtpCode("153524");
        
    	doReturn(Optional.of(payment)).when(paymentRepository).findById(payment.getId());
    	
    	mockMvc.perform(post("/payment/finishPayment")
    			.param("id", "1")
    			.param("otpCode", "153524"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(jsonPath("$.success", is(true)));
    }
    
    @Test
    public void finishPayment_test_2() throws Exception {
    	// failed, no payment found
        
    	doReturn(Optional.empty()).when(paymentRepository).findById(1);
    	
    	mockMvc.perform(post("/payment/finishPayment")
    			.param("id", "1")
    			.param("otpCode", "153524"))
        		
        		.andExpect(status().is4xxClientError())
        		.andExpect(jsonPath("$.success", is(false)))
        		.andExpect(jsonPath("$.error", is("Có lỗi xảy ra trong quá trình thanh toán.")));
    }
    
    @Test
    public void finishPayment_test_3() throws Exception {
    	// failed, wrong otp code
    	Payment payment = new Payment();
    	payment.setId(1);
    	payment.setOtpCode("153524");
         
     	doReturn(Optional.of(payment)).when(paymentRepository).findById(payment.getId());
    	
    	mockMvc.perform(post("/payment/finishPayment")
    			.param("id", "1")
    			.param("otpCode", "999999"))
        		
        		.andExpect(status().is4xxClientError())
        		.andExpect(jsonPath("$.success", is(false)))
        		.andExpect(jsonPath("$.error", is("Mã OTP không hợp lệ.")));
    }
}
