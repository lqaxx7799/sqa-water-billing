package web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import web.repos.AccountRepository;
import web.repos.AddressRepository;
import web.repos.AddressTypeRepository;
import web.repos.CustomerRepository;
import web.repos.DistrictRepository;
import web.repos.ProvinceRepository;
import web.repos.WardRepository;
import web.util.CommonUtils;
import web.dto.LogInDTO;
import web.dto.RegistrationDTO;
import web.model.Account;
import web.model.Address;
import web.model.AddressType;
import web.model.Customer;
import web.model.District;
import web.model.Province;
import web.model.Ward;

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
			errEmail = "Email cannot be empty";
		}
		if (password.equals("")) {
			isValid = false;
			errPassword = "Password cannot be empty";
		}
		
		if (!isValid) {
			model.addAttribute("errEmail", errEmail);
			model.addAttribute("errPassword", errPassword);
			model.addAttribute("logInDTO", logInDTO);
			return "logIn";
		} else {
			boolean found = accountRepository.existByEmailAndPassword(email, CommonUtils.generateSHA1(password));
			if (!found) {
				model.addAttribute("errEmail", "Wrong email or password");
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
		return "registration";
	}
	
	@PostMapping("/registration")
	public String submitRegistration(@ModelAttribute RegistrationDTO registrationDTO, Model model) {
		List<AddressType> addressTypes = addressTypeRepository.findAll();
		List<Province> provinces = provinceRepository.findAll();
		
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
		addressRepository.save(address);
		
		return "redirect:/";
		
//		model.addAttribute("addressTypes", addressTypes);
//		model.addAttribute("provinces", provinces);
//		model.addAttribute("registrationDTO", new RegistrationDTO());
//		return "registration";
	}
	
	@GetMapping("/logOut")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("email");
		session.removeAttribute("role");
		return "redirect:/";
	}
}
