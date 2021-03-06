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

package de.myreality.acidsnake.screens;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import de.myreality.acid.Acid;
import de.myreality.acid.gdx.GdxBufferedRenderer;
import de.myreality.acidsnake.Resources;
import de.myreality.acidsnake.SnakeGame;
import de.myreality.acidsnake.controls.IngameProcessor;
import de.myreality.acidsnake.core.PointManager;
import de.myreality.acidsnake.core.Snake;
import de.myreality.acidsnake.google.ArchievementManager;
import de.myreality.acidsnake.graphics.ParticleRenderer;
import de.myreality.acidsnake.graphics.WorldRenderer;
import de.myreality.acidsnake.ui.LevelLabel;
import de.myreality.acidsnake.ui.PauseButton;
import de.myreality.acidsnake.ui.PlayerActorAnimator;
import de.myreality.acidsnake.ui.PopupManager;
import de.myreality.acidsnake.ui.ProgressImage;
import de.myreality.acidsnake.ui.ScoreLabel;
import de.myreality.acidsnake.world.SimpleWorld;
import de.myreality.acidsnake.world.World;

/**
 * Ingame screen which handles the basic game
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 * @version 1.0
 */
public class IngameScreen implements Screen {

	// ===========================================================
	// Constants
	// ===========================================================
	
	private static final int GLOBAL_PADDING = 30;

	// ===========================================================
	// Fields
	// ===========================================================
	
	private SnakeGame game;
	
	private Acid acid;
	
	private World world;
	
	private WorldRenderer worldRenderer;
	
	private ParticleRenderer particleRenderer;
	
	private GdxBufferedRenderer bufferedRenderer;
	
	private Button btnPause;
	
	private ProgressImage progressImage;
	
	private Stage stage;
	
	private LevelLabel lblLevel;
	
	private SpriteBatch batch;
	
	@SuppressWarnings("unused")
	private FPSLogger fpsLogger;
	
	private TweenManager tweenManager;
	
	private ScoreLabel lblScore;
	
	private PopupManager popupManager;

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public IngameScreen(SnakeGame game) {
		this.game = game;
	}

	// ===========================================================
	// Getters and Setters
	// ===========================================================

	// ===========================================================
	// Methods from Superclass
	// ===========================================================
	

	boolean pressed;

	private ArchievementManager achievementManager;

	@Override
	public void render(float delta) {
		float color = 0.0f;
		
		Gdx.gl.glClearColor(color, color, color, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (!world.isPaused()) {
			tweenManager.update(delta);
			world.update(delta);
			stage.act(delta);
		}
		
		//fpsLogger.log();
		
		acid.render();	
		
		batch.begin();
			particleRenderer.render(batch, delta);
		batch.end();
		
		
		stage.draw();
		
		if (world.getSnake().isKilled()) {
			game.setScreen(new GameOverScreen(game, world.getPlayer()));
		}
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null) {
			stage = new IngameProcessor(width, height, game, world);
			Gdx.input.setInputProcessor(stage);
			
			LabelStyle popupStyle = new LabelStyle();
			popupStyle.font = Resources.BITMAP_FONT_REGULAR;
			popupStyle.fontColor = Resources.COLOR_GREEN;
			
			LabelStyle style = new LabelStyle();
			style.font = Resources.BITMAP_FONT_LARGE;
			style.fontColor = Resources.COLOR_GREEN;
			
			LabelStyle styleLevel = new LabelStyle();
			styleLevel.font = Resources.BITMAP_FONT_LARGE;
			styleLevel.fontColor = Resources.COLOR_VIOLET;
			
			ButtonStyle pauseStyle = new ButtonStyle();
			pauseStyle.up = new SpriteDrawable(new Sprite(Resources.TEXTURE_ICON_PAUSE));
			pauseStyle.checked = new SpriteDrawable(new Sprite(Resources.TEXTURE_ICON_PLAY));
			
			btnPause = new PauseButton(world, pauseStyle);
			
			stage.addActor(btnPause);
			
			lblScore = new ScoreLabel(world.getPlayer(), tweenManager, style);	
			lblLevel = new LevelLabel(world.getPlayer(), tweenManager, styleLevel);
			stage.addActor(lblScore);
			stage.addActor(lblLevel);
			applyUI();
			
			popupManager = new PopupManager(stage, tweenManager, popupStyle);
			PointManager pointManager = new PointManager(acid, popupManager);
			
			achievementManager.addListener(pointManager);
			
			Snake snake = world.getSnake();
			snake.addListener(pointManager);
			
			progressImage = new ProgressImage(world.getPlayer());
			stage.addActor(progressImage);
			
			PlayerActorAnimator animator = new PlayerActorAnimator(progressImage, tweenManager);
			world.getPlayer().addListener(animator);
		} else {
			stage.setViewport(width, height, false);
			applyUI();
		}
	}
	
	@Override
	public void show() {
		ShaderProgram.pedantic = false;
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		fpsLogger = new FPSLogger();
		final int VERTICAL_INDEX = 18;
		final int CELL_SIZE = (int) ((Gdx.graphics.getHeight()) / VERTICAL_INDEX);
		final int HORIZONTAL_INDEX = (int) (Gdx.graphics.getWidth() / CELL_SIZE);
		world = new SimpleWorld(HORIZONTAL_INDEX, VERTICAL_INDEX);
		bufferedRenderer = new GdxBufferedRenderer();

        acid = new Acid(HORIZONTAL_INDEX, VERTICAL_INDEX, CELL_SIZE, bufferedRenderer);
        acid.setPosition(Gdx.graphics.getWidth() / 2f - acid.getWidth() / 2f, 
							   (Gdx.graphics.getHeight()) / 2f - acid.getHeight() / 2f);
        acid.setPadding(1);
        Resources.reloadCellRenderer((GdxBufferedRenderer) acid.getBufferedRenderer());
        
        
        Snake snake = world.getSnake();
        
        worldRenderer = new WorldRenderer(acid);
        world.addListener(worldRenderer);
        achievementManager = new ArchievementManager(world, game.getGoogleInterface());
		snake.addListener(achievementManager);
		world.getPlayer().addListener(achievementManager);
        world.build();
        //world.getSnake().addListener(new WorldDebugger(world));
        particleRenderer = new ParticleRenderer(acid, world);
        world.getPlayer().addListener(particleRenderer);
        world.getSnake().addListener(particleRenderer);
        world.addListener(particleRenderer);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	
	// ===========================================================
	// Methods
	// ===========================================================
	
	private void applyUI() {
		btnPause.setX(GLOBAL_PADDING);
		btnPause.setY(GLOBAL_PADDING);		
		lblScore.setX(GLOBAL_PADDING);
		lblScore.setY(Gdx.graphics.getHeight() - lblScore.getHeight() - 10);
		lblLevel.setX(Gdx.graphics.getWidth() - lblLevel.getWidth() - GLOBAL_PADDING);
		lblLevel.setY(Gdx.graphics.getHeight() - lblLevel.getHeight() - 10);
		
	}

	// ===========================================================
	// Inner classes
	// ===========================================================
}
