package web.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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
import org.springframework.web.bind.annotation.RequestMethod;

import web.repos.AccountRepository;
import web.repos.AddressRepository;
import web.repos.AddressTypeRepository;
import web.repos.CustomerRepository;
import web.repos.DistrictRepository;
import web.repos.ProvinceRepository;
import web.repos.WardRepository;
import web.repos.WaterMeterRepository;
import web.util.CommonUtils;
import web.dto.LogInDTO;
import web.dto.RegistrationDTO;
import web.mail.EmailService;
import web.model.Account;
import web.model.Address;
import web.model.AddressType;
import web.model.Customer;
import web.model.District;
import web.model.Province;
import web.model.Ward;
import web.model.WaterMeter;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private AddressTypeRepository addressTypeRepository;
	@Autowired
	private ProvinceRepository provinceRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private WardRepository wardRepository;
	@Autowired
	private WaterMeterRepository waterMeterRepository;

	@GetMapping("/logIn")
	public String viewLogIn(Model model) {
		model.addAttribute("errEmail", "");
		model.addAttribute("errPassword", "");
		model.addAttribute("logInDTO", new LogInDTO());
		return "logIn";
	}
	
	@PostMapping("/logIn")
	public String submitLogIn(@ModelAttribute LogInDTO logInDTO, Model model, HttpServletRequest request) {
		String email = logInDTO.getEmail(), password = logInDTO.getPassword();
		boolean isValid = true;
		String errEmail = "", errPassword = "";
		if (email.equals("")) {
			isValid = false;
			errEmail = "Vui l??ng nh???p email";
		}
		if (password.equals("")) {
			isValid = false;
			errPassword = "Vui l??ng nh???p m???t kh???u";
		}
		
		if (!isValid) {
			model.addAttribute("errEmail", errEmail);
			model.addAttribute("errPassword", errPassword);
			model.addAttribute("logInDTO", logInDTO);
			return "logIn";
		} else {
			boolean found = accountRepository.existByEmailAndPassword(email, CommonUtils.generateSHA1(password));
			if (!found) {
				model.addAttribute("errEmail", "Sai email ho???c password");
				model.addAttribute("errPassword", "");
				model.addAttribute("logInDTO", logInDTO);
				return "logIn";
			} else {
				Account foundAccount = accountRepository.findOneByEmail(email);
				HttpSession session = request.getSession();
				session.setAttribute("email", email);
				session.setAttribute("role", foundAccount.getRole());
				return "redirect:/";				
			}
		}
	}
	
	@GetMapping("/registration")
	public String registration(Model model) {
		List<AddressType> addressTypes = addressTypeRepository.findAll();
		List<Province> provinces = provinceRepository.findAll();
		List<District> districts = districtRepository.findByTblProvince(provinces.get(0));
		List<Ward> wards = wardRepository.findByTblDistrict(districts.get(0));
		
		model.addAttribute("addressTypes", addressTypes);
		model.addAttribute("provinces", provinces);
		model.addAttribute("districts", districts);
		model.addAttribute("wards", wards);
		model.addAttribute("registrationDTO", new RegistrationDTO());
		model.addAttribute("errors", new LinkedHashMap<String, String>());
		return "registration";
	}
	
	@PostMapping("/registration")
	public String submitRegistration(@ModelAttribute RegistrationDTO registrationDTO, Model model) throws AddressException, MessagingException {
		Map<String, String> errors = new LinkedHashMap<>();
		boolean isValid = true;
		
		if (registrationDTO.getLastName() == null || registrationDTO.getLastName().equals("")) {
			isValid = false;
			errors.put("errLastName", "H??? kh??ng ???????c ????? tr???ng");
		}
		
		if (registrationDTO.getFirstName() == null || registrationDTO.getFirstName().equals("")) {
			isValid = false;
			errors.put("errFirstName", "T??n kh??ng ???????c ????? tr???ng");
		}
		
		if (registrationDTO.getEmail() == null || registrationDTO.getEmail().equals("")) {
			isValid = false;
			errors.put("errEmail", "Email kh??ng ???????c ????? tr???ng");
		} else if (!CommonUtils.checkEmailFormat(registrationDTO.getEmail())) {
			isValid = false;
			errors.put("errEmail", "?????nh d???ng email kh??ng h???p l???");
		} else {
			Account existedAccount = accountRepository.findOneByEmail(registrationDTO.getEmail());
			if (existedAccount != null) {
				isValid = false;
				errors.put("errEmail", "Email ???? t???n t???i");
			}
		}
		
		if (registrationDTO.getPassword() == null || registrationDTO.getPassword().equals("")) {
			isValid = false;
			errors.put("errPassword", "M???t kh???u kh??ng ???????c ????? tr???ng");
		}
		
		if (registrationDTO.getReenterPassword() == null || registrationDTO.getReenterPassword().equals("")) {
			isValid = false;
			errors.put("errReenterPassword", "Vui l??ng nh???p l???i m???t kh???u");
		} else if (!registrationDTO.getReenterPassword().equals(registrationDTO.getPassword())) {
			isValid = false;
			errors.put("errReenterPassword", "M???t kh???u kh??ng kh???p");
		}
		
		if (registrationDTO.getIdNumber() == null || registrationDTO.getIdNumber().equals("")) {
			isValid = false;
			errors.put("errIdNumber", "CMT/CCCD kh??ng ???????c ????? tr???ng");
		} else if (!CommonUtils.checkIdNumberFormat(registrationDTO.getIdNumber())) {
			isValid = false;
			errors.put("errIdNumber", "CMT/CCCD c?? 9 ho???c 12 s???");
		}
		
		if (registrationDTO.getPhoneNumber() == null || registrationDTO.getPhoneNumber().equals("")) {
			isValid = false;
			errors.put("errPhoneNumber", "S??? ??i???n tho???i kh??ng ???????c ????? tr???ng");
		}
		
		if (registrationDTO.getDateOfBirth() == null) {
			isValid = false;
			errors.put("errDateOfBirth", "Ng??y sinh kh??ng ???????c ????? tr???ng");
		} else if (registrationDTO.getDateOfBirth().after(new Date())) {
			isValid = false;
			errors.put("errDateOfBirth", "Ng??y sinh kh??ng ???????c trong t????ng lai");
		}
		
		if (registrationDTO.getStreet() == null || registrationDTO.getStreet().equals("")) {
			isValid = false;
			errors.put("errStreet", "?????a ch??? kh??ng ???????c ????? tr???ng");
		}
		
		if (!isValid) {
			List<AddressType> addressTypes = addressTypeRepository.findAll();
			List<Province> provinces = provinceRepository.findAll();
			Province selectedProvince = provinces.size() == 0 ? null : registrationDTO.getProvinceId() == 0
					? provinces.get(0)
					: provinces
						.stream()
						.filter(province -> province.getId() == registrationDTO.getProvinceId())
						.findFirst()
						.get();
			List<District> districts = districtRepository.findByTblProvince(selectedProvince);
			District selectedDistrict = districts.size() == 0 ? null : registrationDTO.getDistrictId() == 0
					? districts.get(0)
					: districts
						.stream()
						.filter(district -> district.getId() == registrationDTO.getDistrictId())
						.findFirst()
						.get();
			
			List<Ward> wards = wardRepository.findByTblDistrict(selectedDistrict);
			
			model.addAttribute("addressTypes", addressTypes);
			model.addAttribute("provinces", provinces);
			model.addAttribute("districts", districts);
			model.addAttribute("wards", wards);
			model.addAttribute("registrationDTO", registrationDTO);
			model.addAttribute("errors", errors);
			return "registration";
		}
		
		Account account = new Account();
		account.setEmail(registrationDTO.getEmail());
		account.setPassword(CommonUtils.generateSHA1(registrationDTO.getPassword()));
		account.setRole("CUSTOMER");
		account.setCreatedAt(new Date());
		accountRepository.save(account);
		
		Customer customer = new Customer();
		customer.setCreatedAt(new Date());
		customer.setFirstName(registrationDTO.getFirstName());
		customer.setLastName(registrationDTO.getLastName());
		customer.setGender(registrationDTO.getGender());
		customer.setIdNumber(registrationDTO.getIdNumber());
		customer.setPhoneNumber(registrationDTO.getPhoneNumber());
		customer.setDateOfBirth(registrationDTO.getDateOfBirth());
		customer.setTblAccount(account);
		customer.setIsVerified(false);
		customerRepository.save(customer);
		
		Ward ward = wardRepository.findById(registrationDTO.getWardId()).orElse(null);
		AddressType addressType = addressTypeRepository.findById(registrationDTO.getAddressTypeId()).orElse(null);
		
		Address address = new Address();
		address.setHouseNumber("");
		address.setStreet(registrationDTO.getStreet());
		address.setTblWard(ward);
		address.setTblAddressType(addressType);
		address.setTblCustomer(customer);
		addressRepository.save(address);
		
		WaterMeter waterMeter = new WaterMeter();
		waterMeter.setInstalledDate(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, 2);
		waterMeter.setExpiredDate(calendar.getTime());
		waterMeter.setIsActive(false);
		waterMeter.setMaximumReading(10000);
		waterMeter.setTblAddress(address);
		waterMeterRepository.save(waterMeter);
		
		String mailBody = "<div>";
		mailBody += "<div>Xin vui l??ng nh???n v??o li??n k???t sau ????? x??c th???c t??i kho???n c???a b???n</div>";
		mailBody += "<a href='http://localhost:8080/validate?email="+ registrationDTO.getEmail() +"' target='_blank'>X??c th???c</a>";
		mailBody += "</div>";
		EmailService.sendMail(registrationDTO.getEmail(), "X??c th???c t??i kho???n c???a b???n", "X??c th???c t??i kho???n c???a b???n", mailBody);
		
		return "redirect:/";
	}
	
	@GetMapping("/validate")
	public String validate(@Param("email") String email, Model model) {
		Account account = accountRepository.findOneByEmail(email);
		if (account == null) {
			return "redirect:/";
		}
		if (!account.getRole().equals("CUSTOMER")) {
			return "redirect:/";
		}
		Customer customer = customerRepository.findOneByAccountId(account.getId());
		if (customer == null) {
			return "redirect:/";
		}
		if (customer.getIsVerified()) {
			return "redirect:/";
		}
		customer.setIsVerified(true);
		customerRepository.save(customer);
		
		WaterMeter waterMeter = waterMeterRepository.findLastByCustomer(customer.getId());
		waterMeter.setIsActive(true);
		waterMeterRepository.save(waterMeter);

		return "redirect:/account/detail";
	}
	
	@GetMapping("/logOut")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("email");
		session.removeAttribute("role");
		return "redirect:/";
	}
}
