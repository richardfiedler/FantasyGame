package de.fiedler.artifacts;

import de.fiedler.Player;
import de.fiedler.repositories.Shop;

public class Bow extends Weapon
{

	public Bow( int price)
	{
		super.setPrice( price ); 
	}

	public void action( Player player )
	{
		int fightingStrength = player.getFightingStrength();
		System.out.println( "Bogen schießt " + fightingStrength + " Pfeile ab." );
		player.removeFromKnapsack(this);
	}

	public String printName( )
	{
		return "bow";
	}
}
