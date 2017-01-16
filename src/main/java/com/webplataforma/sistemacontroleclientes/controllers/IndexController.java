package com.webplataforma.sistemacontroleclientes.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.webplataforma.sistemacontroleclientes.domain.User;
import com.webplataforma.sistemacontroleclientes.domain.UserRepository;

@Controller
@RequestMapping(value="")
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(){
		return "redirect:/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)	
	public String login(){
		return "index";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession httpSession, Model model){
		User user = userRepository.findByEmail(email);
		if(user != null && user.getPassword().equals(password)){
			httpSession.setAttribute("id", user.getId());
			httpSession.setAttribute("name", user.getName());
			httpSession.setAttribute("email", user.getEmail());
			
			return "redirect:/customers";
		}
		
		model.addAttribute("erro", "Usuário ou senha inválidos!");
		
		return "index";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession httpSession){
		httpSession.invalidate();
		
		return "redirect:/login";
	}
	
}
