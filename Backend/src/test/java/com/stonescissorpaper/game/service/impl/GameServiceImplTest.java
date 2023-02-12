package com.stonescissorpaper.game.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.stonescissorpaper.game.dao.GameDao;
import com.stonescissorpaper.game.exception.GameNotFoundException;
import com.stonescissorpaper.game.exception.GameOverException;
import com.stonescissorpaper.game.model.Choice;
import com.stonescissorpaper.game.model.Game;
import com.stonescissorpaper.game.model.Round;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { GameServiceImpl.class })
@ExtendWith(SpringExtension.class)
class GameServiceImplTest {
	@MockBean
	private GameDao gameDao;

	@Autowired
	private GameServiceImpl gameServiceImpl;

	/**
	 * Method under test: {@link GameServiceImpl#start(String, String, Optional)}
	 */
	@Test
	void testStart() {
		Game game = new Game("P1 Name", "P2 Name");

		when(gameDao.save((Game) any())).thenReturn(game);
		assertSame(game, gameServiceImpl.start("Player One", "Player Two", Optional.of(42)));
		verify(gameDao).save((Game) any());
	}

	/**
	 * Method under test: {@link GameServiceImpl#start(String, String, Optional)}
	 */
	@Test
	void testStartNoRounds() {
		Game game = new Game("P1 Name", "P2 Name");

		when(gameDao.save((Game) any())).thenReturn(game);
		assertSame(game, gameServiceImpl.start("Player One", "Player Two", Optional.empty()));
		verify(gameDao).save((Game) any());
	}

	/**
	 * Method under test: {@link GameServiceImpl#play(int, Choice, Choice)}
	 */
	@Test
	void testPlay() throws GameNotFoundException, GameOverException {
		Game game = new Game("P1 Name", "P2 Name");

		when(gameDao.save((Game) any())).thenReturn(game);
		when(gameDao.findById((Integer) any())).thenReturn(Optional.of(new Game("P1 Name", "P2 Name")));
		assertSame(game, gameServiceImpl.play(1, Choice.STONE, Choice.STONE));
		verify(gameDao).save((Game) any());
		verify(gameDao).findById((Integer) any());
	}

	/**
	 * Method under test: {@link GameServiceImpl#play(int, Choice, Choice)}
	 */
	@Test
	void testPlayForGameOverException() throws GameNotFoundException, GameOverException {
		Game game = mock(Game.class);
		when(game.getNoOfRounds()).thenReturn(0);
		when(game.getRounds()).thenReturn(new ArrayList<>());
		Optional<Game> ofResult = Optional.of(game);
		when(gameDao.save((Game) any())).thenReturn(new Game("P1 Name", "P2 Name"));
		when(gameDao.findById((Integer) any())).thenReturn(ofResult);
		assertThrows(GameOverException.class, () -> gameServiceImpl.play(1, Choice.STONE, Choice.STONE));
		verify(gameDao).findById((Integer) any());
		verify(game).getNoOfRounds();
		verify(game).getRounds();
	}

	/**
     * Method under test: {@link GameServiceImpl#play(int, Choice, Choice)}
     */
    @Test
    void testPlayForGameNotFoundException() throws GameNotFoundException, GameOverException {
        when(gameDao.save((Game) any())).thenReturn(new Game("P1 Name", "P2 Name"));
        when(gameDao.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(GameNotFoundException.class, () -> gameServiceImpl.play(1, Choice.STONE, Choice.STONE));
        verify(gameDao).findById((Integer) any());
    }

	/**
	 * Method under test: {@link GameServiceImpl#play(int, Choice, Choice)}
	 */
	@Test
	void testPlayWithVerify() throws GameNotFoundException, GameOverException {
		Game game = mock(Game.class);
		when(game.getPlayerOneScore()).thenReturn(3);
		doNothing().when(game).setPlayerOneScore(anyInt());
		doNothing().when(game).setRounds((List<Round>) any());
		when(game.getNoOfRounds()).thenReturn(1);
		when(game.getRounds()).thenReturn(new ArrayList<>());
		Optional<Game> ofResult = Optional.of(game);
		Game game1 = new Game("P1 Name", "P2 Name");

		when(gameDao.save((Game) any())).thenReturn(game1);
		when(gameDao.findById((Integer) any())).thenReturn(ofResult);
		assertSame(game1, gameServiceImpl.play(1, Choice.PAPER, Choice.STONE));
		verify(gameDao).save((Game) any());
		verify(gameDao).findById((Integer) any());
		verify(game).getNoOfRounds();
		verify(game).getPlayerOneScore();
		verify(game, atLeast(1)).getRounds();
		verify(game).setPlayerOneScore(anyInt());
		verify(game).setRounds((List<Round>) any());
	}

	/**
	 * Method under test: {@link GameServiceImpl#getGame(int)}
	 */
	@Test
	void testGetGame() throws GameNotFoundException {
		Game game = new Game("P1 Name", "P2 Name");

		when(gameDao.findById((Integer) any())).thenReturn(Optional.of(game));
		assertSame(game, gameServiceImpl.getGame(1));
		verify(gameDao).findById((Integer) any());
	}

	/**
     * Method under test: {@link GameServiceImpl#getGame(int)}
     */
    @Test
    void testGetGameForException() throws GameNotFoundException {
        when(gameDao.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(GameNotFoundException.class, () -> gameServiceImpl.getGame(1));
        verify(gameDao).findById((Integer) any());
    }

	/**
	 * Method under test: {@link GameServiceImpl#updateGameMetadata(int, int)}
	 */
	@Test
	void testUpdateGameMetadata() throws GameNotFoundException {
		Game game = new Game("P1 Name", "P2 Name");

		when(gameDao.save((Game) any())).thenReturn(game);
		when(gameDao.findById((Integer) any())).thenReturn(Optional.of(new Game("P1 Name", "P2 Name")));
		assertSame(game, gameServiceImpl.updateGameMetadata(123, 1));
		verify(gameDao).save((Game) any());
		verify(gameDao).findById((Integer) any());
	}

	/**
     * Method under test: {@link GameServiceImpl#updateGameMetadata(int, int)}
     */
    @Test
    void testUpdateGameMetadataForException() throws GameNotFoundException {
        when(gameDao.save((Game) any())).thenReturn(new Game("P1 Name", "P2 Name"));
        when(gameDao.findById((Integer) any())).thenReturn(Optional.empty());
        assertThrows(GameNotFoundException.class, () -> gameServiceImpl.updateGameMetadata(123, 1));
        verify(gameDao).findById((Integer) any());
    }
}
