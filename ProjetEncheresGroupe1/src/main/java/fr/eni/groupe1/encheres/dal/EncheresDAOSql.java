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
	private final static String SELECT_ARTICLE_BY_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_categorie =:noCategorie";
	private final static String SELECT_ARTICLE_BY_NAME = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE '%' + :nomArticle + '%'";
	private final static String SELECT_ARTICLE_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article =:noArticle";

	private final static String SELECT_ARTICLE_BY_NAME_AND_CATEGORY = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE '%' + :nomArticle + '%' AND no_categorie=:noCategorie";
	private final static String SELECT_ALL_RETRAITS = "select * from RETRAITS" ;

	private final static String INSERT_NEW_ARTICLE = "insert into ARTICLES_VENDUS ( nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie ) values (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)" ;
	private final static String INSERT_NEW_INFORETRAIT = "insert into RETRAITS ( no_article, rue, code_postal, ville ) values (:no_article, :rue, :code_postal, :ville)" ;
	private static final String DELETE_MODIF_ARTICLE = null;
	private static final String UPDATE = null;
	private static final String SELECT_RETRAIT_BY_ID = "SELECT * FROM RETRAITS WHERE no_article =:noArticle";;
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private UtilisateurDAO utilisateurDAO;
	private EncheresCategoriesDAO encheresCategoriesDAO; 
	
		@Autowired
		public void setNamedParameterJdbcTemplate(UtilisateurDAO utilisateurDAO,EncheresCategoriesDAO encheresCategoriesDAO, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
			this.utilisateurDAO = utilisateurDAO;
			this.encheresCategoriesDAO = encheresCategoriesDAO;
		}
		
		
/////////////////////////////////       AFFICHAGE     ////////////////////////////////////////////
		
		@Override
		public List<ArticleVendu> articleAll() {
			List<ArticleVendu> listArticle;
			
			System.out.println("Dans articleAll()");
			listArticle = namedParameterJdbcTemplate.query(SELECT_ALL_ARTICLE, new ArticlesVendusRowMapper(utilisateurDAO,encheresCategoriesDAO)) ;		
			return listArticle;
		}
		
		
		@Override
		public List<Retrait> retraitAll() {
			List<Retrait> listRetrait;
			listRetrait = namedParameterJdbcTemplate.query(SELECT_ALL_RETRAITS, new BeanPropertyRowMapper<>(Retrait.class)) ;	
			return listRetrait;
		}
/////////////////////////////////       ARTICLE BY     ////////////////////////////////////////////

		@Override
		public List<ArticleVendu> articleByCategorie(Integer noCategorie) {
			List<ArticleVendu> listArticle;
			System.out.println("Dans articlebyCategorie() , numero categorie :" + noCategorie);
			
			MapSqlParameterSource params = new MapSqlParameterSource(); 
			params.addValue("noCategorie", noCategorie); 
			System.out.println(params);
			
			listArticle = namedParameterJdbcTemplate.query(SELECT_ARTICLE_BY_CATEGORIE, params, new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
			System.out.println("liste articles = " + listArticle.toString());
			
			return listArticle;
		}
		@Override
		public List<ArticleVendu> articleByName(String nomArticle) {
			System.out.println("dans la méthode article by name de la DAO, nomArticle = " + nomArticle);
			List<ArticleVendu> listArticle;
						
			MapSqlParameterSource params = new MapSqlParameterSource(); 
			params.addValue("nomArticle", nomArticle); 
			
			listArticle = namedParameterJdbcTemplate.query(SELECT_ARTICLE_BY_NAME, params, new ArticlesVendusRowMapper(utilisateurDAO,encheresCategoriesDAO));
			
			System.out.println("dans la methode articleByName de la DAO, listArticle = " + listArticle);
			
			return listArticle;
		}
		
		
		@Override
		public List<ArticleVendu> articleByNameAndCategorie(String nomArticle, Integer noCategorie) {
			List<ArticleVendu> listArticle;
			MapSqlParameterSource params = new MapSqlParameterSource(); 
			params.addValue("nomArticle", nomArticle);
			params.addValue("noCategorie",noCategorie );
			
			listArticle = namedParameterJdbcTemplate.query(SELECT_ARTICLE_BY_NAME_AND_CATEGORY, params, new ArticlesVendusRowMapper(utilisateurDAO,encheresCategoriesDAO));
			return listArticle;
		}
/////////////////////////////////       BY ID     ////////////////////////////////////////////
		
		@Override
		public ArticleVendu articleById(int id) {
			System.out.println("je passe par le article by id");
			ArticleVendu article;
						
			MapSqlParameterSource params = new MapSqlParameterSource(); 
			params.addValue("noArticle", id); 
			System.out.println("avant le query");
			article = namedParameterJdbcTemplate.queryForObject(SELECT_ARTICLE_BY_ID, params, new ArticlesVendusRowMapper(utilisateurDAO,encheresCategoriesDAO));
			System.out.println("après le query");
			System.out.println("dans la methode articleById de la DAO, listArticle = " + article);
			return article;
		}
		@Override
		public Retrait retraitById(int id) {
			System.out.println("je passe par le retrait by id");
			Retrait retrait;
						
			MapSqlParameterSource params = new MapSqlParameterSource(); 
			params.addValue("noArticle", id); 
			
			retrait = namedParameterJdbcTemplate.queryForObject(SELECT_RETRAIT_BY_ID, params,  new BeanPropertyRowMapper<>(Retrait.class));
			
			System.out.println("dans la methode RetraitById de la DAO, listRetrait = " + retrait);
			return retrait;
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
			System.out.println(article.toString());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			System.out.println("debut du set article");
			
			if(article.getNoArticle()== null) {
				System.out.println("debut du if save "+ article);
				namedParameterJdbcTemplate.update(INSERT_NEW_ARTICLE, newArticleMap, keyHolder);
				System.out.println("juste avant le keyholder");
				article.setNoArticle( keyHolder.getKey().intValue());
			//	article.setNoArticle((Integer)keyHolder.getKeys().get("no_article"));// recuperqtion de lq vqleur de l qrticle qfin de vqloriser lq FK
				System.out.println(article.getNoArticle());
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
		