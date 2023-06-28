package fr.eni.groupe1.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Retrait;

@Repository
public class EncheresDAOSql implements EncheresDAO{
	
	private final static String SELECT_ALL_ARTICLE = "select * from ARTICLES_VENDUS" ;
	private final static String SELECT_ALL_RETRAITS = "select * from RETRAITS" ;

	private final static String INSERT_NEW_ARTICLE = "insert into ARTICLES_VENDUS ( nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie ) values (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)" ;
	private final static String INSERT_NEW_INFORETRAIT = "insert into RETRAITS ( rue, code_postal, ville ) values (:rue, :code_postal, :ville)" ;
	
	
	
	private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
		
		@Autowired
		public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		}
		
	class ArticlesVendusRowMapper implements RowMapper<ArticleVendu>{
		
		@Override
		public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
			ArticleVendu article = new ArticleVendu();
			System.out.println("vous êtes dans le RowMapper");
			article.setNoArticle(rs.getInt("no_article"));
			article.setNomArticle(rs.getString("nom_article"));
			article.setDescription(rs.getString("description"));
			article.setDateDebutEncheres(rs.getDate("date_debut_encheres"));
			article.setDateFinEncheres(rs.getDate("date_fin_encheres"));
			article.setMiseAPrix(rs.getInt("prix_initial"));
			article.setPrixVente(rs.getInt("prix_vente"));				
			article.setNoUtilisateur(rs.getInt("no_utilisateur"));
			article.setNoCategorie(rs.getInt("no_categorie"));
			return article;
		}
		
	}
		
/////////////////////////////////       AFFICHAGE     ////////////////////////////////////////////
		
		@Override
		public List<ArticleVendu> articleAll() {
			List<ArticleVendu> listArticle;
			
			System.out.println("Vous êtes arrivés dans la couche DAO !");
			listArticle = namedParameterJdbcTemplate.query(SELECT_ALL_ARTICLE, new ArticlesVendusRowMapper()) ;		

			return listArticle;
		}
		@Override
		public List<Retrait> retraitAll() {
			List<Retrait> listRetrait;
			listRetrait = namedParameterJdbcTemplate.query(SELECT_ALL_RETRAITS, new BeanPropertyRowMapper<>(Retrait.class)) ;	
			return listRetrait;
		}
		
		
/////////////////////////////////       SET     ////////////////////////////////////////////
	
		
		@Override
		public void setArticle(ArticleVendu article) {
			MapSqlParameterSource newArticleMap = new MapSqlParameterSource();

			newArticleMap.addValue("nom_article", article.getNomArticle());	
			newArticleMap.addValue("description", article.getDescription());	
			newArticleMap.addValue("date_debut_encheres", article.getDateDebutEncheres());	
			newArticleMap.addValue("date_fin_encheres", article.getDateFinEncheres());	
			newArticleMap.addValue("prix_initial", article.getMiseAPrix());	
			newArticleMap.addValue("prix_vente", article.getPrixVente());	
			newArticleMap.addValue("no_utilisateur", article.getNoUtilisateur());	
			newArticleMap.addValue("no_categorie", article.getNoCategorie());	
			
			namedParameterJdbcTemplate.update(INSERT_NEW_ARTICLE, newArticleMap);			
		}

		@Override
		public void setInfoRetrait(Retrait infoRetrait) {
			MapSqlParameterSource newInfoRetraitMap = new MapSqlParameterSource();
			
			newInfoRetraitMap.addValue("no_article", "1");
			newInfoRetraitMap.addValue("rue", infoRetrait.getRue());
			newInfoRetraitMap.addValue("code_postal", infoRetrait.getCodePostal());
			newInfoRetraitMap.addValue("ville", infoRetrait.getVille());	
	
			namedParameterJdbcTemplate.update(INSERT_NEW_INFORETRAIT, newInfoRetraitMap);						
		}

/////////////////////////////////       SET     ////////////////////////////////////////////
		
		
		
}
