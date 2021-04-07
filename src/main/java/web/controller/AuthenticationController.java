package web.controller;

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
import web.repos.AddressTypeRepository;
import web.repos.ProvinceRepository;
import web.util.CommonUtils;
import web.dto.LogInDTO;
import web.dto.RegistrationDTO;
import web.model.Account;
import web.model.AddressType;
import web.model.Province;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private AddressTypeRepository addressTypeRepository;
	@Autowired
	private ProvinceRepository provinceRepository;

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
		
		model.addAttribute("addressTypes", addressTypes);
		model.addAttribute("provinces", provinces);
		model.addAttribute("registrationDTO", new RegistrationDTO());
		return "registration";
	}
	
	@PostMapping("/registration")
	public String submitRegistration(Model model) {
		List<AddressType> addressTypes = addressTypeRepository.findAll();
		List<Province> provinces = provinceRepository.findAll();
		
		model.addAttribute("addressTypes", addressTypes);
		model.addAttribute("provinces", provinces);
		model.addAttribute("registrationDTO", new RegistrationDTO());
		return "registration";
	}
	
	@GetMapping("/logOut")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("email");
		session.removeAttribute("role");
		return "redirect:/";
	}
}
