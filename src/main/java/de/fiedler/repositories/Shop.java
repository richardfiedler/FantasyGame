package de.fiedler.repositories;

// import weil aus anddrer Klasse?
import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import de.fiedler.OrderResult;
import de.fiedler.Player;
import de.fiedler.artifacts.Weapon;
import de.fiedler.artifacts.ArtifactType;
import de.fiedler.artifacts.Bow;
import de.fiedler.artifacts.Sword;
import de.fiedler.interfaces.BuyInterface;

public class Shop {

	private Collection<Sword> swords = new ArrayList<Sword>();
	private Collection<Bow> bows = new ArrayList<Bow>();

	/**
	 * @param countOfBows   - Anzahl der Bögen
	 * @param countOfSwords
	 * @param priceForBow
	 * @param priceForSword
	 */
	public Shop(Collection<Sword> swords, Collection<Bow> bows) {
		Weapon.shop = this;
		this.swords = swords;
		this.bows = bows;

	}

	public Collection<Sword> getSwords() {
		return swords;
	}

	public Collection<Bow> getBows() {
		return bows;
	}

	public Shop setSwords(Collection<Sword> swords) {
		return new Shop(swords, this.bows);
	}

	public Shop setBows(Collection<Bow> bows) {
		return new Shop(this.swords, bows);
	}

	public boolean checkIfEnoughWeaponsInShop(Collection<Weapon> artifactsToOrder) {
		List<Bow> orderedBows = this.filterBows(artifactsToOrder);

		List<Sword> orderedSwords = this.filterSwords(artifactsToOrder);

		Collection<Bow> bowsInForge = this.getBows();
		Collection<Sword> swordsInForge = this.getSwords();

		boolean enoughSwords = swordsInForge.size() >= orderedSwords.size();
		boolean enoughBows = bowsInForge.size() >= orderedBows.size();
		boolean enoughWeapons = enoughSwords && enoughBows;

		return enoughWeapons;
	}

	public Shop newShopStateAfterOrder(Collection<Weapon> artifactsToOrder) {
		List<Bow> orderedBows = this.filterBows(artifactsToOrder);
		List<Sword> orderedSwords = this.filterSwords(artifactsToOrder);
		this.swords.removeAll(orderedSwords);
		this.bows.removeAll(orderedBows);
		return this;
	}

	public List<Bow> filterBows(Collection<Weapon> artifactsToOrder) {
		List<Bow> filteredBows = artifactsToOrder
				.stream()
				.filter(artifact -> artifact instanceof Bow)
				.map(artifact -> (Bow) artifact)
				.collect(Collectors.toList());
		return filteredBows;
	}

	public List<Sword> filterSwords(Collection<Weapon> artifactsToOrder) {
		List<Sword> filteredSwords = artifactsToOrder
				.stream()
				.filter(artifact -> artifact instanceof Sword)
				.map(artifact -> (Sword) artifact)
				.collect(Collectors.toList());
		return filteredSwords;
	}
	


	/*
	 * public SellProcess sell(int countOfweaponsToSell, ArtifactType type) throws
	 * IllegalArgumentException { int countOfAvailableWeapons; Collection<Artifact>
	 * soldArtifacts = new ArrayList<Artifact>(countOfweaponsToSell); switch (type)
	 * { case SWORD: { countOfAvailableWeapons = this.swords.size();
	 * checkIfEnoughWeapons(countOfAvailableWeapons, countOfweaponsToSell);
	 * this.swords.stream().limit(countOfweaponsToSell).forEach(soldArtifacts::add);
	 * this.swords.removeAll(soldArtifacts); return new SellProcess(this,
	 * soldArtifacts); }
	 * 
	 * default: { countOfAvailableWeapons = this.bows.size();
	 * checkIfEnoughWeapons(countOfAvailableWeapons, countOfweaponsToSell);
	 * this.bows.stream().limit(countOfweaponsToSell).forEach(soldArtifacts::add);
	 * this.bows.removeAll(soldArtifacts); return new SellProcess(this,
	 * soldArtifacts);
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * 
	 * private void checkIfEnoughWeapons(int countOfAvailableWeapons, int
	 * countOfWeaponsToSell) throws IllegalArgumentException { if
	 * (countOfWeaponsToSell > countOfAvailableWeapons) { throw new
	 * IllegalArgumentException("Zu wenig Waffen auf Lager"); } }
	 */
	/*
	 * public Optional<OrderRequest> order(int countOfweaponsToSell, ArtifactType
	 * type) { int countOfAvailableWeapons; Collection<Artifact> artifactsOfType =
	 * new ArrayList<Artifact>(); switch (type) { case SWORD: {
	 * countOfAvailableWeapons = this.swords.size();
	 * artifactsOfType.addAll(this.swords); Optional<OrderRequest> sp =
	 * this.createSellProcess(countOfAvailableWeapons, countOfweaponsToSell,
	 * artifactsOfType); boolean sucessfullyCreated = sp.isPresent(); if
	 * (sucessfullyCreated) { int newSize = countOfAvailableWeapons -
	 * countOfweaponsToSell; this.swords =
	 * this.swords.stream().limit(newSize).collect(Collectors.toList()); } return
	 * sp;
	 * 
	 * }
	 * 
	 * default: { countOfAvailableWeapons = this.bows.size();
	 * artifactsOfType.addAll(this.bows); Optional<OrderRequest> sp =
	 * this.createSellProcess(countOfAvailableWeapons, countOfweaponsToSell,
	 * artifactsOfType); boolean sucessfullyCreated = sp.isPresent(); if
	 * (sucessfullyCreated) { int newSize = countOfAvailableWeapons -
	 * countOfweaponsToSell; this.bows =
	 * this.bows.stream().limit(newSize).collect(Collectors.toList()); } return sp;
	 * 
	 * } }
	 * 
	 * }
	 * 
	 * private Optional<OrderRequest> createSellProcess(int countOfAvailableWeapons,
	 * int countOfweaponsToSell, Collection<Artifact> artifacts) { boolean
	 * enoughWeapons = countOfweaponsToSell < countOfAvailableWeapons; if
	 * (enoughWeapons) { Collection<Artifact> soldArtifacts = new
	 * ArrayList<Artifact>(countOfweaponsToSell);
	 * artifacts.stream().limit(countOfweaponsToSell).forEach(soldArtifacts::add);
	 * OrderRequest sp = new OrderRequest(this, soldArtifacts); return
	 * Optional.of(sp); } return Optional.empty();
	 * 
	 * }
	 */

}
