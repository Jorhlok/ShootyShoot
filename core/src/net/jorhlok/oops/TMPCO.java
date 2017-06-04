//package net.jorhlok.oops;
//
//import com.badlogic.gdx.maps.objects.RectangleMapObject;
//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
//import com.badlogic.gdx.math.Rectangle;
//
///**
// * TiledMap-Physical Collision Object
// * @author joshm
// */
//public class TMPCO {
//    public Rectangle AABB = null;
//    public RectangleMapObject NonTile = null;
//    public Cell cell = null;
//    public byte CollisionFlags = 0; //0000UDLR
//
//    public boolean CollisionUp() {
//        return (CollisionFlags&8) != 0;
//    }
//
//    public boolean CollisionDown() {
//        return (CollisionFlags&4) != 0;
//    }
//
//    public boolean CollisionLeft() {
//        return (CollisionFlags&2) != 0;
//    }
//
//    public boolean CollisionRight() {
//        return (CollisionFlags&1) != 0;
//    }
//}
