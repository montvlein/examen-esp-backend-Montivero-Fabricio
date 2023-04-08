package com.dh.catalog.controller;

import com.dh.catalog.client.MovieFeign;

import com.dh.catalog.client.SerieFeign;
import com.dh.catalog.exceptions.MovieServerNotResponse;
import com.dh.catalog.exceptions.SerieServerNotResponse;
import com.dh.catalog.model.CatalogResponse;
import com.dh.catalog.service.CatalogServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
	CatalogResponse getByGenreOnline(@PathVariable String genre) {
		return services.getByGenreOnline(genre);
	}

	@GetMapping("/offline/{genre}")
	@ResponseStatus(HttpStatus.OK)
	CatalogResponse getByGenreOffline(@PathVariable String genre) {
		return services.getByGenreOffline(genre);
	}

	@GetMapping("/online/{genre}/movies")
	@ResponseStatus(HttpStatus.OK)
	List<MovieFeign.MovieDto> getMoviesByGenreOnline(@PathVariable String genre) throws MovieServerNotResponse {
		return services.getMovieByGenre(genre);
	}

	@GetMapping("/online/{genre}/series")
	@ResponseStatus(HttpStatus.OK)
	List<SerieFeign.SerieDto> getSeriesByGenreOnline(@PathVariable String genre) throws SerieServerNotResponse {
		return services.getSerieByGenre(genre);
	}

	@GetMapping("/offline/{genre}/movies")
	@ResponseStatus(HttpStatus.OK)
	List<MovieFeign.MovieDto> getMoviesByGenreOffline(@PathVariable String genre) {
		return services.getMovieByGenreOffline(genre);
	}

	@GetMapping("/offline/{genre}/series")
	@ResponseStatus(HttpStatus.OK)
	List<SerieFeign.SerieDto> getSeriesByGenreOffline(@PathVariable String genre) {
		return services.getSerieByGenreOffline(genre);
	}

}
