package web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.dto.DetailedBillDTO;
import web.dto.WaterBillDTO;
import web.model.Account;
import web.model.Address;
import web.model.Customer;
import web.model.Employee;
import web.model.Payment;
import web.model.Pricing;
import web.model.WaterBill;
import web.repos.AccountRepository;
import web.repos.CustomerRepository;
import web.repos.PaymentRepository;
import web.repos.PricingRepository;
import web.repos.WaterBillRepository;
import web.util.CommonUtils;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	@Autowired
	public WaterBillRepository waterBillRepository;
	@Autowired
	public PaymentRepository paymentRepository;
	@Autowired
	public AccountRepository accountRepository;
	@Autowired
	public CustomerRepository customerRepository;
	@Autowired
	public PricingRepository pricingRepository;
	
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
		billDTO.setDetailedBill(_getDetailedBill(bill));
		
		model.addAttribute("bill", billDTO);
		
		if (bill.getIsPaid()) {
			model.addAttribute("payment", paymentRepository.findOneByWaterBill(bill.getId()));
		} else {			
			model.addAttribute("payment", new Payment());
		}
		
		model.addAttribute("errors", new LinkedHashMap<String, String>());
		return "paymentDetail";
	}
	
	@PostMapping("/detail")
	public String submitPayment(@RequestParam("id") String billId, @ModelAttribute Payment payment, Model model, HttpServletRequest request) {
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
		
		boolean isValid = true;
		Map<String, String> errors = new LinkedHashMap<>();
		
		if (payment.getPaymentCode().equals("")) {
			isValid = false;
			errors.put("errPaymentCode", "Vui lòng nhập mã tài khoản của bạn");
		} else if (!CommonUtils.checkPaymentCodeFormat(payment.getPaymentCode())) {
			isValid = false;
			errors.put("errPaymentCode", "Mã tài khoản không hợp lệ");
		}
		
		if (!isValid) {
			model.addAttribute("errors", errors);
			model.addAttribute("payment", payment);
		} else {
			payment.setConfirmed(false);
			payment.setCreatedAt(new Date());
			payment.setTblWaterBill(bill);
			paymentRepository.save(payment);
			
			bill.setIsPaid(true);
			waterBillRepository.save(bill);
			
			// [TODO]: send confirmation email to customer
			
			model.addAttribute("errors", new LinkedHashMap<String, String>());
			model.addAttribute("payment", new Payment());
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
	
	
	private List<DetailedBillDTO> _getDetailedBill(WaterBill bill) {
		Address address = bill.getTblWaterMeterReading().getTblWaterMeter().getTblAddress();
		
		Pricing pricing = pricingRepository.findOneByTblAddressTypeAndPeriod(address.getTblAddressType().getId(), bill.getCreatedAt());
		int temp = bill.getTblWaterMeterReading().getCalculatedValue();
		List<DetailedBillDTO> detailedBill = new ArrayList<>();
		for (int i = 1; i <= 4; i ++) {
			float unitPrice = 0;
			switch (i) {
				case 1:
					unitPrice = pricing.getUnitPriceLevel1();
					break;
				case 2:
					unitPrice = pricing.getUnitPriceLevel2();
					break;
				case 3:
					unitPrice = pricing.getUnitPriceLevel3();
					break;
				case 4:
					unitPrice = pricing.getUnitPriceLevel4();
					break;
			}
			
			if (i != 4) {				
				if (temp > 10) {
					DetailedBillDTO detailedBillItem = new DetailedBillDTO();
					detailedBillItem.setLevel(i);
					detailedBillItem.setUnitPrice(unitPrice);
					detailedBillItem.setValue(10);
					detailedBill.add(detailedBillItem);
				} else {
					DetailedBillDTO detailedBillItem = new DetailedBillDTO();
					detailedBillItem.setLevel(i);
					detailedBillItem.setUnitPrice(unitPrice);
					detailedBillItem.setValue(temp);
					detailedBill.add(detailedBillItem);
					break;
				}
				temp -= 10;
			} else {
				DetailedBillDTO detailedBillItem = new DetailedBillDTO();
				detailedBillItem.setLevel(i);
				detailedBillItem.setUnitPrice(unitPrice);
				detailedBillItem.setValue(temp);
				detailedBill.add(detailedBillItem);
			}
		}
		return detailedBill;
	}
}
