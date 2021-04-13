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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.dto.WaterBillCalculateDTO;
import web.model.Account;
import web.model.Address;
import web.model.Employee;
import web.model.Pricing;
import web.model.Ward;
import web.model.WaterBill;
import web.model.WaterMeter;
import web.model.WaterMeterReading;
import web.repos.AccountRepository;
import web.repos.AddressRepository;
import web.repos.EmployeeRepository;
import web.repos.PricingRepository;
import web.repos.WardRepository;
import web.repos.WaterBillRepository;
import web.repos.WaterMeterReadingRepository;
import web.repos.WaterMeterRepository;
import web.util.CommonUtils;

@Controller
@RequestMapping("/waterBill")
public class WaterBillController {
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
	@Autowired
	private PricingRepository pricingRepository;
	@Autowired
	private WaterBillRepository waterBillRepository;
	
	@GetMapping("/calculate")
	public String calculate(
			@RequestParam(value = "wardId", required = false) Integer wardId,
			Model model,
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
			return "waterBillCalculate";
		}
		
		Ward ward = wardRepository.findById(wardId).get();
		List<Address> addresses = addressRepository.findByTblWard(ward);
		
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH) + 1;
		int year = today.get(Calendar.YEAR);
		int[] previousPeriod = CommonUtils.getPreviousPeriod(month, year);
		
		List<WaterMeterReading> currentReadings = waterMeterReadingRepository.findByPeriodAndWard(month, year, wardId);
		List<WaterMeterReading> previousReadings = waterMeterReadingRepository.findByPeriodAndWard(previousPeriod[0], previousPeriod[1], wardId);
		
		List<WaterBillCalculateDTO> bills = addresses
			.stream()
			.map((address) -> {
				WaterBillCalculateDTO billDTO = new WaterBillCalculateDTO();
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
				
				Pricing pricing = pricingRepository.findOneByTblAddressType(address.getTblAddressType().getId());
			
				billDTO.setAddress(address);
				billDTO.setWaterMeter(foundMeter);
				billDTO.setCustomer(address.getTblCustomer());
				billDTO.setCurrentReading(currentReading);
				billDTO.setPreviousReading(previousReading);
				
				int readingValue = currentReading.getReadingValue() - previousReading.getReadingValue();
				
				billDTO.setCost(CommonUtils.calculateBill(readingValue, pricing));
				
				return billDTO;
			})
			.collect(Collectors.toList());
		
		model.addAttribute("bills", bills);
		model.addAttribute("assignedAreas", employee.getTblWards());
		return "waterBillCalculate";
	}
	
	
	@PostMapping("/save")
	public void save(
			@RequestParam(value = "wardId", required = false) Integer wardId,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException {
		HttpSession session = request.getSession();
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String email = (String) session.getAttribute("email");
		Account account = accountRepository.findOneByEmail(email);
		
		if (account == null || (!account.getRole().equals("EMPLOYEE") && !account.getRole().equals("MANAGER"))) {
			out.write("{\"result\": \"error\"}");
			return;
		}
		
		Employee employee = employeeRepository.findOneByAccountId(account.getId());
		if (employee == null) {
			out.write("{\"result\": \"error\"}");
			return;
		}

		if (wardId == null || wardId.equals("")) {			
			out.write("{\"result\": \"error\"}");
			return;
		}
		
		Ward ward = wardRepository.findById(wardId).get();
		List<Address> addresses = addressRepository.findByTblWard(ward);
		
		Calendar today = Calendar.getInstance();
		int month = today.get(Calendar.MONTH) + 1;
		int year = today.get(Calendar.YEAR);
		int[] previousPeriod = CommonUtils.getPreviousPeriod(month, year);
		
		List<WaterMeterReading> currentReadings = waterMeterReadingRepository.findByPeriodAndWard(month, year, wardId);
		List<WaterMeterReading> previousReadings = waterMeterReadingRepository.findByPeriodAndWard(previousPeriod[0], previousPeriod[1], wardId);
		
		List<WaterBill> bills = addresses
			.stream()
			.map((address) -> {
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
				
				Pricing pricing = pricingRepository.findOneByTblAddressType(address.getTblAddressType().getId());
				
				// if bill is already created, only update
				// else create new bill
				WaterBill waterBill = waterBillRepository.findOneByTblWaterMeterReading(currentReading);
				
				if (waterBill == null) {
					waterBill = new WaterBill();
					waterBill.setAmount(1000);
					waterBill.setCreatedAt(new Date());
					
					Calendar dueDate = Calendar.getInstance();
					dueDate.setTime(new Date());
					dueDate.add(Calendar.DAY_OF_MONTH, 7);
					
					waterBill.setDueDate(dueDate.getTime());
					waterBill.setIsPaid(false);
					waterBill.setTblWaterMeterReading(currentReading);
				}
				
				waterBill.setTblEmployee(employee);
				
				int readingValue = currentReading.getReadingValue() - previousReading.getReadingValue();
				waterBill.setAmount(CommonUtils.calculateBill(readingValue, pricing));				
				
				return waterBill;
			})
			.collect(Collectors.toList());
		
		bills.forEach(bill -> {
			waterBillRepository.save(bill);
		});
		
		out.write("{\"result\": \"success\"}");
	}
}
