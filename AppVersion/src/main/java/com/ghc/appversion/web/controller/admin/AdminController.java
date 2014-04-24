package com.ghc.appversion.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/admin")
@Controller
public class AdminController extends AbstractAdminController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultPage(){
		return "redirect:/admin/users";
	}
}
