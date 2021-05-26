package web.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import web.model.District;
import web.model.Province;
import web.repos.DistrictRepository;
import web.repos.ProvinceRepository;

@WebMvcTest(DistrictController.class)
@AutoConfigureMockMvc
public class DistrictControllerTest {
	@MockBean
	private ProvinceRepository provinceRepository;
	@MockBean
	private DistrictRepository districtRepository;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void getDistrictsByProvinceId_test_1() throws Exception {
		Province province1 = new Province();
		province1.setId(1);
		province1.setProvinceName("Province 1");
		
		District district1 = new District();
		district1.setId(1);
		district1.setDistrictName("District 1");
		district1.setTblProvince(province1);
		District district2 = new District();
		district2.setId(2);
		district2.setDistrictName("District 2");
		district2.setTblProvince(province1);
		List<District> districts = Arrays.asList(district1, district2);
		
		doReturn(Optional.of(province1)).when(provinceRepository).findById(1);
		doReturn(districts).when(districtRepository).findByTblProvince(province1);
		
		mockMvc.perform(get("/district/getByProvinceId")
        		.param("provinceId", "1"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
        		.andExpect(jsonPath("$[0].id", is(1)))
        		.andExpect(jsonPath("$[0].districtName", is("District 1")));
	}
	
	@Test
	public void getDistrictsByProvinceId_test_2() throws Exception {
		// empty data
		mockMvc.perform(get("/district/getByProvinceId")
        		.param("provinceId", "1"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(jsonPath("$", Matchers.hasSize(0)));
	}
	
	@Test
	public void getDistrictsByProvinceId_test_3() throws Exception {
		// no param
		mockMvc.perform(get("/district/getByProvinceId"))
        		
        		.andExpect(status().is4xxClientError())
        		.andExpect(jsonPath("$", Matchers.hasEntry("message", "provinceId cannot be empty")));
	}
	
	@Test
	public void getDistrictsByProvinceId_test_4() throws Exception {
		// param not a number
		mockMvc.perform(get("/district/getByProvinceId")
				.param("provinceId", "not number"))
        		
        		.andExpect(status().is4xxClientError());
	}
}
