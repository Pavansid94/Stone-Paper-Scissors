package com.stonescissorpaper.game.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stonescissorpaper.game.model.Game;

public interface GameDao extends JpaRepository<Game, Integer> {

}
