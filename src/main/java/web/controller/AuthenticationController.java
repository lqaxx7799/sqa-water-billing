package web.controller;

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
import web.util.CommonUtils;
import web.dto.LogInDTO;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
	@Autowired
	private AccountRepository accountRepository;

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
				HttpSession session = request.getSession();
				session.setAttribute("email", email);
				return "redirect:/";				
			}
		}
	}
	
	@GetMapping("/logOut")
	public String logOut(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("email");
		return "redirect:/";
	}
}
