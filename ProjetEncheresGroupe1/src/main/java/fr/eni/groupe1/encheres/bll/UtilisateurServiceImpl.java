package fr.eni.groupe1.encheres.bll;

<<<<<<< HEAD
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
=======
import java.util.List;

import org.springframework.stereotype.Service;
>>>>>>> branch 'main' of https://github.com/Brendan-Lbhn/ProjetEncheresGroupe1.git

import fr.eni.groupe1.encheres.bo.Utilisateur;
import fr.eni.groupe1.encheres.dal.UtilisateurDAO;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurDAO utilisateurDAO;
    private PasswordEncoder passwordEncoder;

<<<<<<< HEAD
    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, PasswordEncoder passwordEncoder) {
        this.utilisateurDAO = utilisateurDAO;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void enregistrer(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        utilisateurDAO.save(utilisateur);
    }
=======
	@Override
	public void enregistrer(Utilisateur utilisateur) {
		utilisateurDAO.save(utilisateur);
	}

	@Override
	public List<Utilisateur> getUtilisateur() {
		// TODO Auto-generated method stub
		return utilisateurDAO.findAll();
	}

	@Override
	public Utilisateur findById(Integer id) {
		// TODO Auto-generated method stub
		return utilisateurDAO.findById(id);
	}

>>>>>>> branch 'main' of https://github.com/Brendan-Lbhn/ProjetEncheresGroupe1.git
}
