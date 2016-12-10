package net.jorhlok.oops;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;

/**
 * TiledMap-Physical Collision Object
 * @author joshm
 */
public class TMPCO {
    Rectangle AABB = null;
    RectangleMapObject NonTile = null;
    TiledMapTile Tile = null;
    boolean vHit = false;
    boolean hHit = false;
}
