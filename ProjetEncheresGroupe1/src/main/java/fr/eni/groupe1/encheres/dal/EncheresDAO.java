package fr.eni.groupe1.encheres.dal;

import java.security.Principal;
import java.util.List;


import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Enchere;
import fr.eni.groupe1.encheres.bo.Retrait;

import org.springframework.stereotype.Repository;

@Repository
public interface EncheresDAO {

	 List<ArticleVendu> articleAll() ;
	 List<ArticleVendu> articleByCategorie(Integer noCategorie);

	void setArticle(ArticleVendu article);

	void setInfoRetrait(Retrait infoRetrait, ArticleVendu article);

	List<Retrait> retraitAll();
	List<ArticleVendu> articleByName(String nomArticle);
	List<ArticleVendu> articleByNameAndCategorie(String nomArticle, Integer noCategorie);
	
	ArticleVendu articleById(int id);

	Retrait retraitById(int id);
	void ajouterEnchere(Principal principal, ArticleVendu article, Enchere infoEncheres);
	Enchere enchereById(int id);
	void ajoutArticleAcheteur(int id, ArticleVendu article);

	
	

}
