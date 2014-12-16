package org.hcmut.emr.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the login page.
 */
@Controller
public class LoginController {
	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("User login");
		return "login";
	}
	
	@RequestMapping(value = "/error.html", method = RequestMethod.GET)
	public String error(Locale locale, Model model) {
		logger.info("Page not fault");
		return "error";
	}
	
	@RequestMapping(value = "/403.html", method = RequestMethod.GET)
	public String permissionDeny(Locale locale, Model model) {
		logger.info("Permission Deny");
		return "403";
	}
}
