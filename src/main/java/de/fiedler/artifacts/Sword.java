package de.fiedler.artifacts;

import java.util.Collection;

import de.fiedler.Player;
import de.fiedler.repositories.Shop;

public class Sword extends Weapon
{

	public Sword( int price)
	{
		super.setPrice( price );
	}

	public void action( Player player)
	{
		int fightingStrength = player.getFightingStrength();
		System.out.println( "Schwert schlägt " + fightingStrength + "-mal zu" );
		player.removeFromKnapsack(this);
	}

	public String printName( )
	{
		return "Sword";
	}

}
