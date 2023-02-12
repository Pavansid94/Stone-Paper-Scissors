package com.stonescissorpaper.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Round {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;

	private Choice p1Choice;

	private Choice p2Choice;

	private Result p1Result;

	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Game game;

	public Round(Choice p1Choice, Choice p2Choice, Result result, Game game) {
		this.p1Choice = p1Choice;
		this.p2Choice = p2Choice;
		this.p1Result = result;
		this.game = game;
	}
}
