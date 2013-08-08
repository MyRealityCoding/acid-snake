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

package de.myreality.acidsnake.graphics;

import com.badlogic.gdx.graphics.Color;

import de.myreality.acid.CellManager;
import de.myreality.acid.CellRenderer;
import de.myreality.acid.gdx.GdxBufferedRenderer;
import de.myreality.acid.gdx.GdxCellRenderer;
import de.myreality.acidsnake.Resources;
import de.myreality.acidsnake.world.World;
import de.myreality.acidsnake.world.WorldEntity;
import de.myreality.acidsnake.world.WorldEntityType;
import de.myreality.acidsnake.world.WorldListener;

/**
 * Renderer which renders the world
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class WorldRenderer implements WorldListener {
	
	private CellManager manager;
	
	private CellRenderer orangeCellRenderer, violetCellRenderer, greenCellRenderer, normalCellRenderer;
	
	public WorldRenderer(CellManager manager) {
		this.manager = manager;
		orangeCellRenderer = new GdxCellRenderer(Resources.TEXTURE_BLOCK_ORANGE, (GdxBufferedRenderer)manager.getBufferedRenderer());
		greenCellRenderer = new GdxCellRenderer(Resources.TEXTURE_BLOCK_GREEN, (GdxBufferedRenderer)manager.getBufferedRenderer());
		violetCellRenderer = new GdxCellRenderer(Resources.TEXTURE_BLOCK_VIOLET, (GdxBufferedRenderer)manager.getBufferedRenderer());
		normalCellRenderer = new GdxCellRenderer(Resources.TEXTURE_BLOCK, (GdxBufferedRenderer)manager.getBufferedRenderer());
	}

	@Override
	public void onPut(int indexX, int indexY, WorldEntity target, World world) {
		
		Color color = Resources.COLOR_GREEN;
		
		switch (target.getType()) {
			case SMALL_FOOD:	
				manager.setCellRenderer(orangeCellRenderer);
				break;
			case RARE_FOOD:
				manager.setCellRenderer(violetCellRenderer);
				break;
			case SNAKE:
				manager.setCellRenderer(greenCellRenderer);
				break;
			case TELEPORTER:
				manager.setCellRenderer(normalCellRenderer);
				break;
			default:
				manager.setCellRenderer(greenCellRenderer);
				break;
		}
		
		manager.color(color.r, color.g, color.b);
		manager.put(indexX, indexY);
	}

	@Override
	public void onRemove(int indexX, int indexY, WorldEntity target, World world) {
		if (!(target.getType().equals(WorldEntityType.SNAKE) && !world.getSnake().getTail().equals(target))) {
			manager.clear(indexX, indexY);
		}
	}

	@Override
	public void onBuild(World world) {
		
	}

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner classes
	// ===========================================================
}
