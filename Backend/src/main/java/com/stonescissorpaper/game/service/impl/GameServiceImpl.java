package com.stonescissorpaper.game.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.stonescissorpaper.game.dao.GameDao;
import com.stonescissorpaper.game.exception.GameNotFoundException;
import com.stonescissorpaper.game.exception.GameOverException;
import com.stonescissorpaper.game.model.Choice;
import com.stonescissorpaper.game.model.Result;
import com.stonescissorpaper.game.model.Round;
import com.stonescissorpaper.game.model.Game;
import com.stonescissorpaper.game.service.GameService;
import com.stonescissorpaper.game.utils.Constants;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private GameDao gameDao;
	
	@Override
	public Game start(String playerOne, String playerTwo, Optional<Integer> noOfRounds) {
		Game game;
		if (noOfRounds.isPresent()) {
			game = new Game(playerOne, playerTwo, noOfRounds.get());
		} else {
			//set unlimited rounds
			game = new Game(playerOne, playerTwo);
		}
		return gameDao.save(game);
	}

	@Override
	public Game play(int id, Choice playerOneChoice, Choice playerTwoChoice)
			throws GameOverException, GameNotFoundException {

		Game game = checkRounds(id, playerOneChoice, playerTwoChoice);
		//increment scores for the new round
		changeScores(game.getRounds().get(game.getRounds().size() - 1), game);
		return gameDao.save(game);
	}

	@Override
	public Game getGame(int id) throws GameNotFoundException {
		return gameDao.findById(id)
				.orElseThrow(() -> new GameNotFoundException(" Game with Id %d not found".formatted(id)));

	}
	
	@Override
	public Game updateGameMetadata(int gameId, int noOfRounds) throws GameNotFoundException {
		Game game = getGame(gameId);
		//update noOfRounds
		game.setNoOfRounds(noOfRounds);
		return gameDao.save(game);
	}
	
	private void changeScores(Round round, Game game) {
		if (round.getP1Result().equals(Result.WIN)) {
			game.setPlayerOneScore(game.getPlayerOneScore() + 1);
		} else if (round.getP1Result().equals(Result.LOSS)) {
			game.setPlayerTwoScore(game.getPlayerTwoScore() + 1);
		}
	}
	
	private Game checkRounds(int id, Choice playerOneChoice, Choice playerTwoChoice)
			throws GameNotFoundException, GameOverException {
		Game game = getGame(id);
		if (game.getRounds().size() < game.getNoOfRounds()) { //add only if you've not played enough rounds
			Result result = evaluateChoices(playerOneChoice, playerTwoChoice);
			Round round = new Round(playerOneChoice, playerTwoChoice, result, game);

			if (CollectionUtils.isEmpty(game.getRounds())) {
				List<Round> rounds = new ArrayList<>();
				game.setRounds(rounds);
			}
			game.getRounds().add(round);
			return game;
		} else
			throw new GameOverException(Constants.GAME_OVER_EXCEPTION);

	}
	
	private Result evaluateChoices(Choice playerOne, Choice playerTwo) {
		Result result = Result.TIE;
		if (playerOne.trumps(playerTwo)) {
			result = Result.WIN;
		} else if (playerTwo.trumps(playerOne)) {
			result = Result.LOSS;
		}
		return result;
	}

}
