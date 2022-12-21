package de.fiedler.interfaces;

import java.util.Collection;

import de.fiedler.artifacts.Weapon;

public interface BuyInterface
{
	public Collection<Weapon> buy( int count, int type );
}
