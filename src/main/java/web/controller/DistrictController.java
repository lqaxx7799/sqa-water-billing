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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	public ResponseEntity<?> getDistrictsByProvinceId(@Param("provinceId") Integer provinceId) {
		if (provinceId == null) {
			Map<String, String> errors = new LinkedHashMap<>();
			errors.put("message", "provinceId cannot be empty");
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		Province province = provinceRepository.findById(provinceId).orElse(null);
		if (province == null) {
			return new ResponseEntity<>(new ArrayList<District>(), HttpStatus.OK);
		}
		
		List<District> districts = districtRepository.findByTblProvince(province);
		return new ResponseEntity<>(districts, HttpStatus.OK);
	}
}
