package web.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.mail.EmailService;
import web.model.Account;
import web.model.Customer;
import web.repos.AccountRepository;
import web.repos.CustomerRepository;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	public AccountRepository accountRepository;
	@Autowired
	public CustomerRepository customerRepository;

	@GetMapping("/detail") 
	public String detail(Model model, HttpServletRequest request) {
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
		
		model.addAttribute("customer", customer);
		return "accountDetail";
	}
	
	@GetMapping("/resendEmail")
	public void resendEmail(@RequestParam("id") String customerId, HttpServletResponse response) throws IOException, AddressException, MessagingException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Customer customer = customerRepository.findById(Integer.parseInt(customerId)).orElse(null);
		if (customer == null) {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			out.write("{\"success\": false}");
			return;
		}
		
		String mailBody = "<html><body>";
		mailBody += "Xin vui lòng nhấn vào liên kết sau để xác thực tài khoản của bạn\n";
		mailBody += "<a href=\"http://localhost:8080/authentication/validate?email="+ customer.getTblAccount().getEmail() +"\">Xác thực</a>";
		mailBody += "</body></html>";
		EmailService.sendMail(customer.getTblAccount().getEmail(), "Xác thực tài khoản của bạn", "Xác thực tài khoản của bạn", mailBody);
		
		out.write("{\"success\": true}");
	}
}
