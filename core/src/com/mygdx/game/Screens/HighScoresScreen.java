package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.Outros.Assets;

import org.w3c.dom.css.Rect;

/**
 * Created by Bruno on 07/01/2018.
 */

public class HighScoresScreen extends ScreenAdapter {

    MyGame game;
    OrthographicCamera camera;
    Vector3 touchPoint;
    Rectangle backBounds;
    String[] highScores;
    String Score;
    float xOffset = 0;
    GlyphLayout glyphLayout = new GlyphLayout();

    public HighScoresScreen(MyGame game) {
        this.game = game;

        camera = new OrthographicCamera(MyGame.V_WIDTH, MyGame.V_HEIGHT);
        camera.position.set(MyGame.V_WIDTH / 2, MyGame.V_HEIGHT / 2, 0);
        backBounds = new Rectangle(0, 0, 64, 64);
        touchPoint = new Vector3();
        Score = new String("HighScore");
        highScores = new String[5];
        for (int i = 0; i < 5; i++) {
            highScores[i] = i + 1 + ". " + Options.highscores[i];
            glyphLayout.setText(Assets.font, highScores[i]);
             xOffset = Math.max(glyphLayout.width, xOffset);
        }
    }
    public void update () {
        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (backBounds.contains(touchPoint.x, touchPoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
    }

    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(Assets.background, 0, 0, MyGame.V_WIDTH, MyGame.V_HEIGHT);
        Assets.font.draw(game.batch,Score,MyGame.V_WIDTH/2,MyGame.V_HEIGHT/2);
        float y = 230;
        for (int i = 4; i >= 0; i--) {
            Assets.font.draw(game.batch, highScores[i], xOffset, y);
            y += Assets.font.getLineHeight();
        }

        game.batch.draw(Assets.arrow, 0, 0, 64, 64);
        game.batch.end();


    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }
}
