package fr.eni.groupe1.encheres.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.groupe1.encheres.bll.EncheresCategoriesService;
import fr.eni.groupe1.encheres.bll.EncheresService;
import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.bo.Enchere;
import fr.eni.groupe1.encheres.bo.Retrait;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Controller
public class EncheresController {
	
	EncheresService encheresService;
	EncheresCategoriesService encheresCategoriesService;
	UtilisateurService utilisateurService;
	
	public EncheresController(EncheresService encheresService,EncheresCategoriesService encheresCategoriesService,UtilisateurService utilisateurService) {
		this.encheresService = encheresService;
		this.encheresCategoriesService = encheresCategoriesService;
		this.utilisateurService = utilisateurService;
		
	}

	
	/////////////////////////////////        CREATION D UN ARTICLE     ////////////////////////////////////////////

	@GetMapping({"CreationVente"})
	public String inscriptionVente(Principal principal,@ModelAttribute ("article")ArticleVendu article,@ModelAttribute ("retrait") Retrait infoRetrait, Model model) {
	System.out.println("je passe par le get CreationVente");
		List<Categorie>listeCategories = encheresCategoriesService.getCategories();
		
		model.addAttribute("utilisateur",utilisateurService.findByPseudo(principal.getName()));
		model.addAttribute("listAticle",encheresService.getArticle());
		model.addAttribute("listRetrait", encheresService.getRetrait());
		model.addAttribute("categorie",listeCategories);
		return "/CreationVente";
	}
	
	@PostMapping({"CreationVente"})
	public String inscriptionFaite( @ModelAttribute ("articleVendu") ArticleVendu articleVendu,@ModelAttribute ("retrait") Retrait infoRetrait,Model model ) {
		System.out.println("je passe par le post CreationVente");
		encheresService.ajouterArticle(articleVendu);
		System.out.println(articleVendu.toString());
		encheresService.ajouterInfoRetrait(infoRetrait, articleVendu);
		System.out.println(infoRetrait.toString());
		return "redirect:/accueil";
	}
	
	/////////////////////////////////       AFFICHAGE D UN DETAIL DE VENTE     ////////////////////////////////////////////
	@GetMapping({"DetailVente"})  //, @RequestParam int id
	public String AfficherDetailVente(Principal principal,@ModelAttribute ("article")ArticleVendu article,@ModelAttribute ("retrait") Retrait infoRetrait, @ModelAttribute ("enchere") Enchere infoEncheres, Model model) {
	System.out.println("je passe par le get DetailVente");
		int id =10;

		model.addAttribute("utilisateur",utilisateurService.findByPseudo(principal.getName()));
		model.addAttribute("article",encheresService.getArticleById(id));
		model.addAttribute("retrait", encheresService.getRetraitByEnchere(id));
		model.addAttribute("enchere", encheresService.getEnchereById(id));
//		infoEncheres.setArticleEnchere(article);
//		 article.setVendeur(null);
//		 Utilisateur utilisateur;
		 //utilisateur.setPseudo(null);
		System.out.println(encheresService.getEnchereById(id));

		return "/DetailVente";
	}
	/////////////////////////////////       ENCHERE DETAIL VENTE     ////////////////////////////////////////////

	@PostMapping({"/EnchereAjout"})  //, @RequestParam int id
	public String FaireUneEnchere(Principal principal,@ModelAttribute ("article")ArticleVendu article, @ModelAttribute ("enchere") Enchere infoEncheres, Model model) {
	System.out.println("je passe par le post enchere detail vente");
	System.out.println("encheres : " + infoEncheres);
	int id =10;
	model.addAttribute("utilisateur",utilisateurService.findByPseudo(principal.getName()));
	model.addAttribute("article",encheresService.getArticleById(id));
	model.addAttribute("retrait", encheresService.getRetraitByEnchere(id));
	model.addAttribute("enchere", encheresService.getEnchereById(id));
		encheresService.ajouterEnchere(principal,article, infoEncheres);
		return "/DetailVente";
	}
}

