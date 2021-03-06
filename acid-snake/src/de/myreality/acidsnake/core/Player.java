/* Acid - Provides a Java cell API to display fancy cell boxes.
 * Copyright (C) 2013  Miguel Gonzalez
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 */

package de.myreality.acidsnake.core;

import de.myreality.acidsnake.util.Pauseable;

/**
 * Player of a given game
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface Player extends Scoreable, Pauseable {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Resets the points
	 */
	void resetPoints();
	
	/**
	 * Add points to the player
	 * 
	 * @param points target points to add
	 */
	void addPoints(int points);
	
	/**
	 * Set a new point amount
	 * 
	 * @param points points to set
	 */
	void setPoints(int points);
	
	/**
	 * Resets the time
	 */
	void resetTime();
	
	void addListener(PlayerListener listener);
	
}
