package fr.eni.groupe1.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;

@Repository
public class EncheresDAOSql implements EncheresDAO{
	
	private final static String SELECT_ALL_ARTICLE = "select * from ARTICLES_VENDUS" ;
	private final static String INSERT_NEW_ARTICLE = "insert into ARTICLES_VENDUS ( nom_article, descripion, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie ) values (:nom_article, :descripion, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)" ;

	private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
		
		@Autowired
		public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		}
		
		public List<ArticleVendu> articleAll() {
			List<ArticleVendu> listArticle;
			listArticle = namedParameterJdbcTemplate.query(SELECT_ALL_ARTICLE, new BeanPropertyRowMapper<>(ArticleVendu.class)) ;		

			return listArticle;
		}

		@Override
		public void setArticle(ArticleVendu article) {
			MapSqlParameterSource newArticleMap = new MapSqlParameterSource();

			newArticleMap.addValue("nom_article", article.getNomArticle());	
			newArticleMap.addValue("descriptin", article.getDescription());	
			newArticleMap.addValue("date_debut_encheres", article.getDateDebutEncheres());	
			newArticleMap.addValue("date_fin_encheres", article.getDateFinEncheres());	
			newArticleMap.addValue("prix_initial", article.getMiseAPrix());	
			newArticleMap.addValue("prix_vente", article.getPrixVente());	
			newArticleMap.addValue("no_utilisateur", article.getNoUtilisateur());	
			newArticleMap.addValue("no_categorie", article.getNoCategorie());	
			
			namedParameterJdbcTemplate.update(INSERT_NEW_ARTICLE, newArticleMap);			
		}
		
		
		
}
