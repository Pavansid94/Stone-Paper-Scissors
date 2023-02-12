package com.stonescissorpaper.game.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Game {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String playerOne;
	
	private String playerTwo;
	
	private int playerOneScore;
	
	private int playerTwoScore;
	
	private int noOfRounds;
	
	@OneToMany(cascade=CascadeType.ALL)
    private List<Round> rounds;
	
	public Game(String p1Name, String p2Name, int noOfRounds) {
        this.playerOne = p1Name;
        this.playerTwo = p2Name;
        this.noOfRounds = noOfRounds;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.rounds = new ArrayList<>();
    }

	public Game(String p1Name, String p2Name) {
		this.playerOne = p1Name;
        this.playerTwo = p2Name;
        this.noOfRounds = 9999; //for unlimited rounds
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        this.rounds = new ArrayList<>();
	}

}
