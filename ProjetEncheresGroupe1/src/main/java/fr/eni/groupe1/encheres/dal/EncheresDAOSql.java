package fr.eni.groupe1.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.bo.Retrait;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Repository
public class EncheresDAOSql implements EncheresDAO{
	
	private final static String SELECT_ALL_ARTICLE = "select * from ARTICLES_VENDUS" ;
	private final static String SELECT_ALL_RETRAITS = "select * from RETRAITS" ;

	private final static String INSERT_NEW_ARTICLE = "insert into ARTICLES_VENDUS ( nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie ) values (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)" ;
	private final static String INSERT_NEW_INFORETRAIT = "insert into RETRAITS ( no_article, rue, code_postal, ville ) values (:no_article, :rue, :code_postal, :ville)" ;
	private static final String DELETE_MODIF_ARTICLE = null;
	private static final String UPDATE = null;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private UtilisateurDAO utilisateurDAO;
	private EncheresCategoriesDAO encheresCategoriesDAO; 
	
		@Autowired
		public void setNamedParameterJdbcTemplate(UtilisateurDAO utilisateurDAO,EncheresCategoriesDAO encheresCategoriesDAO, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
			this.utilisateurDAO = utilisateurDAO;
			this.encheresCategoriesDAO = encheresCategoriesDAO;
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
			Utilisateur vendeur = null;
//			vendeur = utilisateurDAO.findById(rs.getInt("no_utilisateur"));
//			article.setVendeur(vendeur);
//			Categorie categorie = null;
//			categorie = encheresCategoriesDAO.findById(rs.getInt("no_categorie"));
//			article.setCategorie(categorie);
			return article;
		}
		
	}
		
/////////////////////////////////       AFFICHAGE     ////////////////////////////////////////////
		
		@Override
		public List<ArticleVendu> articleAll() {
			List<ArticleVendu> listArticle;
			
			System.out.println("Dans articleAll()");
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
				System.out.println("article : " + article.toString());
				
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
		