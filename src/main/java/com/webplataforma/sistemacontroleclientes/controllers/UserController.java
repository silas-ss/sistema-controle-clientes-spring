package com.webplataforma.sistemacontroleclientes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webplataforma.sistemacontroleclientes.domain.User;
import com.webplataforma.sistemacontroleclientes.domain.UserRepository;

@Controller
@EnableAutoConfiguration
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	private UserRepository userRespository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("users", userRespository.findAll());
		
		return "users/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newUser(){
		return "users/new";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(@RequestParam("name") String name, 
							@RequestParam("email") String email,
							@RequestParam("password") String password,
							Model model){
		User user = new User(name, email, password);
		userRespository.save(user);
		
		return new ModelAndView("redirect:/users");
	}
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.GET)
	public String edit(@PathVariable long id, Model model){
		model.addAttribute("user", userRespository.findOne(id));
		
		return "users/edit";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ModelAndView update(@RequestParam("name") String name,
							@RequestParam("email") String email,
							@RequestParam("password") String password,
							@RequestParam("user_id") long id,
							Model model){
		 User user = userRespository.findOne(id);
		 user.setEmail(email);
		 user.setName(name);
		 user.setPassword(password);
		 userRespository.save(user);
		
		return new ModelAndView("redirect:/users");
	}
	
	@RequestMapping(value="/{id}/delete", method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable long id, Model model){
		userRespository.delete(id);
		
		return new ModelAndView("redirect:/users");
	}

}
