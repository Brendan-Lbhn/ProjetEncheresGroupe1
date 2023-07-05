package fr.eni.groupe1.encheres.dal;


import java.util.List;

import fr.eni.groupe1.encheres.bo.ArticleVendu;

public interface EncheresDAOFiltres {
	List<ArticleVendu> articleByFilter(Integer filtre, boolean encheresOuvertes, boolean encheresEnCours,
			boolean encheresRemportees, boolean ventesEnCours, boolean ventesNonDebutees, boolean ventesTerminees, int userId);

}
