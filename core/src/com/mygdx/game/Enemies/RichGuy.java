package com.mygdx.game.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGame;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by Bruno on 13/01/2018.
 */

public class RichGuy extends Enemy {

    private float stateTime;
    public Body body;
    private TextureRegion playerDefault;

    private Animation running;
    private Animation jump;
    private boolean correrdireita;
    public float stateTimer;
    private Array<TextureRegion> frames;
    public RichGuy(PlayScreen screen, float x, float y)
    {
        super(screen, x, y);


        frames = new Array<TextureRegion>();
        for(int i = 1; i < 5; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("badguy"), i * 256, 0, 256, 256));
            running = new Animation(0.2f, frames);
        }
        setBounds(getX(), getY(), 128 / MyGame.PPM, 128 / MyGame.PPM);
        stateTime = 0;
    }
    public void update(float dt) {
        stateTime += dt;
        //body.setLinearVelocity(velocity);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion((TextureRegion) running.getKeyFrame(stateTime, true));
    }
    @Override
    public void defineEnemy()
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
