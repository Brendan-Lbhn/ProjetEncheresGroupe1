package fr.eni.groupe1.encheres.bo;

import java.util.Objects;

public class Utilisateur {
	
	private Integer noUtilisateur; 
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone; 
	private String rue;
	private Integer codePostal;
	private String ville;
	private String motDePasse;
	private Integer credit=0;
	private Integer administrateur=0;
	
	public Utilisateur() {

	}
	

	public Utilisateur(Integer noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, Integer codePostal, String ville, String motDePasse, Integer credit, Integer administrateur) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
	}


	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(Integer noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public Integer getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(Integer codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Integer administrateur) {
		this.administrateur = administrateur;
	}

	@Override
	public int hashCode() {
		return Objects.hash(administrateur, codePostal, credit, email, motDePasse, noUtilisateur, nom, prenom, pseudo,
				rue, telephone, ville);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		return Objects.equals(administrateur, other.administrateur) && Objects.equals(codePostal, other.codePostal)
				&& Objects.equals(credit, other.credit) && Objects.equals(email, other.email)
				&& Objects.equals(motDePasse, other.motDePasse) && Objects.equals(noUtilisateur, other.noUtilisateur)
				&& Objects.equals(nom, other.nom) && Objects.equals(prenom, other.prenom)
				&& Objects.equals(pseudo, other.pseudo) && Objects.equals(rue, other.rue)
				&& Objects.equals(telephone, other.telephone) && Objects.equals(ville, other.ville);
	}

	@Override
	public String toString() {
		return "Utilisateur [noUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + "]";
	}

	
	

}
