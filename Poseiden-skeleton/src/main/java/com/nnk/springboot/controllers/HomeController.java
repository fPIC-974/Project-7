package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController
{
	@RequestMapping("/")
	public String home(Model model, Principal principal)
	{
		model.addAttribute("user", principal.getName());

		return "redirect:/bidList/list";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model, Principal principal)
	{
		model.addAttribute("user", principal.getName());

		return "redirect:/user/list";
	}

}
