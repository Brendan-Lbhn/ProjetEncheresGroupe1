package fr.eni.groupe1.encheres.bll;

import java.security.Principal;
import java.util.List;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Enchere;
import fr.eni.groupe1.encheres.bo.Retrait;

public interface EncheresService {

	List<ArticleVendu> getArticle();
	List<ArticleVendu> getArtcicleByCategorie(Integer noCategorie);
	List<ArticleVendu> getArticleByName(String nameCategorie); 
	List<ArticleVendu> getArticleByNameAndCategorie(String nomArticle, Integer noCategorie);
	List<ArticleVendu> getArticleByFilters(Integer filtre, boolean encheresOuvertes, boolean encheresEnCours, boolean encheresRemportees, boolean ventesEnCours, boolean ventesNonDebutees, boolean ventesTerminees, int userId);
	
	void ajouterArticle(ArticleVendu articleVendu);

	void ajouterInfoRetrait(Retrait infoRetrait, ArticleVendu article);

	List<Retrait> getRetrait();
	ArticleVendu getArticleById(int id);
	Retrait getRetraitByEnchere(int id);
	void ajouterEnchere(Principal principal, ArticleVendu article, Enchere infoEncheres);
	Enchere getEnchereById(int id);
	

}
