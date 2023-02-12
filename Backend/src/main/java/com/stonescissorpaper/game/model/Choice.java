package com.stonescissorpaper.game.model;

import java.util.Random;

public enum Choice {

	STONE {
		@Override
		public boolean trumps(Choice choice) {
			return (SCISSOR.equals(choice));
		}
	},

	PAPER {
		@Override
		public boolean trumps(Choice choice) {
			return (STONE.equals(choice));
		}
	},

	SCISSOR {
		@Override
		public boolean trumps(Choice choice) {
			return (PAPER.equals(choice));
		}
	};

	public static Choice getRandom() {
		int pick = new Random().nextInt(Choice.values().length);
		return Choice.values()[pick];
	}

	public abstract boolean trumps(Choice choice);

}
