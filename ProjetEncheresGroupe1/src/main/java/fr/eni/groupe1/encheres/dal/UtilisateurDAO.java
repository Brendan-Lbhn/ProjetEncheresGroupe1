package fr.eni.groupe1.encheres.dal;

import java.util.List;

import fr.eni.groupe1.encheres.bo.Utilisateur;

public interface UtilisateurDAO {
	
	void save(Utilisateur utilisateur);
	
	List<Utilisateur>findAll();
}
