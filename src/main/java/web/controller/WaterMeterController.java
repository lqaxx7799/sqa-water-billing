package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.repos.WaterMeterRepository;

@Controller
@RequestMapping("/waterMeter")
public class WaterMeterController {
	@Autowired
	private WaterMeterRepository waterMeterRepository;
	
	@GetMapping("/")
	public String read() {
		return "";
	}
}
