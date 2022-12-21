package de.fiedler;

import java.util.Collection;
import java.util.function.Function;

import javax.swing.Action;

import de.fiedler.artifacts.Weapon;
import de.fiedler.repositories.Shop;

public class OrderResult {
	private Shop shopState;
	private Player playerState;
	
	public Shop getShopState() {
		return shopState;
	}

	

	public Player getPlayerState() {
		return playerState;
	}



	private Collection<Weapon> weapons;
	
	public OrderResult(Shop forgeState,  Player player) {
		this.shopState = forgeState;
		this.playerState = player;
	}
	
}
