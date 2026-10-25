package com.example.movie_description;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    @Value("${gemini.api-key:}")
    private String apiKey;

    public String generateDescription(String title) {

        if(apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "Gemini API key is not configured."
            );
        }

        Client client = Client.builder().apiKey(apiKey).build();

        String prompt = "Write a short, engaging description of the movie "
                + title
                + ". Keep it to 2-3 sentences. Do not invent specific plot "
                + "details if you are unsure about the movie.";

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-flash-latest",
                        prompt,
                        null
                );

        String description = response.text();

        if (description == null || description.isBlank()) {
            throw new IllegalStateException(
                    "Gemini returned an empty description."
            );
        }

        return description.trim();
    }
}
