package com.eduardo.ekki.ekkiTransfer.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeToTimestampConverter implements Converter<LocalDateTime, Timestamp>{

	@Override
	public Timestamp convert(LocalDateTime source) {		 
		return Timestamp.valueOf(source);
	}

}
