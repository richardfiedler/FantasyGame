package de.fiedler.artifacts;

import java.util.Collection;

import de.fiedler.Player;
import de.fiedler.repositories.Shop;

public abstract class Weapon {
	private int price;
	public static Shop shop;
	public abstract void action(Player player);

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
