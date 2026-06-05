package com.darkhole.gerenteEventos.shared.database.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "locations")
public class LocationEntity {
	@Id
	private String id;

	private String country;

	private String state;

	private String city;

	private String neighborhood;

	private String street;

	private String number;

	private String cep;
}
