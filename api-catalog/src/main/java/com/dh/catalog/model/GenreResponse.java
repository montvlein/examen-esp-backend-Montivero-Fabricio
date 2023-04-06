package com.dh.catalog.model;

import com.dh.catalog.client.MovieFeign;
import com.dh.catalog.client.SerieFeign;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenreResponse {
    private String genre;
    private List<MovieFeign.MovieDto> movies = new ArrayList<>();
    private List<SerieFeign.SerieDto> series = new ArrayList<>();

}
