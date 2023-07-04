package fr.eni.groupe1.encheres.bo;

import java.util.Date;
import java.util.Objects;

public class Enchere {

	private Date dateEnchere;
	private Integer montantEnchere;
	private Integer noUtilisateur;
	private Integer noArticle;
	private Utilisateur utilisateur;
	
	public Enchere() {
	}
	
	public Enchere(Date dateEnchere, Integer montantEnchere, Integer noUtilisateur, Integer noArticle,
			Utilisateur utilisateur) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.noUtilisateur = noUtilisateur;
		this.noArticle = noArticle;
		this.utilisateur = utilisateur;
	}


	
	

	public Enchere(Date dateEnchere, Integer montantEnchere, Integer noUtilisateur, Integer noArticle) {
		this.dateEnchere = dateEnchere;
		this.montantEnchere = montantEnchere;
		this.noUtilisateur = noUtilisateur;
		this.noArticle = noArticle;
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public Integer getMontantEnchere() {
		return montantEnchere;
	}

	public void setMontantEnchere(Integer montantEnchere) {
		this.montantEnchere = montantEnchere;
	}

	public Integer getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(Integer noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public Integer getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(Integer noArticle) {
		this.noArticle = noArticle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateEnchere, montantEnchere);
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enchere other = (Enchere) obj;
		return Objects.equals(dateEnchere, other.dateEnchere) && Objects.equals(montantEnchere, other.montantEnchere);
	}

	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", montantEnchere=" + montantEnchere + ", noUtilisateur="
				+ noUtilisateur + ", noArticle=" + noArticle + ", utilisateur=" + utilisateur + "]";
	}


	





	
}
