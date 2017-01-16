package com.webplataforma.sistemacontroleclientes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webplataforma.sistemacontroleclientes.domain.Occupation;
import com.webplataforma.sistemacontroleclientes.domain.OccupationRepository;

@Controller
@RequestMapping(value="/occupations")
public class OccupationController {
	
	@Autowired
	private OccupationRepository ocRepository;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("occupations", ocRepository.findAll());
		
		return "occupations/list";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String newOccupation(){
		return "occupations/new";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public ModelAndView create(@RequestParam("description") String desc, Model model){
		Occupation o = new Occupation(desc);
		ocRepository.save(o);
		
		return new ModelAndView("redirect:/occupations");
	}
	
	@RequestMapping(value="/{id}/edit", method=RequestMethod.GET)
	public String edit(@PathVariable long id, Model model){
		model.addAttribute("occupation", ocRepository.findOne(id));
		
		return "occupations/edit";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public ModelAndView update(@RequestParam("description") String desc, @RequestParam("occupation_id") long id){
		Occupation o = ocRepository.findOne(id);
		o.setDescription(desc);
		ocRepository.save(o);
		
		return new ModelAndView("redirect:/occupations");
	}
	
	@RequestMapping(value="/{id}/delete", method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable long id){
		ocRepository.delete(id);
		
		return new ModelAndView("redirect:/occupations");
	}

}
