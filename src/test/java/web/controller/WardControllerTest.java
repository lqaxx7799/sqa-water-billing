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
import web.model.Ward;
import web.repos.DistrictRepository;
import web.repos.ProvinceRepository;
import web.repos.WardRepository;

@WebMvcTest(WardController.class)
@AutoConfigureMockMvc
public class WardControllerTest {
	@MockBean
	private WardRepository wardRepository;
	@MockBean
	private DistrictRepository districtRepository;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void getWardsByDistrictId_test_1() throws Exception {
		District district1 = new District();
		district1.setId(1);
		district1.setDistrictName("District 1");

		Ward ward1 = new Ward();
		ward1.setId(1);
		ward1.setWardName("Ward 1");
		ward1.setTblDistrict(district1);
		Ward ward2 = new Ward();
		ward2.setId(2);
		ward2.setWardName("Ward 1");
		ward2.setTblDistrict(district1);
		List<Ward> wards = Arrays.asList(ward1, ward2);
		
		doReturn(Optional.of(district1)).when(districtRepository).findById(1);
		doReturn(wards).when(wardRepository).findByTblDistrict(district1);
		
		mockMvc.perform(get("/ward/getByDistrictId")
        		.param("districtId", "1"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
        		.andExpect(jsonPath("$[0].id", is(1)))
        		.andExpect(jsonPath("$[0].wardName", is("Ward 1")));
	}
	
	@Test
	public void getWardsByDistrictId_test_2() throws Exception {
		// empty data
		mockMvc.perform(get("/ward/getByDistrictId")
        		.param("districtId", "1"))
        		
        		.andExpect(status().is2xxSuccessful())
        		.andExpect(jsonPath("$", Matchers.hasSize(0)));
	}
	
	@Test
	public void getWardsByDistrictId_test_3() throws Exception {
		// no param
		mockMvc.perform(get("/ward/getByDistrictId"))
        		
        		.andExpect(status().is4xxClientError())
        		.andExpect(jsonPath("$", Matchers.hasEntry("message", "districtId cannot be empty")));
	}
	
	@Test
	public void getWardsByDistrictId_test_4() throws Exception {
		// param not a number
		mockMvc.perform(get("/ward/getByDistrictId")
				.param("districtId", "not number"))
        		
        		.andExpect(status().is4xxClientError());
	}
}
