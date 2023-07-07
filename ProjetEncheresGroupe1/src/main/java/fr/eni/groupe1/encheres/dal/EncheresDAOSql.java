package fr.eni.groupe1.encheres.dal;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Enchere;
import fr.eni.groupe1.encheres.bo.Retrait;
import fr.eni.groupe1.encheres.bo.Utilisateur;
import fr.eni.groupe1.encheres.dal.UtilisateurDAOSql.UtilisateursRowMapper;

@Repository
public class EncheresDAOSql implements EncheresDAO {

	private final static String SELECT_ALL_ARTICLE = "select * from ARTICLES_VENDUS";
	private final static String SELECT_ALL_RETRAITS = "select * from RETRAITS";

	private final static String SELECT_ARTICLE_BY_CATEGORIE = "SELECT * FROM ARTICLES_VENDUS WHERE no_categorie =:noCategorie";
	private final static String SELECT_ARTICLE_BY_NAME = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE '%' + :nomArticle + '%'";
	private final static String SELECT_ARTICLE_BY_ID = "SELECT * FROM ARTICLES_VENDUS WHERE no_article =:noArticle";
	private final static String SELECT_ARTICLE_BY_NAME_AND_CATEGORY = "SELECT * FROM ARTICLES_VENDUS WHERE nom_article LIKE '%' + :nomArticle + '%' AND no_categorie=:noCategorie";

	private static final String SELECT_COUNT_ENCHEREPARAM_BY_ID = "SELECT COUNT(*) FROM ENCHERES WHERE no_article = ?";
	private static final String SELECT_COUNT_ENCHERE_BY_ID = "SELECT COUNT(*) FROM ENCHERES WHERE no_article = ?";

	private static final String SELECT_RETRAIT_BY_ID = "SELECT * FROM RETRAITS WHERE no_article =:noArticle";
	private static final String SELECT_ENCHERE_BY_ID = "SELECT * FROM ENCHERES WHERE no_article =:noArticle";

	private final static String INSERT_NEW_ARTICLE = "insert into ARTICLES_VENDUS ( nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie ) values (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)";
	private final static String INSERT_NEW_INFORETRAIT = "insert into RETRAITS ( no_article, rue, code_postal, ville ) values (:no_article, :rue, :code_postal, :ville)";
	private static final String INSERT_NEW_ENCHERE = "insert into ENCHERES ( no_utilisateur, no_article, date_enchere, montant_enchere ) values (:no_utilisateur, :no_article, :date_enchere, :montant_enchere)";

	private static final String UPDATE_NEW_ENCHERE = "update ENCHERES set no_utilisateur=:no_utilisateur,no_article=:no_article,date_enchere=:date_enchere, montant_enchere=:montant_enchere WHERE no_article =:no_article";
	private static final String UPDATE_NEW_ARTICLE = "update ARTICLES_VENDUS set prix_vente=:prix_vente WHERE no_article =:no_article";
	private static final String UPDATE_MODIF_ARTICLE = "update ARTICLES_VENDUS set nom_article=:nom_article, description=:description, date_debut_encheres=:date_debut_encheres, date_fin_encheres=:date_fin_encheres, no_categorie=:no_categorie  WHERE no_article =:no_article";
	private static final String UPDATE_MODIF_RETRAIT = "update RETRAITS set rue=:rue, code_postal=:code_postal, ville=:ville WHERE no_article =:no_article";;

	private static final String UPDATE_NEW_ARTICLE_ACHETEUR = "update ARTICLES_VENDUS set no_utilisateur=:no_utilisateur WHERE no_article =:no_article";
	private static final String UPDATE_CREDIT_UTILISATEUR = "update UTILISATEURS set credit=:credit WHERE no_utilisateur =:no_utilisateur";
	private static final String DELETE_ARTICLE =  "delete ARTICLES_VENDUS where no_article=:noArticle";
	private static final String DELETE_ENCHERE =  "delete ENCHERES where no_article=:noArticle";
	private static final String DELETE_RETRAIT =  "delete RETRAITS where no_article=:noArticle";

//	private static final String DELETE_MODIF_ARTICLE = null;
//	private static final String UPDATE = null;

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private UtilisateurDAO utilisateurDAO;
	private EncheresCategoriesDAO encheresCategoriesDAO;

