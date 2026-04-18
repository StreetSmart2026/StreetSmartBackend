package com.streetsmart.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class PostPointConverter implements AttributeConverter<PostPoint, String> {

	@Override
	public String convertToDatabaseColumn(PostPoint attribute) {
		if (attribute == null) {
			return null;
		}

		return "(" + attribute.getX() + "," + attribute.getY() + ")";
	}

	@Override
	public PostPoint convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.isBlank()) {
			return null;
		}

		String pointValue = dbData.trim().replace("(", "").replace(")", "");
		String[] coordinates = pointValue.split(",");

		if (coordinates.length != 2) {
			throw new IllegalArgumentException("Invalid point value returned from database.");
		}

		return new PostPoint(Double.parseDouble(coordinates[0].trim()),
				Double.parseDouble(coordinates[1].trim()));
	}

}
