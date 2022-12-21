package de.fiedler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import de.fiedler.artifacts.Weapon;
import de.fiedler.artifacts.Bow;
import de.fiedler.artifacts.Sword;
import de.fiedler.repositories.Shop;

public class Player {

	private int PieceOfGold = 1000;

	private int fightingStrength;

	private String playerName;

	private Collection<Weapon> knapsack = new ArrayList<Weapon>();

	public Player(int PieceOfGold, int fightingStrength, String playerName, Collection<Weapon> knapsack) {
		this.PieceOfGold = PieceOfGold;
		this.fightingStrength = fightingStrength;
		this.playerName = playerName;
		this.knapsack = knapsack;
	}

	public int getPieceOfGold() {
		return PieceOfGold;
	}

	public Player setPieceOfGold(int pieceOfGold) {
		return new Player(pieceOfGold, this.fightingStrength, this.playerName, knapsack);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Collection<Weapon> getKnapsack() {
		return knapsack;
	}

	public Player setKnapsack(Collection<Weapon> knapsack) {
		return new Player(this.PieceOfGold, this.fightingStrength, this.playerName, knapsack);
	}

	public int getFightingStrength() {
		return fightingStrength;
	}

	public void setFightingStrength(int fightingStrength) {
		this.fightingStrength = fightingStrength;
	}

	public Player removeFromKnapsack(Weapon artifact) {
		Collection<Weapon> artifacts = this.getKnapsack();
		artifacts.remove(artifact);
		Player newPlayerState = this.setKnapsack(artifacts);
		return newPlayerState;
	}

	public void fight() {
		System.out.println("Anwendung der Gegenstände: ");

		Collection<Weapon> artifacts = this.getKnapsack();

		Collection<Weapon> artifactCopy = new ArrayList<Weapon>(this.getKnapsack().size());
		artifactCopy.addAll(this.getKnapsack());

		for (Weapon artifact : artifactCopy) {
			artifact.action(this);

		}
	}

	public Optional<OrderResult> order(Collection<Weapon> artifactsToOrder) {
		boolean artifactsToOrderExist = artifactsToOrder.isEmpty()? false : true;
		if(!artifactsToOrderExist) {
			return Optional.empty();
		}
		Shop weaponShop = Weapon.shop;
		boolean enoughWeapons = weaponShop.checkIfEnoughWeaponsInShop(artifactsToOrder);
		if (enoughWeapons) {
			OrderResult order = this.createOrder(artifactsToOrder,weaponShop);
			return Optional.of(order);
		}
		return Optional.empty();
	}

	private OrderResult createOrder(Collection<Weapon> artifactsToOrder, Shop weaponShop) {
		Player newPlayer = this.newPlayerStateAfterOrder(artifactsToOrder, this, weaponShop);
		Shop newShop = weaponShop.newShopStateAfterOrder(artifactsToOrder);
		return new OrderResult(newShop, newPlayer);
	}
	
	private Player newPlayerStateAfterOrder(Collection<Weapon> artifactsToOrder, Player playerToUpdate, Shop weaponShop) {
		playerToUpdate = updateKnapsack(artifactsToOrder, playerToUpdate);
		return updateGold(artifactsToOrder, playerToUpdate, weaponShop);
	}

	private Player updateKnapsack(Collection<Weapon> artifactsToOrder, Player playerToUpdate) {
		Collection<Weapon> newKnapsack = playerToUpdate.getKnapsack();
		newKnapsack.addAll(artifactsToOrder);
		return playerToUpdate.setKnapsack(newKnapsack);
	}

	private Player updateGold(Collection<Weapon> artifactsToOrder, Player player, Shop weaponShop) {
		List<Bow> bowsToOrder = weaponShop.filterBows(artifactsToOrder);
		List<Sword> swordsToOrder = weaponShop.filterSwords(artifactsToOrder);
		int numberOfBows = bowsToOrder.size();
		int numberOfSwords =swordsToOrder.size();
		int goldBeforeBuy = player.getPieceOfGold();
		int pricePerSword = swordsToOrder.isEmpty() ? 0 : swordsToOrder.get(0).getPrice();
		int pricePerBow = bowsToOrder.isEmpty()  ? 0 : bowsToOrder.get(0).getPrice();
		int payedAmount = (numberOfBows * pricePerBow) + (numberOfSwords * pricePerSword);
		int newAmountOfGold = goldBeforeBuy - payedAmount;
		return player.setPieceOfGold(newAmountOfGold);
	}

}
