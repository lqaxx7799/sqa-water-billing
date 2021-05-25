package web.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import web.dto.LogInDTO;
import web.model.Account;
import web.model.AddressType;
import web.model.District;
import web.model.Province;
import web.model.Ward;
import web.repos.AccountRepository;
import web.repos.AddressRepository;
import web.repos.AddressTypeRepository;
import web.repos.CustomerRepository;
import web.repos.DistrictRepository;
import web.repos.ProvinceRepository;
import web.repos.WardRepository;
import web.repos.WaterMeterRepository;
import web.util.CommonUtils;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
	@MockBean
    private AccountRepository accountRepository;
	@MockBean
	private CustomerRepository customerRepository;
	@MockBean
	private AddressRepository addressRepository;
	@MockBean
	private AddressTypeRepository addressTypeRepository;
	@MockBean
	private ProvinceRepository provinceRepository;
	@MockBean
	private DistrictRepository districtRepository;
	@MockBean
	private WardRepository wardRepository;
	@MockBean
	private WaterMeterRepository waterMeterRepository;

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void logIn_test_success() throws Exception {
    	Account account = new Account();
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        doReturn(true).when(accountRepository).existByEmailAndPassword(account.getEmail(), account.getPassword());
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());
        
        mockMvc.perform(post("/authentication/logIn")
        		.param("email", "test@gmail.com")
        		.param("password", "abcd1234"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void logIn_test_fail_missing_info() throws Exception {
    	Account account = new Account();
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        doReturn(true).when(accountRepository).existByEmailAndPassword(account.getEmail(), account.getPassword());
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());

        mockMvc.perform(post("/authentication/logIn")
        		.param("email", "")
        		.param("password", ""))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("logIn"))
        		.andExpect(model().attribute("errEmail", equalTo("Vui lòng nhập email")))
        		.andExpect(model().attribute("errPassword", equalTo("Vui lòng nhập mật khẩu")));
    }
    
    @Test
    public void logIn_test_fail_wrong_email() throws Exception {
    	Account account = new Account();
        account.setEmail("test@gmail.com");
        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
        account.setRole("EMPLOYEE");
        account.setCreatedAt(new Date());
        
        doReturn(false).when(accountRepository).existByEmailAndPassword(account.getEmail(), account.getPassword());
        doReturn(account).when(accountRepository).findOneByEmail(account.getEmail());

        mockMvc.perform(post("/authentication/logIn")
        		.param("email", "test1@gmail.com")
        		.param("password", "abcd1234"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("logIn"))
        		.andExpect(model().attribute("errEmail", equalTo("Sai email hoặc password")));
    }
    
    @Test
    public void registration_test_success() throws Exception {
//    	Account account = new Account();
//        account.setEmail("test@gmail.com");
//        account.setPassword(CommonUtils.generateSHA1("abcd1234"));
//        account.setRole("EMPLOYEE");
//        account.setCreatedAt(new Date());
//        
//        AddressType addressType = new AddressType();
//        addressType.setType("Hộ gia đình");
//        List<AddressType> addressTypes = new ArrayList<>();
//        addressTypes.add(addressType);
//        
//        Ward ward = new Ward();
//        ward.setWardName("Phường Nam Đồng");
//        List<Ward> wards = new ArrayList<>();
//        wards.add(ward);
//        
//        District district = new District();
//        district.setDistrictName("Quận Thanh Xuân");
//        List<District> districts = new ArrayList<>();
//        districts.add(district);
//        
//        Province province = new Province();
//        province.setProvinceName("Thành phố Hà Nội");
//        List<Province> provinces = new ArrayList<>();
//        provinces.add(province);
//        
//        doReturn(addressTypes).when(addressTypeRepository).findAll();
//        doReturn(wards).when(wardRepository).findAll();
//        doReturn(districts).when(districtRepository).findAll();
//        doReturn(provinces).when(provinceRepository).findAll();
        
        mockMvc.perform(post("/authentication/registration")
        		.param("email", "someone@gmail.com")
        		.param("password", "abcd1234")
        		.param("reenterPassword", "abcd1234")
        		.param("lastName", "Nguyen")
        		.param("firstName", "Van A")
        		.param("idNumber", "012345678")
        		.param("phoneNumber", "01169795952")
        		.param("gender", "male")
        		.param("dateOfBirth", "1999-01-01")
        		.param("addressTypeId", "1")
        		.param("street", "Nguyen Trai")
        		.param("wardId", "1")
        		.param("districtId", "1")
        		.param("provinceId", "1"))
        		
        		.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
    }
    
    @Test
    public void registration_test_failed_fields_empty() throws Exception {
        mockMvc.perform(post("/authentication/registration")
        		.param("email", "")
        		.param("firstName", "")
        		.param("lastName", "")
        		.param("password", "")
        		.param("reenterPassword", "")
        		.param("idNumber", "")
        		.param("phoneNumber", "")
        		.param("dateOfBirth", "")
        		.param("street", ""))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("registration"))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errEmail", "Email không được để trống")))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errFirstName", "Tên không được để trống")))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errLastName", "Họ không được để trống")))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errPassword", "Mật khẩu không được để trống")))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errReenterPassword", "Vui lòng nhập lại mật khẩu")))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errIdNumber", "CMT/CCCD không được để trống")))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errPhoneNumber", "Số điện thoại không được để trống")))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errDateOfBirth", "Ngày sinh không được để trống")))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errStreet", "Địa chỉ không được để trống")));
    }
    
    @Test
    public void registration_test_failed_email_wrong_format() throws Exception {
        mockMvc.perform(post("/authentication/registration")
        		.param("email", "not an email"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("registration"))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errEmail", "Định dạng email không hợp lệ")));
    }
    
    @Test
    public void registration_test_failed_email_existed() throws Exception {
    	Account account = new Account();
    	account.setEmail("test@gmail.com");
    	account.setPassword(CommonUtils.generateSHA1("abcd1234"));
    	account.setRole("EMPLOYEE");
    	account.setCreatedAt(new Date());
    	doReturn(account).when(accountRepository).findOneByEmail("someone@gmail.com");
    	
        mockMvc.perform(post("/authentication/registration")
        		.param("email", "someone@gmail.com"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("registration"))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errEmail", "Email đã tồn tại")));
    }
    
    @Test
    public void registration_test_failed_password_mismatched() throws Exception {
        mockMvc.perform(post("/authentication/registration")
        		.param("pasword", "abcd1234")
        		.param("reenterPassword", "abcd1235"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("registration"))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errReenterPassword", "Mật khẩu không khớp")));
    }
    
    
    @Test
    public void registration_test_failed_id_number_wrong_format() throws Exception {
        mockMvc.perform(post("/authentication/registration")
        		.param("idNumber", "123456"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("registration"))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errIdNumber", "CMT/CCCD có 9 hoặc 12 số")));
    }
    
    
    @Test
    public void registration_test_failed_date_of_birth_future() throws Exception {
        mockMvc.perform(post("/authentication/registration")
        		.param("dateOfBirth", "2022-10-10"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(view().name("registration"))
        		.andExpect(model().attribute("errors", Matchers.hasEntry("errDateOfBirth", "Ngày sinh không được trong tương lai")));
    }
    
    
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    

}
