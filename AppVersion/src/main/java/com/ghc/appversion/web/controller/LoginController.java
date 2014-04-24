package com.ghc.appversion.web.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ghc.appversion.web.form.Message;

@RequestMapping("/login")
@Controller
public class LoginController extends AbstractController {
			
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model, Locale locale){		
		return "login";
	}
	
	@RequestMapping(value = "/loginfail", method = RequestMethod.GET)
	public String loginFail(Model model, Locale locale){
		model.addAttribute(
				"message",
				new Message("error", messageSource.getMessage(
						"message_login_fail", new Object[]{}, locale)));
		return "login";
	}
}
