package fr.eni.groupe1.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.ArticleVendu;
import fr.eni.groupe1.encheres.bo.Utilisateur;
import fr.eni.groupe1.encheres.dal.EncheresDAOSql.ArticlesVendusRowMapper;

@Repository
public class UtilisateurDAOSql implements UtilisateurDAO {
	private final static String SELECT_ALL_UTILISATEURS = "Select * from UTILISATEURS";
	
	private final static String INSERT = "insert into UTILISATEURS ( pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe,credit,administrateur ) values (:pseudo, :nom, :prenom, :email, :telephone, :rue, :codePostal, :ville, :motDePasse,:credit,:administrateur)" ;
	
	private NamedParameterJdbcTemplate njt;
	
	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate njt) {
		this.njt=njt;
	}
	
	class UtilisateursRowMapper implements RowMapper<Utilisateur>{
		
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
		KeyHolder keyHolder = new GeneratedKeyHolder();
		njt.update(INSERT,new BeanPropertySqlParameterSource(utilisateur),keyHolder);
		utilisateur.setNoUtilisateur(keyHolder.getKey().intValue());
		System.out.println("insert de l'utilisateur : "+ utilisateur);
		
	}

	@Override
	public List<Utilisateur>findAll() {
		List<Utilisateur> listUtilisateur;
		
		System.out.println("Dans findAll()");
		listUtilisateur = njt.query(SELECT_ALL_UTILISATEURS, new UtilisateursRowMapper()) ;
		System.out.println(listUtilisateur);
		return listUtilisateur;
		
	}

}
