package web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import web.dto.WaterReadingUpdateDTO;
import web.model.Account;
import web.model.Address;
import web.model.Customer;
import web.model.Employee;
import web.model.Ward;
import web.model.WaterBill;
import web.model.WaterMeter;
import web.repos.AccountRepository;
import web.repos.AddressRepository;
import web.repos.EmployeeRepository;
import web.repos.WardRepository;
import web.repos.WaterMeterReadingRepository;
import web.repos.WaterMeterRepository;
import web.util.CommonUtils;

@WebMvcTest(WaterMeterReadingController.class)
@AutoConfigureMockMvc
public class WaterMeterReadingControllerTest {

	@MockBean
	private WaterMeterRepository waterMeterRepository;
	@MockBean
	private WaterMeterReadingRepository waterMeterReadingRepository;
	@MockBean
	private EmployeeRepository employeeRepository;
	@MockBean
	private WardRepository wardRepository;
	@MockBean
	private AddressRepository addressRepository;
	@MockBean
	private AccountRepository accountRepository;
	
	@Autowired
    private MockMvc mockMvc;
	    
	@Test
	public void read_test_1() throws Exception {
		// success, not selected area
		Ward ward1 = new Ward();
		ward1.setId(1);
		ward1.setWardName("Phường Nam Đồng");
		
		Ward ward2 = new Ward();
		ward2.setId(2);
		ward2.setWardName("Phường Thành Công");
		
		List<Ward> wards = Arrays.asList(ward1, ward2);
		
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        Employee employee = new Employee();
        employee.setId(1);
        employee.setTblAccount(account);
        employee.setTblWards(wards);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(employee).when(employeeRepository).findOneByAccountId(account.getId());
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(get("/reading/list")
    			.sessionAttrs(sessionattr))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("waterReadingList"))
        		.andExpect(model().attribute("assignedAreas", Matchers.hasSize(2)))
        		.andExpect(model().attribute("assignedAreas", Matchers.hasItem(
        				Matchers.allOf(
                                Matchers.hasProperty("id", is(1)),
                                Matchers.hasProperty("wardName", is("Phường Nam Đồng"))
                        ))));
	}
	
	@Test
	public void read_test_2() throws Exception {
		// success, selected area
		Ward ward1 = new Ward();
		ward1.setId(1);
		ward1.setWardName("Phường Nam Đồng");
		
		Ward ward2 = new Ward();
		ward2.setId(2);
		ward2.setWardName("Phường Thành Công");
		
		List<Ward> wards = Arrays.asList(ward1, ward2);
		
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        Employee employee = new Employee();
        employee.setId(1);
        employee.setTblAccount(account);
        employee.setTblWards(wards);
        
        WaterMeter waterMeter1 = new WaterMeter();
        waterMeter1.setIsActive(true);

        Address address1 = new Address();
        address1.setTblWaterMeters(Arrays.asList(waterMeter1));
        
        Address address2 = new Address();
        address2.setTblWaterMeters(Arrays.asList(waterMeter1));
        
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(employee).when(employeeRepository).findOneByAccountId(account.getId());
        doReturn(Optional.of(ward1)).when(wardRepository).findById(ward1.getId());
        doReturn(Arrays.asList(address1, address2)).when(addressRepository).findByTblWard(ward1);
        
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(get("/reading/list")
    			.sessionAttrs(sessionattr)
    			.param("wardId", "1"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("waterReadingList"))
        		.andExpect(model().attribute("assignedAreas", Matchers.hasSize(2)))
        		.andExpect(model().attribute("assignedAreas", Matchers.hasItem(
        				Matchers.allOf(
                                Matchers.hasProperty("id", is(1)),
                                Matchers.hasProperty("wardName", is("Phường Nam Đồng"))
                        ))))
        		.andExpect(model().attribute("readings", Matchers.hasSize(2)));
	}
	
	@Test
	public void read_test_3() throws Exception {
		// failed, not logged in
    	mockMvc.perform(get("/reading/list"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
	}
	
	@Test
	public void read_test_4() throws Exception {
		// failed, logged in account that is not employee
		Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("CUSTOMER");
        account.setCreatedAt(new Date());
         
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	mockMvc.perform(get("/reading/list")
    			.sessionAttrs(sessionattr))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
	}
	
	@Test
	public void update_test_1() throws Exception {
		// success, on new data
		Ward ward1 = new Ward();
		ward1.setId(1);
		ward1.setWardName("Phường Nam Đồng");
		
		Ward ward2 = new Ward();
		ward2.setId(2);
		ward2.setWardName("Phường Thành Công");
		
		List<Ward> wards = Arrays.asList(ward1, ward2);
		
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        Employee employee = new Employee();
        employee.setId(1);
        employee.setTblAccount(account);
        employee.setTblWards(wards);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(employee).when(employeeRepository).findOneByAccountId(account.getId());
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	WaterReadingUpdateDTO dto = new WaterReadingUpdateDTO();
    	dto.setReadingValue(10);
    	dto.setWaterMeterId(1);
    	List<WaterReadingUpdateDTO> waterReadingUpdateDTOs = Arrays.asList(dto);
    	
    	ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(waterReadingUpdateDTOs);

    	mockMvc.perform(post("/reading/update")
    			.sessionAttrs(sessionattr)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(requestJson))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(jsonPath("$.result", is("success")));
	}
	
	@Test
	public void update_test_2() throws Exception {
		// success, on old data
		Ward ward1 = new Ward();
		ward1.setId(1);
		ward1.setWardName("Phường Nam Đồng");
		
		Ward ward2 = new Ward();
		ward2.setId(2);
		ward2.setWardName("Phường Thành Công");
		
		List<Ward> wards = Arrays.asList(ward1, ward2);
		
    	Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        Employee employee = new Employee();
        employee.setId(1);
        employee.setTblAccount(account);
        employee.setTblWards(wards);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(employee).when(employeeRepository).findOneByAccountId(account.getId());
    	
    	HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
    	
    	WaterReadingUpdateDTO dto = new WaterReadingUpdateDTO();
    	dto.setReadingValue(10);
    	dto.setWaterMeterId(1);
    	List<WaterReadingUpdateDTO> waterReadingUpdateDTOs = Arrays.asList(dto);
    	
    	ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(waterReadingUpdateDTOs);

    	mockMvc.perform(post("/reading/update")
    			.sessionAttrs(sessionattr)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(requestJson))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(jsonPath("$.result", is("success")));
	}
	
	@Test
	public void update_test_3() throws Exception {
		// failed, not logged in
    	mockMvc.perform(post("/reading/update"))
        		
        		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void update_test_4() throws Exception {
		// failed, logged in but not employee
		Account account = new Account();
    	account.setId(1);
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        Employee employee = new Employee();
        employee.setId(1);
        employee.setTblAccount(account);
        
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        doReturn(employee).when(employeeRepository).findOneByAccountId(account.getId());
        
        HashMap<String, Object> sessionattr = new HashMap<String, Object>();
    	sessionattr.put("email", "test@gmail.com");
        
    	mockMvc.perform(post("/reading/update")
    			.sessionAttrs(sessionattr))
        		
        		.andExpect(status().is4xxClientError());
	}
}
