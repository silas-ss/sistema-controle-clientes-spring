package com.webplataforma.sistemacontroleclientes.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webplataforma.sistemacontroleclientes.domain.Customer;
import com.webplataforma.sistemacontroleclientes.domain.CustomerRepository;
import com.webplataforma.sistemacontroleclientes.domain.Occupation;
import com.webplataforma.sistemacontroleclientes.domain.OccupationRepository;

@Controller
@RequestMapping(value="/customers")
public class CustomerController {
	
	@Autowired
	private CustomerRepository cuRepository;

	@Autowired
	private OccupationRepository ocRepository;
	
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("customers", cuRepository.findAll());
		
		return "customers/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newCustomer(Model model){
		model.addAttribute("occupations", ocRepository.findAll());
		
		return "customers/new";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(@RequestParam("name") String name, @RequestParam("email") String email,
							@RequestParam("address") String address, @RequestParam("phone") String phone,
							@RequestParam("occupation") long id){
		Customer c = new Customer(name, email, address, phone, new Occupation(id));
		cuRepository.save(c);
		
		return new ModelAndView("redirect:/customers");
	}
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.GET)
	public String edit(@PathVariable long id, Model model){
		model.addAttribute("customer", cuRepository.findOne(id));
		model.addAttribute("occupations", ocRepository.findAll());
		
		return "customers/edit";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ModelAndView update(@RequestParam("name") String name, @RequestParam("email") String email,
							@RequestParam("address") String address, @RequestParam("phone") String phone,
							@RequestParam("occupation") long occupationId, @RequestParam("customer_id") long customerId){
		Customer c = cuRepository.findOne(customerId);
		c.setName(name);
		c.setEmail(email);
		c.setAddress(address);
		c.setPhone(phone);
		c.setOccupation(new Occupation(occupationId));
		cuRepository.save(c);
		
		return new ModelAndView("redirect:/customers");
	}
	
	@RequestMapping(value="/{id}/delete", method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable long id){
		cuRepository.delete(id);
		
		return new ModelAndView("rediect:/customers");
	}

}
