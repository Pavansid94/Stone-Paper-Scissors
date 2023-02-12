package com.stonescissorpaper.game.service;

import java.util.Optional;

import com.stonescissorpaper.game.exception.GameNotFoundException;
import com.stonescissorpaper.game.exception.GameOverException;
import com.stonescissorpaper.game.model.Choice;
import com.stonescissorpaper.game.model.Game;

public interface GameService {
	
	/**
	 * Returns the game initialized
	 * @param playerOne
	 * @param string
	 * @param noOfRounds
	 * @return the game
	 */
	Game start(String playerOne, String string, Optional<Integer> noOfRounds);
	
	/**
	 * Returns the game based on gameId
	 * @param gameId
	 * @return the game
	 * @throws GameNotFoundException
	 */
	Game getGame(int gameId) throws GameNotFoundException;
	
	/**
	 * Returns the game advancing a round
	 * @param gameId
	 * @param playerOneChoice
	 * @param playerTwoChoice
	 * @return the game 
	 * @throws GameOverException
	 * @throws GameNotFoundException
	 */
	Game play(int gameId, Choice playerOneChoice, Choice playerTwoChoice)
			throws GameOverException, GameNotFoundException;
	
	/**
	 * Returns the game updating noOfRounds
	 * @param gameId
	 * @param noOfRounds
	 * @return game 
	 * @throws GameNotFoundException
	 */
	Game updateGameMetadata(int gameId, int noOfRounds) throws GameNotFoundException;

}