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

package de.myreality.acidsnake.google;

/**
 * Default google interface for Google Play API
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public interface GoogleInterface {

	public void login();

	public void logout();

	// get if client is signed in to Google+
	public boolean getSignedIn();

	// submit a score to a leaderboard
	public void submitScore(int score);
	
	// submit a new archivement
	public void submitAchievement(String id);
	
	public void incrementAchievement(String id, int steps);

	// gets the scores and displays them threw googles default widget
	public void showScores();

	// gets the score and gives access to the raw score data
	public void getScoresData();
	
	boolean isConnected();
	
	void showAchievements();
}