package com.stonescissorpaper.game.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stonescissorpaper.game.exception.GameNotFoundException;
import com.stonescissorpaper.game.exception.GameOverException;
import com.stonescissorpaper.game.model.Choice;
import com.stonescissorpaper.game.model.Game;
import com.stonescissorpaper.game.service.GameService;
import com.stonescissorpaper.game.utils.Constants;

@RestController
@RequestMapping("/api/v1/games/")
public class GameController {

	@Autowired
	private GameService gameService;

	@CrossOrigin(origins = Constants.CROSS_ORIGIN)
	@PostMapping
	public Game start(@RequestParam("playerOne") String playerOne,
			@RequestParam("noOfRounds") Optional<Integer> noOfRounds) {
		return gameService.start(playerOne, Constants.PLAYER_TWO_NAME, noOfRounds);
	}

	@CrossOrigin(origins = Constants.CROSS_ORIGIN)
	@PutMapping("/{gameId}")
	public Game play(@PathVariable(required = true) int gameId, @RequestParam("choice") Choice playerOneChoice)
			throws GameOverException, GameNotFoundException {
		Choice playerTwoChoice = Choice.getRandom();
		return gameService.play(gameId, playerOneChoice, playerTwoChoice);

	}

	@CrossOrigin(origins = Constants.CROSS_ORIGIN)
	@PutMapping("/{gameId}/rounds/{noOfRounds}")
	public Game updateGameMetadata(@PathVariable(required = true) int gameId,
			@PathVariable(required = true) int noOfRounds) throws GameNotFoundException {
		return gameService.updateGameMetadata(gameId, noOfRounds);
	}

}
