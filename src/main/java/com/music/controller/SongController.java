package com.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.music.services.SpotifyService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/songs")
public class SongController {

	private final SpotifyService spotifyService;

    @Autowired
    public SongController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

	@GetMapping("/random")
	public Mono<String> getRandomTrack(@RequestParam int year) {
		return spotifyService.getRandomTrack(year);
	}

}
