package fr.eni.groupe1.encheres.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.eni.groupe1.encheres.bll.EncheresCategoriesService;
import fr.eni.groupe1.encheres.bll.EncheresService;
import fr.eni.groupe1.encheres.bll.UtilisateurService;
import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Controller
@RequestMapping("")
public class EncheresControllerAccueil {
	
	private EncheresCategoriesService encheresCategoriesService;
	private EncheresService encheresService;
	private UtilisateurService utilisateurService;

	
	public EncheresControllerAccueil(EncheresCategoriesService encheresCategoriesService,
			EncheresService encheresService,
			UtilisateurService utilisateurService) {
		this.encheresCategoriesService = encheresCategoriesService;
		this.encheresService = encheresService;
		this.utilisateurService = utilisateurService; 
		 
	}

	@GetMapping({"/","/accueil"})
	public String afficherAccueil(@ModelAttribute("categorie") Categorie categorie,@ModelAttribute("article") ArticleVendu article, Model model) {
		
		List<Categorie>listeCategories = encheresCategoriesService.getCategories();
		model.addAttribute("categorie",listeCategories); 
		
		List<ArticleVendu>listArticles = encheresService.getArticle();
		model.addAttribute("articleVendu",listArticles);
		
		return "Index";
	}
	
	@PostMapping({"rechercher"})
	public String rechercherArticle(@ModelAttribute("categorie") Categorie categorie, @ModelAttribute("article") ArticleVendu article, Model model ) {
				
		if(categorie.getNoCategorie() == 1 && article.getNomArticle().isEmpty()) {
			return "redirect:/accueil";
		}else if(categorie.getNoCategorie() != 1 && article.getNomArticle().isEmpty()) {
			List<ArticleVendu>listArticles = encheresService.getArtcicleByCategorie(categorie.getNoCategorie());
			model.addAttribute("articleVendu",listArticles);
			return "Index";
		}else if(categorie.getNoCategorie() == 1 && !article.getNomArticle().isEmpty()) {
			List<ArticleVendu>listArticles = encheresService.getArticleByName(article.getNomArticle());
			model.addAttribute("articleVendu",listArticles);
			return "Index";
		}else {
			System.out.println("controller name categorie");
			List<ArticleVendu>listArticles = encheresService.getArticleByNameAndCategorie(article.getNomArticle(), categorie.getNoCategorie());
			model.addAttribute("articleVendu",listArticles);
			return "Index";
		}
		
	}
	
	@PostMapping({"rechercher2"})
	public String rechercherArticle2(@RequestParam("filtre") int filtre,
			@RequestParam(value = "encheres-ouvertes", defaultValue = "false") boolean encheresOuvertes,
			@RequestParam (value = "encheres-en-cours", defaultValue = "false") boolean encheresEnCours,
			@RequestParam (value = "encheres-remportees", defaultValue = "false") boolean encheresRemportees,
			@RequestParam (value = "ventes-en-cours", defaultValue = "false") boolean ventesEnCours,
			@RequestParam (value = "ventes-non-debutees", defaultValue = "false") boolean ventesNonDebutees,
			@RequestParam (value = "ventes-terminees", defaultValue = "false") boolean ventesTerminees,
			@ModelAttribute("article") ArticleVendu article,
			Model model,
			Principal principal) {
		
		Utilisateur utilisateur = utilisateurService.findByPseudo(principal.getName());
		utilisateur.getNoUtilisateur();
		
		int userId = utilisateur.getNoUtilisateur();
		
		List<ArticleVendu> listArticles = encheresService.getArticleByFilters(filtre, encheresOuvertes, encheresEnCours, encheresRemportees, ventesEnCours, ventesNonDebutees, ventesTerminees, userId);
		model.addAttribute("articleVendu", listArticles);
				
		return "Index";
		
	}
	
}
