package web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.model.Employee;
import web.model.Ward;
import web.model.WaterMeter;
import web.model.WaterMeterReading;
import web.dto.WaterMeterReadingDTO;
import web.dto.WaterReadingUpdateDTO;
import web.model.Account;
import web.model.Address;
import web.repos.AccountRepository;
import web.repos.AddressRepository;
import web.repos.WardRepository;
import web.repos.EmployeeRepository;
import web.repos.WaterMeterReadingRepository;
import web.repos.WaterMeterRepository;
import web.util.CommonUtils;

@Controller
@RequestMapping("/reading")
public class WaterMeterReadingController {
	@Autowired
	private WaterMeterRepository waterMeterRepository;
	@Autowired
	private WaterMeterReadingRepository waterMeterReadingRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private WardRepository wardRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	@GetMapping("/list")
	public String read(
			Model model, 
			@RequestParam(value = "wardId", required = false) Integer wardId, 
			HttpServletRequest request
	) {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		Account account = accountRepository.findOneByEmail(email);
		
		if (account == null || (!account.getRole().equals("EMPLOYEE") && !account.getRole().equals("MANAGER"))) {
			return "redirect:/";
		}
		
		Employee employee = employeeRepository.findOneByAccountId(account.getId());
		if (employee == null) {
			return "redirect:/";
		}
		
		if (wardId == null || wardId.equals("")) {			
			model.addAttribute("assignedAreas", employee.getTblWards());
			return "waterReadingList";
		}
		
		Ward ward = wardRepository.findById(wardId).get();
		List<Address> addresses = addressRepository.findByTblWard(ward);
		
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH) + 1;
		int year = today.get(Calendar.YEAR);
		int[] previousPeriod = CommonUtils.getPreviousPeriod(month, year);
		
		List<WaterMeterReading> currentReadings = waterMeterReadingRepository.findByPeriodAndWard(month, year, wardId);
		List<WaterMeterReading> previousReadings = waterMeterReadingRepository.findByPeriodAndWard(previousPeriod[0], previousPeriod[1], wardId);
		
		List<WaterMeterReadingDTO> readings = addresses
			.stream()
			.map((address) -> {
				WaterMeterReadingDTO readingDTO = new WaterMeterReadingDTO();
				WaterMeter foundMeter = address.getTblWaterMeters().stream()
					.filter((meter) -> {
						return meter.getIsActive();
					})
					.findFirst()
					.orElse(new WaterMeter());
				
				WaterMeterReading currentReading = currentReadings.stream()
					.filter((reading) -> {
						return reading.getTblWaterMeter().getId() == foundMeter.getId();
					})
					.findFirst()
					.orElse(new WaterMeterReading());
			
				WaterMeterReading previousReading = previousReadings.stream()
					.filter((reading) -> {
						return reading.getTblWaterMeter().getId() == foundMeter.getId();
					})
					.findFirst()
					.orElse(new WaterMeterReading());
			
				readingDTO.setAddress(address);
				readingDTO.setWaterMeter(foundMeter);
				readingDTO.setCustomer(address.getTblCustomer());
				readingDTO.setCurrentReading(currentReading);
				readingDTO.setPreviousReading(previousReading);
				
				return readingDTO;
			})
			.collect(Collectors.toList());
		
		model.addAttribute("readings", readings);
		model.addAttribute("assignedAreas", employee.getTblWards());
		return "waterReadingList";
	}
	
	@PostMapping(value = "/update")
	public void update(@RequestBody List<WaterReadingUpdateDTO> data, HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		Account account = accountRepository.findOneByEmail(email);
		
		if (account == null || (!account.getRole().equals("EMPLOYEE") && !account.getRole().equals("MANAGER"))) {
			return;
		}
		
		Employee employee = employeeRepository.findOneByAccountId(account.getId());
		if (employee == null) {
			return;
		}
		
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH) + 1;
		int year = today.get(Calendar.YEAR);
		int[] previousPeriod = CommonUtils.getPreviousPeriod(month, year);
		
		for (WaterReadingUpdateDTO item: data) {
			WaterMeterReading reading = waterMeterReadingRepository.findOneByPeriodAndMeter(month, year, item.getWaterMeterId());
			WaterMeterReading previousReading = waterMeterReadingRepository.findOneByPeriodAndMeter(previousPeriod[0], previousPeriod[1], item.getWaterMeterId());
			WaterMeter waterMeter = waterMeterRepository.findById(item.getWaterMeterId()).orElse(null);
			
			int previousValue = previousReading == null ? 0 : previousReading.getCalculatedValue();
			
			if (reading == null) {
				WaterMeterReading newReading = new WaterMeterReading();
				newReading.setCreatedAt(new Date());
				newReading.setMonth(month);
				newReading.setReadingValue(item.getReadingValue());
				newReading.setTblEmployee(employee);
				newReading.setYear(year);
				newReading.setTblWaterMeter(waterMeter);
				newReading.setCalculatedValue(item.getReadingValue() - previousValue);
				waterMeterReadingRepository.save(newReading);
			} else {
				reading.setReadingValue(item.getReadingValue());
				reading.setCalculatedValue(item.getReadingValue() - previousValue);
				waterMeterReadingRepository.save(reading);
			}
		}
		PrintWriter out = response.getWriter();
		out.write("{\"result\": \"success\"}");
	}
}
