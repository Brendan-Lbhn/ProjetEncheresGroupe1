package fr.eni.groupe1.encheres.dal;

import java.util.List;


import fr.eni.groupe1.encheres.bo.ArticleVendu;

import org.springframework.stereotype.Repository;

@Repository
public interface EncheresDAO {

	 List<ArticleVendu> articleAll() ;

	void setArticle(ArticleVendu article);

	
	
}
