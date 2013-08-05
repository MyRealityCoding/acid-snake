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

package de.myreality.acidsnake.world;

/**
 * Listens events to a given world
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface WorldListener {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================
	
	/**
	 * Is called before putting an entity target to a given position
	 * 
	 * @param indexX
	 * @param indexY
	 * @param target
	 */
	void onPut(int indexX, int indexY, WorldEntity target);
	
	/**
	 * Is called when a specific entity will be removed from the world
	 * 
	 * @param indexX
	 * @param indexY
	 * @param target
	 */
	void onRemove(int indexX, int indexY, WorldEntity target);
	
	/**
	 * Is called when the world builds
	 * 
	 * @param world target world
	 */
	void onBuild(World world);
}
