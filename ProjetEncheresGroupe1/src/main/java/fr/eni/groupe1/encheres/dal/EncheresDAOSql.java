package fr.eni.groupe1.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Retrait;

@Repository
public class EncheresDAOSql implements EncheresDAO{
	
	private final static String SELECT_ALL_ARTICLE = "select * from ARTICLES_VENDUS" ;
	private final static String SELECT_ALL_RETRAITS = "select * from RETRAITS" ;

	private final static String INSERT_NEW_ARTICLE = "insert into ARTICLES_VENDUS ( nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie ) values (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)" ;
	private final static String INSERT_NEW_INFORETRAIT = "insert into RETRAITS ( rue, code_postal, ville ) values (:rue, :code_postal, :ville)" ;
	private final static String INSERT_NEW_NOARTICLE = "Insert INTO RETRAITS (no_article) SELECT no_article FROM ARTICLES_VENDUS";
	private static final String DELETE_MODIF_ARTICLE = null;
	private static final String UPDATE = null;
	private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
		
		@Autowired
		public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		}
		
/////////////////////////////////       AFFICHAGE     ////////////////////////////////////////////
		
		@Override
		public List<ArticleVendu> articleAll() {
			List<ArticleVendu> listArticle;
			listArticle = namedParameterJdbcTemplate.query(SELECT_ALL_ARTICLE, new BeanPropertyRowMapper<>(ArticleVendu.class)) ;		

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

			if (article.getNoArticle() != null ) {
				newArticleMap.addValue("no_article", article.getNoArticle());
			}
			newArticleMap.addValue("nom_article", article.getNomArticle());	
			newArticleMap.addValue("description", article.getDescription());	
			newArticleMap.addValue("date_debut_encheres", article.getDateDebutEncheres());	
			newArticleMap.addValue("date_fin_encheres", article.getDateFinEncheres());	
			newArticleMap.addValue("prix_initial", article.getMiseAPrix());	
			newArticleMap.addValue("prix_vente", article.getPrixVente());	
			newArticleMap.addValue("no_utilisateur", article.getNoUtilisateur());	
			newArticleMap.addValue("no_categorie", article.getNoCategorie());	
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			System.out.println("debut du set article");
			
			if(article.getNoArticle()== null) {
				System.out.println("debut du if save "+ article);
				namedParameterJdbcTemplate.update(INSERT_NEW_ARTICLE, newArticleMap, keyHolder);
				article.setNoArticle(keyHolder.getKey().intValue());
				//setArticleByNoArticle(article.getNoArticle(), article.getNoUtilisateur());		
				System.out.println("article : " + article);
				
				// methode
			}else {//update
				System.out.println("je passe par le update du set article");
				MapSqlParameterSource articleModifMap = new MapSqlParameterSource();
				articleModifMap.addValue("no_article", article.noArticle);
				System.out.println("ajout map noArticle");
				namedParameterJdbcTemplate.update(DELETE_MODIF_ARTICLE, articleModifMap);
				System.out.println("delete article");
				//setActeurByFilm(film.getActeurs(), film.getIndexFilm());
				System.out.println("ajout acteur");
				namedParameterJdbcTemplate.update(UPDATE, newArticleMap);
				System.out.println("ajout des nouveaux param au film grace a la map");
			}			
		}


		@Override
		public void setInfoRetrait(Retrait infoRetrait, ArticleVendu article) {
			MapSqlParameterSource newInfoRetraitMap = new MapSqlParameterSource();
			
			newInfoRetraitMap.addValue("no_article", article.getNoArticle());
			newInfoRetraitMap.addValue("rue", infoRetrait.getRue());
			newInfoRetraitMap.addValue("code_postal", infoRetrait.getCodePostal());
			newInfoRetraitMap.addValue("ville", infoRetrait.getVille());
								
			namedParameterJdbcTemplate.update(INSERT_NEW_INFORETRAIT, newInfoRetraitMap);						
		}

/////////////////////////////////       SET     ////////////////////////////////////////////
}	
		