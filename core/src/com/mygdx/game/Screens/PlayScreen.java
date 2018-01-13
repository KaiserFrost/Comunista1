package com.mygdx.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.HUD.Hud;
import com.mygdx.game.MyGame;
import com.mygdx.game.Outros.Assets;
import com.mygdx.game.Outros.WorldCreator;
import com.mygdx.game.Sprites.Player;

/**
 * Created by Bruno on 31/12/2017.
 */

public class PlayScreen implements Screen {

public MyGame game;

    public enum State{PAUSA, INGAME,GAMEOVER}
    public Player.State currentState;
    public State state;
    private Stage pauseStage;
    public Player.State previousState;
    private OrthographicCamera camera, camera2;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Vector3 touchPoint;
    private World world;
    private Player player;
    private TextureAtlas atlas;
    private Box2DDebugRenderer b2dr;
    private Rectangle pauseBounds;
    private Rectangle restartBounds;
    private Rectangle noBounds;
    private Rectangle soundBounds;
    private boolean sound;
    public PlayScreen(MyGame game){

        atlas = new TextureAtlas("Sprites.pack");
        this.game = game;
        sound = true;
        touchPoint = new Vector3();
        camera = new OrthographicCamera();
        camera2 = new OrthographicCamera(MyGame.V_WIDTH / MyGame.PPM,MyGame.V_HEIGHT / MyGame.PPM);
        gamePort = new FitViewport(MyGame.V_WIDTH / MyGame.PPM,MyGame.V_HEIGHT / MyGame.PPM,camera);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mapy.tmx");
        state = State.INGAME;
        renderer = new OrthogonalTiledMapRenderer(map,1/ MyGame.PPM);
        pauseBounds = new Rectangle((MyGame.V_WIDTH - 50)/ MyGame.PPM, (MyGame.V_HEIGHT -50)/ MyGame.PPM, 50/ MyGame.PPM, 50/ MyGame.PPM);
        soundBounds = new Rectangle((MyGame.V_WIDTH - 50)/ MyGame.PPM, 0, 50/ MyGame.PPM, 50/ MyGame.PPM);
        restartBounds = new Rectangle(0, 0, 100/ MyGame.PPM, 100/ MyGame.PPM);
        noBounds = new Rectangle(0, (MyGame.V_HEIGHT -50)/ MyGame.PPM, 50/ MyGame.PPM, 50/ MyGame.PPM);
        camera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight() /2,0);
        camera2.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight() /2,0);
        pauseStage = new Stage(new FitViewport(MyGame.V_WIDTH,MyGame.V_HEIGHT));
        world = new World(new Vector2(0,-9),true);

        player = new Player(world, this);

        b2dr = new Box2DDebugRenderer();

        new WorldCreator(world,map);

    }

    public void HandleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.body.applyLinearImpulse(new Vector2(0,6f),player.body.getWorldCenter(),true);

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.body.getLinearVelocity().x<=2)
            player.body.applyLinearImpulse(new Vector2(1f,0f),player.body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.body.getLinearVelocity().x>=-2)
            player.body.applyLinearImpulse(new Vector2(-1f,0f),player.body.getWorldCenter(),true);

    }
    public void update(float dt) {




            switch(state)
            {
                case INGAME:
                    HandleInput(dt);
                    player.update(dt);
                    world.step(1 / 60f, 6, 2);

                    camera.position.x = player.body.getPosition().x;
                    camera.position.y = player.body.getPosition().y;
                    if (Gdx.input.justTouched()) {
                        camera2.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                        if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                            Assets.playSound(Assets.clickSound);
                            state = State.PAUSA;
                            return;
                        }
                    }

                    break;
                case PAUSA:
                    if (Gdx.input.justTouched()) {
                        camera2.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

                        if (pauseBounds.contains(touchPoint.x, touchPoint.y)) {
                            Assets.playSound(Assets.clickSound);
                            state = State.INGAME;
                            return;
                        }
                        if (restartBounds.contains(touchPoint.x, touchPoint.y)) {
                            Assets.playSound(Assets.clickSound);
                            game.setScreen(new PlayScreen(game));
                            return;
                        }
                        if (noBounds.contains(touchPoint.x, touchPoint.y)) {
                            Assets.playSound(Assets.clickSound);
                            game.setScreen(new MainMenuScreen(game));
                            return;
                        }

                            if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
                                Assets.playSound(Assets.clickSound);
                                if (sound == true) {
                                    Options.soundEnabled = false;
                                    sound = false;
                                }
                               else
                                   {
                                    Options.soundEnabled = true;
                                    sound = true;
                                }
                                return;
                            }

                    }
                    break;
                case GAMEOVER:
                    break;
            }

        camera.update();
        camera2.update();
        renderer.setView(camera);


    }
    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world,camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.setSize(128/MyGame.PPM,128/MyGame.PPM);
        player.draw(game.batch);

        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        game.batch.setProjectionMatrix(camera2.combined);
        game.batch.begin();
        switch (state)
        {
            case INGAME:
                game.batch.draw(Assets.pauseButton,(MyGame.V_WIDTH - 50)/ MyGame.PPM, (MyGame.V_HEIGHT -50)/ MyGame.PPM, 50/ MyGame.PPM, 50/ MyGame.PPM);

                break;
            case PAUSA:
                game.batch.draw(Assets.restartButton,0, 0, 50/ MyGame.PPM, 50/ MyGame.PPM);
                game.batch.draw(Assets.noButton,0, (MyGame.V_HEIGHT -50)/ MyGame.PPM, 50/ MyGame.PPM, 50/ MyGame.PPM);
                game.batch.draw(Assets.arrowred,(MyGame.V_WIDTH - 50)/ MyGame.PPM, (MyGame.V_HEIGHT -50)/ MyGame.PPM, 50/ MyGame.PPM, 50/ MyGame.PPM);
                if(sound == true)
                game.batch.draw(Assets.soundButton,(MyGame.V_WIDTH - 50)/ MyGame.PPM, 0, 50/ MyGame.PPM, 50/ MyGame.PPM);
                if(sound == false)
                game.batch.draw(Assets.nosoundButton,(MyGame.V_WIDTH - 50)/ MyGame.PPM, 0, 50/ MyGame.PPM, 50/ MyGame.PPM);

                break;
        }
       game.batch.end();



    }

    @Override
    public void resize(int width, int height)
    {
gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
