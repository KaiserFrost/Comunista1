package com.mygdx.game.Outros;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGame;
import com.mygdx.game.Screens.Options;

import java.awt.Font;

/**
 * Created by Bruno on 07/01/2018.
 */

public class Assets {
    public static Texture background;
    public static Texture playButton;
    public static Texture scoreButton;
    public static Texture scoreButtonclick;
    public static Texture optionsButton;
    public static Texture pauseButton;
    public static Texture arrow, arrowred;
    public static Texture restartButton;
    public static Texture noButton;
    public static Texture soundButton,nosoundButton;
    public static Sound clickSound;
    public static Music music;
    public static BitmapFont font;


    public static void load () {
        background = new Texture("imagens/commie.jpg");
        playButton = new Texture("imagens/FoiceMartelo.png");
        scoreButton = new Texture("imagens/score.png");
        optionsButton = new Texture("imagens/defenicoes.png");
        scoreButtonclick =  new Texture("imagens/score2.png");
        pauseButton = new Texture("imagens/pause.png");
        arrowred = new Texture("imagens/arrowred.png");
        arrow = new Texture("imagens/arrow.png");
        restartButton = new Texture("imagens/restart.png");
        soundButton= new Texture("imagens/sound.png");
        nosoundButton = new Texture("imagens/nosound.png");
        noButton = new Texture("imagens/no.png");
        clickSound = Gdx.audio.newSound(Gdx.files.internal("Sound/Click.wav"));

        font =new BitmapFont(Gdx.files.internal("stuff.fnt"),Gdx.files.internal("stuff.png"),false);

        music = Gdx.audio.newMusic(Gdx.files.internal("Sound/Soviet Union National Anthem.mp3"));

    }

    public static void playSound (Sound sound) {
        if (Options.soundEnabled) sound.play(1);
    }
}
