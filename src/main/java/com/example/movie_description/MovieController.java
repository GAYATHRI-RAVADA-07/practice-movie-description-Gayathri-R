package com.example.movie_description;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final GeminiService geminiService;

    public MovieController(
            MovieRepository movieRepository,
            GeminiService geminiService) {
        this.movieRepository = movieRepository;
        this.geminiService = geminiService;

    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        String description = geminiService.generateDescription(movie.getTitle());
        movie.setDescription(description);

        return movieRepository.save(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

}
