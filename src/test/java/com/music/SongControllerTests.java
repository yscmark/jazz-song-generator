package com.music;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.music.controller.SongController;
import com.music.services.SpotifyService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = SongController.class)
@Import(SpotifyService.class)
public class SongControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private SpotifyService spotifyService;

    @BeforeEach
    void setUp() {
        Mono<String> responseMono = Mono.just("random-track");
        Mockito.when(spotifyService.getRandomTrack(Mockito.anyInt())).thenReturn(responseMono);
    }

    @Test
    public void testGetRandomTrackReturnsSuccess() {
        int year = 1980;
        webTestClient.get().uri("/api/songs/random?year=" + year)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("random-track");
    }

}
