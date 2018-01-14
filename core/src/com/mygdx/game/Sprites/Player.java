package com.mygdx.game.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by Bruno on 31/12/2017.
 */

public class Player extends Sprite {

    public enum State{SALTO, CORRER,PARADO}
    public State currentState;
    public State previousState;
    public World world;
    public Body body;
    private TextureRegion playerDefault;
    private Animation running;
    private Animation jump;
    private boolean correrdireita;
    public float stateTimer;
    public Player(PlayScreen screen)
    {
        super(screen.getAtlas().findRegion("RunFausto"));
        this.world = screen.getWorld();
        currentState = State.PARADO;
        previousState = State.PARADO;
        stateTimer = 0;
        correrdireita = true;
        Array<TextureRegion> frames = new Array<TextureRegion>();


        for(int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("RunFausto"), i * 256, 0, 256, 256));
            running = new Animation(0.2f, frames);
        }

        frames.clear();

        DefinePlayer();
        playerDefault = new TextureRegion(screen.getAtlas().findRegion("RunFausto"),0,0,256,256);
        setBounds(0,0,256/MyGame.PPM,256/MyGame.PPM);

        setRegion(playerDefault);
    }

    public void update(float dt)
    {
        setPosition(body.getPosition().x - getWidth()/2,body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();

        TextureRegion region;

        switch (currentState)
        {
            case CORRER:
                region = (TextureRegion) running.getKeyFrame(stateTimer,true);
                break;
                default:
                    region = playerDefault;
                    break;
        }
        if((body.getLinearVelocity().x < 0 || !correrdireita) && !region.isFlipX())
        {
            region.flip(true, false);
            correrdireita = false;
        }

        //if mario is running right and the texture isnt facing right... flip it.
        else if((body.getLinearVelocity().x > 0 || correrdireita) && region.isFlipX()){
            region.flip(true, false);
            correrdireita = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;

        return  region;
    }

    public State getState(){
        if(body.getLinearVelocity().x != 0)
            return State.CORRER;
        else
            return State.PARADO;
    }
    public void DefinePlayer()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(128/ MyGame.PPM,128/ MyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(60/ MyGame.PPM);

        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
