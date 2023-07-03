package fr.eni.groupe1.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Utilisateur;

@Repository
public class UtilisateurDAOSql implements UtilisateurDAO {
	private final static String SELECT_ALL_UTILISATEURS = "Select * from UTILISATEURS";
	private final static String FIND_BY_ID = "select * from UTILISATEURS where no_utilisateur=:id";
	private final static String FIND_BY_PSEUDO = "select * from UTILISATEURS where pseudo=?";

	private final static String INSERT = "insert into UTILISATEURS ( pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe,credit,administrateur ) values (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse,:credit,:administrateur)";
	private static final String DELETE = "delete UTILISATEURS where no_Utilisateur= :noUtilisateur";
	private static final String UPDATE = "update UTILISATEURS set pseudo=:pseudo, nom=:nom, prenom=:prenom, email=:email, telephone=:telephone, rue=:rue, code_postal=:codePostal, ville=:ville, mot_de_passe=:motDePasse where no_Utilisateur=:noUtilisateur";
	private final static String CHECK_PSEUDO = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = ?";
	private final static String CHECK_EMAIL = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = ?";
	

	@Autowired
	private NamedParameterJdbcTemplate njt;

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate njt) {
		this.njt = njt;
	}

	class UtilisateursRowMapper implements RowMapper<Utilisateur> {

		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur user = new Utilisateur();
			System.out.println("Dans UtilisateurRowMapper");
			user.setNoUtilisateur(rs.getInt("no_utilisateur"));
			user.setPseudo(rs.getString("pseudo"));
			user.setNom(rs.getString("nom"));
			user.setPrenom(rs.getString("prenom"));
			user.setEmail(rs.getString("email"));
			user.setTelephone(rs.getString("telephone"));
			user.setRue(rs.getString("rue"));
			user.setCodePostal(rs.getInt("code_postal"));
			user.setVille(rs.getString("ville"));
			user.setMotDePasse(rs.getString("mot_de_passe"));
			user.setCredit(rs.getInt("credit"));
			user.setAdministrateur(rs.getInt("administrateur"));
			return user;
		}
	}

	@Override
	public void save(Utilisateur utilisateur) {
		if (utilisateur.getNoUtilisateur() == null) {
			//Test d'unicité mot de passe
//			String pseudo = utilisateur.getPseudo();
//			Map<String, Object> params = new HashMap<>();
//	        params.put("pseudo", pseudo);
	        //TODO il aurait été préférable de faire dans le DAO une méthode pour chaque comptage
	        StringBuilder errors = new StringBuilder();
	        int countPseudo = njt.getJdbcOperations().queryForObject(CHECK_PSEUDO, Integer.class,utilisateur.getPseudo());
	        if (countPseudo > 0) {
	            errors.append("Le pseudo est déjà utilisé par un autre utilisateur. ");
	        	//throw new IllegalStateException("Le pseudo est déjà utilisé par un autre utilisateur.");
	        }
	        
	        int countEmail = njt.getJdbcOperations().queryForObject(CHECK_EMAIL, Integer.class, utilisateur.getEmail());
	        if (countEmail > 0) {
	        	errors.append("L'email est déjà utilisé par un autre utilisateur.");
	            //throw new IllegalStateException("L'email est déjà utilisé par un autre utilisateur.");
	        }
	        if (errors.length()>0) {
	        	throw new IllegalStateException(errors.toString());
	        }
			
			// Insertion utilisateur
			KeyHolder keyHolder = new GeneratedKeyHolder();
			njt.update(INSERT, new BeanPropertySqlParameterSource(utilisateur), keyHolder);
			utilisateur.setNoUtilisateur(keyHolder.getKey().intValue());
			System.out.println("insert de l'utilisateur : " + utilisateur);
			// mise à jours
		} else {// update
			System.out.println("je passe par le update du set utilisateur");
			// njt.update(DELETE_MODIF, new BeanPropertySqlParameterSource(utilisateur));
			njt.update(UPDATE, new BeanPropertySqlParameterSource(utilisateur));

		}

	}

	@Override
	public List<Utilisateur> findAll() {
		List<Utilisateur> listUtilisateur;

		System.out.println("Dans findAll()");
		listUtilisateur = njt.query(SELECT_ALL_UTILISATEURS, new UtilisateursRowMapper());
		System.out.println(listUtilisateur);
		return listUtilisateur;
	}

	@Override
	public Utilisateur findById(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		Utilisateur user = null;

		user = njt.queryForObject(FIND_BY_ID, params, new UtilisateursRowMapper());

		return user;

	}

	// @Override
	public Utilisateur findByPseudo(String pseudo) {

		Utilisateur user = null;

		// user = njt.queryForObject(FIND_BY_ID, params, new UtilisateursRowMapper());
		user = njt.getJdbcOperations().queryForObject(FIND_BY_PSEUDO, new UtilisateursRowMapper(), pseudo);
		System.out.println(user);

		return user;

	}
	@Override
	public List<Utilisateur> VendeurByName(String nomVendeur) {
		System.out.println("dans la méthode vendeur by name de la DAO, nomArticle = " + nomVendeur);
		List<Utilisateur> listVendeur;
					
		MapSqlParameterSource params = new MapSqlParameterSource(); 
		params.addValue("pseudo", nomVendeur); 
		
		listVendeur = njt.query(FIND_BY_PSEUDO, params, new UtilisateursRowMapper());
		
		System.out.println("dans la methode articleByName de la DAO, listArticle = " + listVendeur);
		
		return listVendeur;
	}

	@Override
	public Utilisateur deleteProfil(String pseudo) {
		System.out.println("je passe dans le delete");
		Utilisateur user = null;
		user = njt.getJdbcOperations().queryForObject(FIND_BY_PSEUDO, new UtilisateursRowMapper(), pseudo);
		njt.update(DELETE, new BeanPropertySqlParameterSource(user));
		return user;
	}

}
