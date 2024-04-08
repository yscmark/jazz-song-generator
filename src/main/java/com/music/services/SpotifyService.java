package com.music.services;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

@Service
public class SpotifyService {

	private final WebClient accountsClient;
	private final WebClient apiClient;

    public SpotifyService(WebClient.Builder webClientBuilder) {
        this.accountsClient = webClientBuilder.baseUrl("https://accounts.spotify.com")
                                         .build();
		this.apiClient = webClientBuilder.baseUrl("https://api.spotify.com/v1")
                                         .build();
    }

	public Mono<String> getSpotifyAuthToken() {
		String clientId = "4829500101294b199e4e351b57a61c9a";
		String clientSecret = "2c79bcf5a22c4df8b515371d62d46970";
		String encodedHeader = Base64.getEncoder()
			.encodeToString((clientId + ":" + clientSecret).getBytes());

		Mono<String> accessToken = accountsClient.post()
			.uri("/api/token")
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.header("Authorization", "Basic " + encodedHeader)
			.body(BodyInserters.fromFormData("grant_type", "client_credentials"))
			.retrieve()
			.bodyToMono(Map.class)
            .map(responseMap -> (String) responseMap.get("access_token"));
		return accessToken;
	}

	public Mono<String> getRandomTrack(int year) {
		Mono<String> accessToken = getSpotifyAuthToken();
		int offset = ThreadLocalRandom.current().nextInt(0, 501);
		String searchUri = "/search?q=year:" + year + "-" + (year + 9) +
			 "+genre:bebop&type=track&offset=" + offset;

		final ObjectMapper mapper = new ObjectMapper();
		
		Mono<String> randomAlbum = accessToken
			.flatMap(token -> {
				return apiClient.get()
				.uri(searchUri)
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token)
				.retrieve()
				.bodyToMono(JsonNode.class)
				.map(jsonNode ->
					jsonNode.path("tracks").path("items").path(0)
						.path("external_urls").path("spotify")
				).map(
					spotifyUrlNode -> mapper.convertValue(spotifyUrlNode, String.class)
				);
			});
		return randomAlbum;
	}
}
