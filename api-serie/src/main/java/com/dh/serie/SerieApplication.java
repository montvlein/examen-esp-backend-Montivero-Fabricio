package com.dh.serie;

import com.dh.serie.model.Serie;
import com.dh.serie.repository.SerieRepository;
import com.dh.serie.service.SerieService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableMongoRepositories
public class SerieApplication {

	public static void main(String[] args) {
		SpringApplication.run(SerieApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(SerieRepository repository, SerieService service) {
		String baseUrl = "www.netflix.com/series";

		return (args) -> {
			if (!repository.findAll().isEmpty()) {
				return;
			}

			//Serie A terror
			List<Serie.Season.Chapter> serieASeasonAChapters = List.of(
					new Serie.Season.Chapter("Chapter A", 1, baseUrl + "/terror/1/season/1/chapter/1"),
					new Serie.Season.Chapter("Chapter B", 2, baseUrl + "/terror/1/season/1/chapter/2")
			);

			List<Serie.Season.Chapter> serieASeasonBChapters = List.of(
					new Serie.Season.Chapter("Chapter A", 1, baseUrl + "/terror/1/season/2/chapter/1"),
					new Serie.Season.Chapter("Chapter B", 2, baseUrl + "/terror/1/season/2/chapter/2")
			);

			List<Serie.Season> serieASeasons = List.of(
					new Serie.Season(1, serieASeasonAChapters),
					new Serie.Season(2, serieASeasonBChapters)
			);

			//Serie B comedia
			List<Serie.Season.Chapter> serieBSeasonAChapters = List.of(
					new Serie.Season.Chapter("Chapter A", 1, baseUrl + "/comedia/1/season/1/chapter/1"),
					new Serie.Season.Chapter("Chapter B", 2, baseUrl + "/comedia/1/season/1/chapter/2")
			);

			List<Serie.Season.Chapter> serieBSeasonBChapters = List.of(
					new Serie.Season.Chapter("Chapter A", 1, baseUrl + "/comedia/1/season/2/chapter/1"),
					new Serie.Season.Chapter("Chapter B", 2, baseUrl + "/comedia/1/season/2/chapter/2")
			);

			List<Serie.Season> serieBSeasons = List.of(
					new Serie.Season(1, serieBSeasonAChapters),
					new Serie.Season(2, serieBSeasonBChapters)
			);

			Serie serieA = new Serie(UUID.randomUUID().toString(),"Serie A", "terror", serieASeasons);
			Serie serieB = new Serie(UUID.randomUUID().toString(),"Serie B", "comedia", serieBSeasons);
			service.create(serieA);
			service.create(serieB);
		};
	}

}
