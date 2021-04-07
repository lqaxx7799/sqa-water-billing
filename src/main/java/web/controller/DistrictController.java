package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import web.model.District;
import web.model.Province;
import web.repos.DistrictRepository;
import web.repos.ProvinceRepository;

@Controller
@RequestMapping("/district")
public class DistrictController {
	@Autowired
	private ProvinceRepository provinceRepository;
	@Autowired
	private DistrictRepository districtRepository;
	
	@GetMapping(value = "/getByProvinceId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<District>> getDistrictsByProvinceId(@Param("provinceId") String provinceId) {
		int provinceIdInt = Integer.parseInt(provinceId);
		Province province = provinceRepository.findById(provinceIdInt).orElse(null);
		if (province == null) {
			return new ResponseEntity<>(new ArrayList<District>(), HttpStatus.OK);
		}
		
		List<District> districts = districtRepository.findByTblProvince(province);
		return new ResponseEntity<>(districts, HttpStatus.OK);
	}
}
