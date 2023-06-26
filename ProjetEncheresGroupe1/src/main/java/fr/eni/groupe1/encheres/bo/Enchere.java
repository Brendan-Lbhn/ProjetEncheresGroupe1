package fr.eni.groupe1.encheres.bo;

import java.util.Date;
import java.util.Objects;

public class Enchere {

	private Date dateEnchere;
	private Integer montant_enchere;
	
	public Enchere() {
	}
	
	public Enchere(Date dateEnchere,Integer montant_enchere) {
		this.dateEnchere=dateEnchere;
		this.montant_enchere=montant_enchere;
	}

	public Date getDateEnchere() {
		return dateEnchere;
	}

	public void setDateEnchere(Date dateEnchere) {
		this.dateEnchere = dateEnchere;
	}

	public Integer getMontant_enchere() {
		return montant_enchere;
	}
	
	public void setMontant_enchere(Integer montant_enchere) {
		this.montant_enchere = montant_enchere;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateEnchere, montant_enchere);
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
		return Objects.equals(dateEnchere, other.dateEnchere) && Objects.equals(montant_enchere, other.montant_enchere);
	}


	@Override
	public String toString() {
		return "Enchere [dateEnchere=" + dateEnchere + ", montant_enchere=" + montant_enchere + "]";
	}
	
}
