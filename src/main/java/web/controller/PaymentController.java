package web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.dto.WaterBillDTO;
import web.model.Account;
import web.model.Address;
import web.model.Customer;
import web.model.Employee;
import web.model.WaterBill;
import web.repos.AccountRepository;
import web.repos.CustomerRepository;
import web.repos.WaterBillRepository;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	public WaterBillRepository waterBillRepository;
	@Autowired
	public AccountRepository accountRepository;
	@Autowired
	public CustomerRepository customerRepository;
	
	@GetMapping("/list")
	public String paymentList(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		Account account = accountRepository.findOneByEmail(email);
		
		if (account == null || !account.getRole().equals("CUSTOMER")) {
			return "redirect:/";
		}
		
		Customer customer = customerRepository.findOneByAccountId(account.getId());
		if (customer == null) {
			return "redirect:/";
		}
		
		List<WaterBill> bills = waterBillRepository.findByCustomerId(customer.getId());
		
		model.addAttribute("bills", bills);
		return "paymentList";
	}
	
	@GetMapping("/detail")
	public String paymentDetail(@RequestParam("id") String billId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		Account account = accountRepository.findOneByEmail(email);
		
		if (account == null || !account.getRole().equals("CUSTOMER")) {
			return "redirect:/";
		}
		
		Customer customer = customerRepository.findOneByAccountId(account.getId());
		if (customer == null) {
			return "redirect:/";
		}
		
		WaterBill bill = waterBillRepository.findById(Integer.parseInt(billId)).orElse(null);
		
		if (bill == null || bill.getTblWaterMeterReading().getTblWaterMeter().getTblAddress().getTblCustomer().getId() != customer.getId()) {
			return "redirect:/";
		}
		
		Address address = bill.getTblWaterMeterReading().getTblWaterMeter().getTblAddress();
		String addressStr = String.format(
				"%s, %s, %s, %s",
				address.getStreet(),
				address.getTblWard().getWardName(),
				address.getTblWard().getTblDistrict().getDistrictName(),
				address.getTblWard().getTblDistrict().getTblProvince().getProvinceName()
				);
		
		WaterBillDTO billDTO = new WaterBillDTO();
		billDTO.setWaterBill(bill);
		billDTO.setAddress(addressStr);
		billDTO.setAddressType(address.getTblAddressType().getType());
		billDTO.setCalculatedReading(bill.getTblWaterMeterReading().getCalculatedValue());
		billDTO.setCurrentReading(bill.getTblWaterMeterReading().getReadingValue());
		billDTO.setCustomerName(address.getTblCustomer().getFirstName() + " " + address.getTblCustomer().getLastName());
		billDTO.setPreviousReading(bill.getTblWaterMeterReading().getReadingValue() - bill.getTblWaterMeterReading().getCalculatedValue());
		billDTO.setDate(bill.getTblWaterMeterReading().getMonth() + "/" + bill.getTblWaterMeterReading().getYear());
		billDTO.setTotalPrice(bill.getAmount());
		
		model.addAttribute("bill", billDTO);
		return "paymentDetail";
	}
}
