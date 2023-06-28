package fr.eni.groupe1.encheres.bll;

import java.util.List;

import fr.eni.groupe1.encheres.bo.Categorie;

public interface EncheresCategoriesService {
	
	List<Categorie> getCategories();
	
	void ajouterCategorie(Categorie categorie); 
	
	Categorie findById(Integer id);
	

}
