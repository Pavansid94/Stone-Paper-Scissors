package com.stonescissorpaper.game.model;

import lombok.Getter;

@Getter
public enum Result {

	WIN(0),

	LOSS(1),

	TIE(2);

	private final Integer value;

	Result(Integer value) {
		this.value = value;
	}
}
