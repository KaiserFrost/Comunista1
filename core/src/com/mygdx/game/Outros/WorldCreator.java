package com.mygdx.game.Outros;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Enemies.Enemy;
import com.mygdx.game.Enemies.RichGuy;
import com.mygdx.game.MyGame;
import com.mygdx.game.Screens.PlayScreen;

/**
 * Created by Bruno on 03/01/2018.
 */

public class WorldCreator {

    private Array<RichGuy> badGuy;

    public WorldCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        for (int x = 0; x < map.getLayers().getCount(); x++) {
            for (MapObject object : map.getLayers().get(x).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                bdef.type = BodyDef.BodyType.StaticBody;
                bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGame.PPM);
                body = world.createBody(bdef);
                polygonShape.setAsBox(rect.getWidth() / 2 / MyGame.PPM, rect.getHeight() / 2 / MyGame.PPM);
                fdef.shape = polygonShape;
                body.createFixture(fdef);
            }
        }
      badGuy = new Array<RichGuy>();
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            badGuy.add(new RichGuy(screen, rect.getX() / MyGame.PPM, rect.getY() / MyGame.PPM));
        }

    }

    public Array<Enemy> getEnemies()
    {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(badGuy);

        return enemies;
    }
}
