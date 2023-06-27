package fr.eni.groupe1.encheres.converter;

import java.util.Locale;

import org.springframework.core.convert.converter.Converter;

import fr.eni.groupe1.encheres.bo.ArticleVendu;

public class StringToDateConverter implements Converter<String,ArticleVendu>{

	@Override
	public ArticleVendu convert(String source) {
		// TODO Auto-generated method stub
		return null;
	}

	
//	SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//	Date date = formatter.parse(dateInString);
//	String formattedDateString = formatter.format(date);
}
