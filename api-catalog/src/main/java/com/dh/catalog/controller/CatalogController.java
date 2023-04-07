package com.dh.catalog.controller;

import com.dh.catalog.client.MovieFeign;

import com.dh.catalog.client.SerieFeign;
import com.dh.catalog.exceptions.MovieServerNotResponse;
import com.dh.catalog.exceptions.SerieServerNotResponse;
import com.dh.catalog.model.GenreResponse;
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
	GenreResponse getByGenreOnline(@PathVariable String genre) {
		return services.getByGenreOnline(genre);
	}

	@GetMapping("/offline/{genre}")
	@ResponseStatus(HttpStatus.OK)
	GenreResponse getByGenreOffline(@PathVariable String genre) {
		return services.getByGenreOffline(genre);
	}


	//	metodos para probar el fallback del circuit breaker
	@GetMapping("/movies/{genre}")
	@ResponseStatus(HttpStatus.OK)
	List<MovieFeign.MovieDto> getMoviesByGenre(@PathVariable String genre) throws MovieServerNotResponse{
		return services.getMovieByGenre(genre);
	}

	@GetMapping("/series/{genre}")
	@ResponseStatus(HttpStatus.OK)
	List<SerieFeign.SerieDto> getSeriesByGenre(@PathVariable String genre) throws SerieServerNotResponse {
		return services.getSerieByGenre(genre);
	}

}
