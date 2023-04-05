package com.dh.catalog.controller;

import com.dh.catalog.client.MovieFeign;

import com.dh.catalog.client.SerieFeign;
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

	private final MovieFeign movieFeign;
	private final SerieFeign serieFeign;

	public CatalogController(MovieFeign movieFeign, SerieFeign serieFeign) {
		this.movieFeign = movieFeign;
		this.serieFeign = serieFeign;
	}

	@GetMapping("/online/{genre}")
	ResponseEntity<List> getByGenreOnline(@PathVariable String genre) {
		List<Object> listByGen = new ArrayList<>();
		listByGen.addAll(movieFeign.getMovieByGenre(genre));
		listByGen.addAll(serieFeign.getByGenre(genre));
		return ResponseEntity.ok(listByGen);
	}

	@GetMapping("/offline/{genre}")
	ResponseEntity<List> getByGenreOffline(@PathVariable String genre) {
		List<Object> listByGen = new ArrayList<>();
		// todo: add collection from mongo librery
		return ResponseEntity.ok(listByGen);
	}

	@GetMapping("/movie/{genre}")
	ResponseEntity<List<MovieFeign.MovieDto>> getMoviesGenre(@PathVariable String genre) {
		return ResponseEntity.ok(movieFeign.getMovieByGenre(genre));
	}

	@GetMapping("/series/{genre}")
	ResponseEntity<List<SerieFeign.SerieDto>> getSeriesGenre(@PathVariable String genre) {
		return ResponseEntity.ok(serieFeign.getByGenre(genre));
	}
}
