package fr.eni.groupe1.encheres.bll;

import org.springframework.security.crypto.password.*;

import java.security.Principal;
import java.util.List;
import org.springframework.stereotype.Service;
import fr.eni.groupe1.encheres.bo.Utilisateur;
import fr.eni.groupe1.encheres.dal.UtilisateurDAO;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurDAO utilisateurDAO;
    private PasswordEncoder passwordEncoder;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, PasswordEncoder passwordEncoder) {
        this.utilisateurDAO = utilisateurDAO;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void enregistrer(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
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

	@Override
	public Utilisateur findByPseudo(String name) {
		return utilisateurDAO.findByPseudo(name);
	}

	@Override
	public Utilisateur deleteProfil(String name) {
		return utilisateurDAO.deleteProfil(name);		
	}

}
