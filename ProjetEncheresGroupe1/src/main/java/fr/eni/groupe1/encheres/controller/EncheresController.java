package fr.eni.groupe1.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.eni.groupe1.encheres.bll.EncheresService;
import fr.eni.groupe1.encheres.bo.ArticleVendu;

@Controller
public class EncheresController {
	
	EncheresService encheresService;
	
	
	
	public EncheresController(EncheresService encheresService) {
		this.encheresService = encheresService;
	}

	@GetMapping({"/CreationVente"})
	public String inscriptionVente(@ModelAttribute ("article")ArticleVendu article, Model model) {
		System.out.println("je passe par le get Creation");
		model.addAttribute("listCasting",encheresService.getArticle());
		System.out.println(encheresService.getArticle());
		
		return "/CreationVente";
	}
	
	@PostMapping("/CreationVente")
	public String inscriptionFaite( @ModelAttribute ("article") ArticleVendu articleVendu, Model model ) {
		System.out.println("je passe par le post inscription");
		encheresService.ajouterArticle(articleVendu);
		
		return "redirect:/Inscription";
	}
}

