package com.mygdx.game.Outros;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGame;

/**
 * Created by Bruno on 03/01/2018.
 */

public class WorldCreator {
    public WorldCreator(World world, TiledMap map)
    {
        BodyDef bdef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        for(int x = 0; x < map.getLayers().getCount(); x++)
        {
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
    }
}
