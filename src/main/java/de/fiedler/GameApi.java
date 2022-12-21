package de.fiedler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import de.fiedler.artifacts.Weapon;
import de.fiedler.artifacts.ArtifactType;
import de.fiedler.artifacts.Bow;
import de.fiedler.artifacts.Sword;
import de.fiedler.repositories.Shop;

public class GameApi {
	public Shop shop;
	public Player playerState;

	public void initForge(int countOfBows, int countOfSwords, int priceForBow, int priceForSword) {
		ArrayList<Bow>bows = new ArrayList<Bow>();
		ArrayList<Sword>swords = new ArrayList<Sword>();
		for ( int ii = 0; ii <= countOfBows; ii++ )
		{
			Bow bow = new Bow( priceForBow );
			bows.add( bow );
		}

		for ( int ii = 0; ii <= countOfSwords; ii++ )
		{
			Sword sword = new Sword( priceForSword );
			swords.add( sword );
		}
		this.shop = new Shop( swords, bows);
	}
	
	public void initPlayer(int pieceOfGold, int fightingStrength, String playerName, Collection<Weapon>	knapsack) {
		this.playerState = new Player(pieceOfGold, fightingStrength, playerName, knapsack);
	}
	
	public void buyWeapons(Collection <Weapon> artifactsToOrder) throws IllegalArgumentException {
		Optional<OrderResult> orderTry = playerState.order(artifactsToOrder);
		OrderResult sucessfullOrder = orderTry.orElseThrow(IllegalArgumentException::new);		
		this.shop = sucessfullOrder.getShopState();
		this.playerState = sucessfullOrder.getPlayerState();
	}
	

	public void fight(Player player) {
		player.fight();
	}
}
