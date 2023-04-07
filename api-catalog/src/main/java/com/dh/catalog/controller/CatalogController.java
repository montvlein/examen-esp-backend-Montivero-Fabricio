package com.dh.catalog.controller;

import com.dh.catalog.client.MovieFeign;

import com.dh.catalog.client.SerieFeign;
import com.dh.catalog.model.GenreResponse;
import com.dh.catalog.service.CatalogServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final CatalogServices services;

	public CatalogController(CatalogServices services) {
		this.services = services;
	}

	@GetMapping("/online/{genre}")
	@ResponseStatus(HttpStatus.OK)
	GenreResponse getByGenreOnline(@PathVariable String genre) {
		return services.getByGenreOnline(genre);
	}

	@GetMapping("/offline/{genre}")
	@ResponseStatus(HttpStatus.OK)
	GenreResponse getByGenreOffline(@PathVariable String genre) {
		return services.getByGenreOffline(genre);
	}

}
