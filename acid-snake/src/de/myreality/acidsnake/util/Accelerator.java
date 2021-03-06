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

package de.myreality.acidsnake.util;


/**
 * Moves movable objects by a given speed rate
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class Accelerator implements Updateable {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final long MAX_INTERVAL = 2000;

	// ===========================================================
	// Fields
	// ===========================================================

	private Moveable moveable;

	private float speedRate;

	private Timer timer;

	// ===========================================================
	// Constructors
	// ===========================================================

	public Accelerator(Moveable moveable) {
		this.moveable = moveable;
		speedRate = 30;
		timer = new Timer();
		timer.start();
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================

	@Override
	public void update(float delta) {
		if (timer.getTicks() > getRequiredTime()) {
			timer.reset();
			moveable.move();
		}
	}
	
	public void setPaused(boolean paused) {
		if (paused) {
			timer.pause();
		} else {
			timer.start();
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	public Moveable getMoveable() {
		return moveable;
	}

	public float getSpeedRate() {
		return speedRate;
	}

	public void setSpeedRate(float rate) {
		this.speedRate = rate;
	}
	
	private long getRequiredTime() {
		return (long) (MAX_INTERVAL / speedRate);
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
