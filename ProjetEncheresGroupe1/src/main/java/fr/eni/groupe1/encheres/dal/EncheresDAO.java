package fr.eni.groupe1.encheres.dal;

import java.util.List;


import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Retrait;

import org.springframework.stereotype.Repository;

@Repository
public interface EncheresDAO {

	 List<ArticleVendu> articleAll() ;
	 List<ArticleVendu> articleByCategorie(Integer noCategorie);

	void setArticle(ArticleVendu article);

	void setInfoRetrait(Retrait infoRetrait, ArticleVendu article);

	List<Retrait> retraitAll();
	List<ArticleVendu> articleByName(String nameCategorie);

	
	
}
