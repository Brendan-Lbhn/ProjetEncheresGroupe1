package fr.eni.groupe1.encheres.bo;

import java.util.Objects;

public class Categorie {
	private Integer noCategorie;
	private String libelle;
	
	public Categorie() {
		// TODO Auto-generated constructor stub
	}
	
	public Categorie(Integer noCategorie,String libelle) {
		this.noCategorie=noCategorie;
		this.libelle=libelle;
	}

	public Integer getNoCategorie() {
		return noCategorie;
	}

	public void setNoCategorie(Integer noCategorie) {
		this.noCategorie = noCategorie;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public int hashCode() {
		return Objects.hash(libelle, noCategorie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		return Objects.equals(libelle, other.libelle) && Objects.equals(noCategorie, other.noCategorie);
	}

	@Override
	public String toString() {
		return "Categorie [noCategorie=" + noCategorie + ", libelle=" + libelle + "]";
	}

}