	@Autowired
	public void setNamedParameterJdbcTemplate(UtilisateurDAO utilisateurDAO,
			EncheresCategoriesDAO encheresCategoriesDAO, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.utilisateurDAO = utilisateurDAO;
		this.encheresCategoriesDAO = encheresCategoriesDAO;
	}

/////////////////////////////////       AFFICHAGE ALL     ////////////////////////////////////////////

	@Override
	public List<ArticleVendu> articleAll() {
		List<ArticleVendu> listArticle;

		System.out.println("Dans articleAll()");
		listArticle = namedParameterJdbcTemplate.query(SELECT_ALL_ARTICLE,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
		return listArticle;
	}

	@Override
	public List<Retrait> retraitAll() {
		List<Retrait> listRetrait;
		listRetrait = namedParameterJdbcTemplate.query(SELECT_ALL_RETRAITS, new BeanPropertyRowMapper<>(Retrait.class));
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

		listArticle = namedParameterJdbcTemplate.query(SELECT_ARTICLE_BY_CATEGORIE, params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
		System.out.println("liste articles = " + listArticle.toString());

		return listArticle;
	}

	@Override
	public List<ArticleVendu> articleByName(String nomArticle) {
		System.out.println("dans la méthode article by name de la DAO, nomArticle = " + nomArticle);
		List<ArticleVendu> listArticle;

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nomArticle", nomArticle);

		listArticle = namedParameterJdbcTemplate.query(SELECT_ARTICLE_BY_NAME, params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));

		System.out.println("dans la methode articleByName de la DAO, listArticle = " + listArticle);

		return listArticle;
	}

	@Override
	public List<ArticleVendu> articleByNameAndCategorie(String nomArticle, Integer noCategorie) {
		List<ArticleVendu> listArticle;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("nomArticle", nomArticle);
		params.addValue("noCategorie", noCategorie);

		listArticle = namedParameterJdbcTemplate.query(SELECT_ARTICLE_BY_NAME_AND_CATEGORY, params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
		return listArticle;
	}

/////////////////////////////////       BY ID     ////////////////////////////////////////////

	public ArticleVendu articleById(int id) {

		ArticleVendu article;

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", id);
		System.out.println("avant le query");
		article = namedParameterJdbcTemplate.queryForObject(SELECT_ARTICLE_BY_ID, params,
				new ArticlesVendusRowMapper(utilisateurDAO, encheresCategoriesDAO));
		System.out.println("après le query");
		System.out.println("dans la methode articleById de la DAO, listArticle = " + article);
		return article;
	}

	@Override
	public Retrait retraitById(int id) {

		Retrait retrait;

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", id);

		retrait = namedParameterJdbcTemplate.queryForObject(SELECT_RETRAIT_BY_ID, params,
				new BeanPropertyRowMapper<>(Retrait.class));

		System.out.println("dans la methode RetraitById de la DAO, listRetrait = " + retrait);
		return retrait;
	}

	@Override
	public Enchere enchereById(int id) {
		Enchere enchere;
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", id);
		int countEnchere = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(SELECT_COUNT_ENCHERE_BY_ID,
				Integer.class, id);
		if (countEnchere != 0) {
			enchere = namedParameterJdbcTemplate.queryForObject(SELECT_ENCHERE_BY_ID, params,
					new BeanPropertyRowMapper<>(Enchere.class));

			return enchere;
		} else {
			return null;
		}
	}
/////////////////////////////////       SET  ARTICLE RETRAIT   ////////////////////////////////////////////

	@Override
	public void setArticle(ArticleVendu article) {
		MapSqlParameterSource newArticleMap = new MapSqlParameterSource();
		if (article.getNoArticle() != null) {
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

		if (article.getNoArticle() == null) {

			namedParameterJdbcTemplate.update(INSERT_NEW_ARTICLE, newArticleMap, keyHolder);
			article.setNoArticle(keyHolder.getKey().intValue());

		} // else {// update
//
//			MapSqlParameterSource articleModifMap = new MapSqlParameterSource();
//			articleModifMap.addValue("no_article", article.noArticle);
//			namedParameterJdbcTemplate.update(DELETE_MODIF_ARTICLE, articleModifMap);
//			namedParameterJdbcTemplate.update(UPDATE, newArticleMap);
//		}
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
/////////////////////////////////      AJOUTE ENCHERE   //////////////////////////////////////////////////////////////

	@Override
	public void ajouterEnchere(Principal principal, ArticleVendu article, Enchere infoEncheres) {

		MapSqlParameterSource newEnchereMap = new MapSqlParameterSource();
		MapSqlParameterSource modifEnchereMap = new MapSqlParameterSource();
		MapSqlParameterSource modifArticleMap = new MapSqlParameterSource();
		MapSqlParameterSource newUtilisateurMap = new MapSqlParameterSource();
		MapSqlParameterSource modifUserCreditMap = new MapSqlParameterSource();

		Utilisateur acheteur;
		LocalDate date = LocalDate.now();
		int idArticle = article.noArticle;
		acheteur = utilisateurDAO.findByPseudo(principal.getName());

		int creditUtilisateur = ((acheteur.getCredit()) - (infoEncheres.getMontantEnchere()));

///////////////////////////////// REDUCTION CREDIT UTILISATEUR		
		newUtilisateurMap.addValue("no_utilisateur", acheteur.getNoUtilisateur());
		newUtilisateurMap.addValue("credit", creditUtilisateur);
		namedParameterJdbcTemplate.update(UPDATE_CREDIT_UTILISATEUR, newUtilisateurMap);

///////////////////////////////// AJOUT ENCHERE		

		int countEnchere = namedParameterJdbcTemplate.getJdbcOperations()
				.queryForObject(SELECT_COUNT_ENCHEREPARAM_BY_ID, Integer.class, idArticle);

		if (countEnchere == 0) {

			newEnchereMap.addValue("no_utilisateur", acheteur.getNoUtilisateur());
			newEnchereMap.addValue("no_article", idArticle);
			newEnchereMap.addValue("date_enchere", date);
			newEnchereMap.addValue("montant_enchere", infoEncheres.getMontantEnchere());

			namedParameterJdbcTemplate.update(INSERT_NEW_ENCHERE, newEnchereMap);

			modifArticleMap.addValue("no_article", idArticle);
			modifArticleMap.addValue("prix_vente", infoEncheres.getMontantEnchere());
			namedParameterJdbcTemplate.update(UPDATE_NEW_ARTICLE, modifArticleMap);
///////////////////////////////// UPDATE ENCHERE		

		} else {

			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("noArticle", infoEncheres.getNoArticle());
			Enchere enchere = namedParameterJdbcTemplate.queryForObject(SELECT_ENCHERE_BY_ID, params,
					new BeanPropertyRowMapper<>(Enchere.class));

			Utilisateur acheteurAvant = utilisateurDAO.findById(enchere.getNoUtilisateur());
			int creditAcheteurAvant = acheteurAvant.getCredit();
			int creditAcheteurApres = creditAcheteurAvant + enchere.getMontantEnchere();
///////////////////////////////// RENDRE CREDIT		

			modifUserCreditMap.addValue("no_utilisateur", acheteurAvant.getNoUtilisateur());
			modifUserCreditMap.addValue("credit", creditAcheteurApres);
			namedParameterJdbcTemplate.update(UPDATE_CREDIT_UTILISATEUR, modifUserCreditMap);

///////////////////////////////// CHANGEMENT ENCHERE				
			modifEnchereMap.addValue("no_utilisateur", acheteur.getNoUtilisateur());
			modifEnchereMap.addValue("no_article", idArticle);
			modifEnchereMap.addValue("date_enchere", date);
			modifEnchereMap.addValue("montant_enchere", infoEncheres.getMontantEnchere());

			namedParameterJdbcTemplate.update(UPDATE_NEW_ENCHERE, modifEnchereMap);

			modifArticleMap.addValue("no_article", idArticle);
			modifArticleMap.addValue("prix_vente", infoEncheres.getMontantEnchere());
			namedParameterJdbcTemplate.update(UPDATE_NEW_ARTICLE, modifArticleMap);
		}
	}

	@Override
	public void ajoutArticleAcheteur(int id, ArticleVendu article) {
		MapSqlParameterSource newArticleAcheteurMap = new MapSqlParameterSource();
		Utilisateur acheteur;
		acheteur = utilisateurDAO.findById(id);

		newArticleAcheteurMap.addValue("no_utilisateur", acheteur.getNoUtilisateur());
		newArticleAcheteurMap.addValue("no_article", article.getNoArticle());

		namedParameterJdbcTemplate.update(UPDATE_NEW_ARTICLE_ACHETEUR, newArticleAcheteurMap);

	}
///////////////////////////////////////   MODIFIER ARTICLE ET RETRAIT  ////////////////////////////////////////////
	@Override
	public void modifierArticle(ArticleVendu articleVendu, int id) {
		MapSqlParameterSource newModifArticleMap = new MapSqlParameterSource();

		newModifArticleMap.addValue("no_article", id);
		newModifArticleMap.addValue("nom_article", articleVendu.getNomArticle());
		newModifArticleMap.addValue("description", articleVendu.getDescription());
		newModifArticleMap.addValue("date_debut_encheres", articleVendu.getDateDebutEncheres());
		newModifArticleMap.addValue("date_fin_encheres", articleVendu.getDateFinEncheres());
		newModifArticleMap.addValue("prix_initial", articleVendu.getPrixVente());
		newModifArticleMap.addValue("no_categorie", articleVendu.getNoCategorie());

		namedParameterJdbcTemplate.update(UPDATE_MODIF_ARTICLE, newModifArticleMap);

	}

	@Override
	public void modifierInfoRetrait(Retrait infoRetrait, ArticleVendu articleVendu) {
		MapSqlParameterSource newModifRetraitMap = new MapSqlParameterSource();

		newModifRetraitMap.addValue("rue", infoRetrait.getRue());
		newModifRetraitMap.addValue("code_postal", infoRetrait.getCodePostal());
		newModifRetraitMap.addValue("ville", infoRetrait.getVille());
		newModifRetraitMap.addValue("no_article", articleVendu.getNoArticle());

		namedParameterJdbcTemplate.update(UPDATE_MODIF_RETRAIT, newModifRetraitMap);

	}

	@Override
	public ArticleVendu deleteArticle(ArticleVendu article) {
		int noArticle = article.getNoArticle();
		ArticleVendu articleDelete;
		System.out.println("article no article" + noArticle);

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("noArticle", noArticle);
		articleDelete = namedParameterJdbcTemplate.queryForObject(SELECT_ARTICLE_BY_ID, params, new BeanPropertyRowMapper<>(ArticleVendu.class));
	
		namedParameterJdbcTemplate.update(DELETE_RETRAIT, new BeanPropertySqlParameterSource(articleDelete));
		namedParameterJdbcTemplate.update(DELETE_ENCHERE, new BeanPropertySqlParameterSource(articleDelete));
		namedParameterJdbcTemplate.update(DELETE_ARTICLE, new BeanPropertySqlParameterSource(articleDelete));
		return articleDelete;
	}

}
