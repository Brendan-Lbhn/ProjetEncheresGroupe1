package fr.eni.groupe1.encheres.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		System.out.println("je passe par le converter string date");

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		try {
			date = formatter.parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		return date;
	}

}
