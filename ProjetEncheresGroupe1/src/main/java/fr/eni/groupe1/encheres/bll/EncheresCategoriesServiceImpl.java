package fr.eni.groupe1.encheres.bll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.eni.groupe1.encheres.bo.Categorie;
import fr.eni.groupe1.encheres.dal.EncheresCategoriesDAO;

@Service // Suppression du nommqge du beqn q voir de l utilite qlors au il y q un seul beqn instqncier pour ce service
public class EncheresCategoriesServiceImpl implements EncheresCategoriesService{

	EncheresCategoriesDAO encheresCategoriesDAO; 
	
	@Autowired // pqs utile qvec les @COMPONENT, SERVICE, REPOSITORIE ETC, si constructeur, spring vqs tenter des fqire les injection de lui mm
	public EncheresCategoriesServiceImpl(EncheresCategoriesDAO encheresCategoriesDAO) {
		this.encheresCategoriesDAO = encheresCategoriesDAO;
	}
	
	@Override
	public List<Categorie> getCategories() {
		List<Categorie>listeCategories = encheresCategoriesDAO.findAll();
		return listeCategories;
	}

	@Override
	public void ajouterCategorie(Categorie categorie) {
		encheresCategoriesDAO.addCategorie();
		
	}

	@Override
	public Categorie findById(Integer id) {
		return encheresCategoriesDAO.findById(id);
	}
	
	

}
