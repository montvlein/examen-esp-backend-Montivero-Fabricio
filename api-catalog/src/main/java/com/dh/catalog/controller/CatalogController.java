package com.dh.catalog.controller;

import com.dh.catalog.client.MovieFeign;

import com.dh.catalog.client.SerieFeign;
import com.dh.catalog.service.CatalogServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	ResponseEntity<List> getByGenreOnline(@PathVariable String genre) throws Exception {
		List<Object> listByGen = new ArrayList<>();
		listByGen.addAll(services.getMovieByGenre(genre));
		listByGen.addAll(services.getSerieByGenre(genre));
		return ResponseEntity.ok(listByGen);
	}

	@GetMapping("/offline/{genre}")
	ResponseEntity<List> getByGenreOffline(@PathVariable String genre) {
		List<Object> listByGen = new ArrayList<>();
		// todo: add collection from mongo librery
		return ResponseEntity.ok(listByGen);
	}

	@GetMapping("/movie/{genre}")
	ResponseEntity<List<MovieFeign.MovieDto>> getMoviesGenre(@PathVariable String genre) throws Exception {
		return ResponseEntity.ok(services.getMovieByGenre(genre));
	}

	@GetMapping("/series/{genre}")
	ResponseEntity<List<SerieFeign.SerieDto>> getSeriesGenre(@PathVariable String genre) throws Exception {
		return ResponseEntity.ok(services.getSerieByGenre(genre));
	}
}
