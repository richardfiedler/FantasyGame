package testGameApi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.junit.jupiter.api.Test;

import de.fiedler.GameApi;
import de.fiedler.artifacts.Weapon;
import de.fiedler.artifacts.ArtifactType;
import de.fiedler.artifacts.Bow;
import de.fiedler.artifacts.Sword;
import junit.framework.Assert;

public class TestGameApi {
	GameApi game = new GameApi();
	
	@Test
	void testForgeInitialisation(){
		Random random = new Random( );
		int countOfBows = random.nextInt( 100 ) + 100;
		int countOfSwords = random.nextInt( 100 ) + 100;
		int priceForBow  = random.nextInt( 10) ;
		int priceForSword  = random.nextInt( 10);
		game.initForge(countOfBows,countOfSwords,priceForBow,priceForSword);
		assertEquals(game.shop.getBows().size()-1, countOfBows);
		assertEquals(game.shop.getSwords().size()-1, countOfSwords);
		assertEquals(game.shop.getBows().stream().findFirst().get().getPrice(), priceForBow);
		assertEquals(game.shop.getSwords().stream().findFirst().get().getPrice(), priceForSword);
	}
	
	@Test
	void testPlayerInitialisation(){
		Random random = new Random( );
		int pieceOfGold = random.nextInt( 100 ) + 100;
		int fightingStrength = random.nextInt( 10 );
		int countOfBowsInKnapsack = random.nextInt( 5 );
		int countOfSwordsInKnapsack = random.nextInt( 5 );
		String playerName  = "test" ;
		int priceForSword  = random.nextInt( 10) ;
		int priceForBow  = random.nextInt( 10) ;
		
		this.initPlayer(pieceOfGold, fightingStrength, countOfBowsInKnapsack, countOfSwordsInKnapsack, playerName, priceForSword, priceForBow);
		assertEquals(game.playerState.getPieceOfGold(), pieceOfGold);
		assertEquals(game.playerState.getFightingStrength(), fightingStrength);
		int expectedSizeOfKnapsack = countOfBowsInKnapsack + countOfSwordsInKnapsack;
		assertEquals(game.playerState.getKnapsack().size(), expectedSizeOfKnapsack);
	}
	
	@Test
	void testBuyWeapons(){
		Random random = new Random( );
		int pieceOfGold = random.nextInt( 100 ) + 100;
		int fightingStrength = random.nextInt( 10 );
		int countOfBowsInKnapsack = random.nextInt( 5 );
		int countOfSwordsInKnapsack = random.nextInt( 5 );
		String playerName  = "test" ;
		int priceForSword  = random.nextInt( 10) ;
		int priceForBow  = random.nextInt( 10) ;
		int countOfBows = random.nextInt( 100 ) + 100;
		int countOfSwords = random.nextInt( 100 ) + 100;
		int numberOfBowsToBuy =  random.nextInt( 5) + 1;
		
		this.initPlayer(pieceOfGold, fightingStrength, countOfBowsInKnapsack, countOfSwordsInKnapsack, playerName, priceForSword, priceForBow);
		game.initForge(countOfBows,countOfSwords,priceForBow,priceForSword);
		
		int moneyBeforeBuy = game.playerState.getPieceOfGold();
		int knapsackSizeBeforeBuy = game.playerState.getKnapsack().size();
		
		
		ArrayList<Weapon>bowsToOrder = this.generateBowsToBuy(priceForBow,numberOfBowsToBuy);
		
		game.buyWeapons(bowsToOrder);
		
		int payedMoney = numberOfBowsToBuy * priceForBow;
		int moneyAfterBuy = game.playerState.getPieceOfGold();
		int knapsackSizeAfterBuy = game.playerState.getKnapsack().size();
		assertEquals(moneyBeforeBuy, moneyAfterBuy + payedMoney);
		assertEquals(knapsackSizeAfterBuy, knapsackSizeBeforeBuy + numberOfBowsToBuy);
		
		numberOfBowsToBuy = 1000;
		bowsToOrder = this.generateBowsToBuy(priceForBow,numberOfBowsToBuy);
		try {
			game.buyWeapons(bowsToOrder);
		    Assert.fail( "Should have thrown an exception" );
		} 
		catch (Exception e) {
			Assert.assertTrue(true);
		}
		
	}
	
	
	private void initPlayer(int pieceOfGold, int fightingStrength, int countOfBowsInKnapsack, int countOfSwordsInKnapsack, String playerName, int priceForSword, int priceForBow) {
		Collection<Bow> bows = new ArrayList<Bow>();
		for(int i = 0; i< countOfBowsInKnapsack;i++) {
			Bow newBow = new Bow(priceForBow);
			bows.add(newBow);
		}
		Collection<Sword> swords = new ArrayList<Sword>();
		for(int i = 0; i< countOfSwordsInKnapsack;i++) {
			Sword newSword = new Sword(priceForSword);
			swords.add(newSword);
		}
		Collection<Weapon> knapsack = new ArrayList<Weapon>();
		knapsack.addAll(bows);
		knapsack.addAll(swords);
		game.initPlayer(pieceOfGold, fightingStrength, playerName, knapsack);
	}
	
	private ArrayList<Weapon> generateBowsToBuy(int priceForBow, int numberOfBowsToBuy) {
		Random random = new Random( );
		ArrayList<Weapon> bowsToOrder = new ArrayList<Weapon>();
		
		for ( int ii = 0; ii < numberOfBowsToBuy; ii++ )
		{
			Bow bow = new Bow( priceForBow );
			bowsToOrder.add( bow );
		}
		return bowsToOrder;
	}
	
}
