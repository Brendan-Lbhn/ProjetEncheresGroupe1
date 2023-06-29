package fr.eni.groupe1.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.bo.Utilisateur;
import fr.eni.groupe1.encheres.dal.UtilisateurDAOSql.UtilisateursRowMapper;

@Repository
public class EncheresCategoriesDAOImpl implements EncheresCategoriesDAO{
	
	private static final String SELECT_ALL = "select * FROM CATEGORIES";
	private static final String FIND_BY_ID = "select * from CATEGORIES where no_categorie=:id";

	@Autowired
	private NamedParameterJdbcTemplate njt;
	
	class CategorieRowMapper implements RowMapper<Categorie>{

		@Override
		public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
			Categorie categorie = new Categorie(); 
			categorie.setNoCategorie(rs.getInt("no_categorie"));
			categorie.setLibelle(rs.getString("libelle"));
			return categorie;
		}
		
	}
	
	
	@Override
	public List<Categorie> findAll() {
		List<Categorie> listCategories = njt.query(SELECT_ALL, 
				new CategorieRowMapper());
		return listCategories;
	}

	@Override
	public void addCategorie() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Categorie findById(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id",id);
		System.out.println("je passe par le find dao");
		Categorie categorie = null;
		
		categorie = njt.queryForObject(FIND_BY_ID, 
			params, 				
			new CategorieRowMapper());
		System.out.println(categorie);
		return categorie;
	}
	

}
