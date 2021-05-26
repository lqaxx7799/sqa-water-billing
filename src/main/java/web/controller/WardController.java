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
import web.model.Ward;
import web.repos.DistrictRepository;
import web.repos.ProvinceRepository;
import web.repos.WardRepository;

@Controller
@RequestMapping("/ward")
public class WardController {
	@Autowired
	private WardRepository wardRepository;
	@Autowired
	private DistrictRepository districtRepository;
	
	@GetMapping(value = "/getByDistrictId", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getWardsByDistrictId(@Param("districtId") Integer districtId) {
		
		District district = districtRepository.findById(districtId).orElse(null);
		if (district == null) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		}
		
		List<Ward> wards = wardRepository.findByTblDistrict(district);
		return new ResponseEntity<>(wards, HttpStatus.OK);
	}
}
