package fr.eni.groupe1.encheres.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.groupe1.encheres.bll.EncheresCategoriesService;
import fr.eni.groupe1.encheres.bo.Categorie;

@Component
public class StringToCategorie implements Converter<String, Categorie>{
EncheresCategoriesService encheresCategoriesService;
	
	public StringToCategorie(EncheresCategoriesService encheresCategoriesService) {
		this.encheresCategoriesService = encheresCategoriesService;
	}

	@Override
	public Categorie convert(String source) {
		Categorie categorieConvert;
		System.out.println("je passe par le converter string genre");
		int id_Convert = Integer.parseInt(source);
		categorieConvert = encheresCategoriesService.findById(id_Convert);
		return categorieConvert;
	}

}
