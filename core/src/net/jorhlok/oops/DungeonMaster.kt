package net.jorhlok.oops

import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import java.util.LinkedList
import java.util.Queue

/**
 * The master script in a room.
 * @author Jorhlok
 */
open class DungeonMaster{
    interface OOPS {
        val GlobalData: MutableMap<String, LabelledObject>
        fun launchScript(key: String)
    }

    //setup
    var MapName: String = ""
    var StrTerrain = "Terrain"
    var StrTileObjects = "TileObjects"
    var StrNonTiles = "NonTiles"

    //runtime
    var Parent: OOPS? = null
    var Level: TiledMap? = null
    var LyrTerrain: TiledMapTileLayer? = null
    var LyrTileObjects: TiledMapTileLayer? = null
    var LyrNonTiles: MapLayer? = null
    var Living = LinkedList<Entity>()

    constructor(mapname: String, terr: String? = null, tile: String? = null, nontile: String? = null) {
        MapName = mapname
        if (terr != null) StrTerrain = terr
        if (tile != null) StrTileObjects = tile
        if (nontile != null) StrNonTiles = nontile
    }

    fun create(maps: Map<String, TiledMap>, parent: OOPS) {
        Parent = parent
        Level = maps[MapName]
        LyrTerrain = Level?.layers?.get(StrTerrain) as TiledMapTileLayer?
        LyrTileObjects = Level?.layers?.get(StrTileObjects) as TiledMapTileLayer?
        LyrNonTiles = Level?.layers?.get(StrNonTiles)
    }

    open fun begin() {}

    open fun end() {}

    open fun dispose() {}

    open fun prestep(deltaTime: Float) {}
    open fun poststep(deltaTime: Float) {}

    fun update(deltatime: Float) {
        prestep(deltatime)
        for (e in Living) e.prestep(deltatime)
        //check for collisions with entities
        //check for collisions with tiles if the wish and engage mid-step if they need it
        for (e in Living) e.poststep(deltatime)
        poststep(deltatime)
    }

    open fun draw(deltatime: Float, obj: LabelledObject) {

    }
//
//    fun CorporealCollisions(cw: List<String>, q: Queue<Corporeal>, aabb: Rectangle) {
//
//    }
//
//    fun PhysicalCollisions(q: Queue<TMPCO>, aoi: Rectangle, projected: Rectangle) {
//        val x1 = Math.floor(aoi.x.toDouble()).toInt()
//        val y1 = Math.floor(aoi.y.toDouble()).toInt()
//        val x2 = Math.ceil((aoi.x + aoi.width).toDouble()).toInt()
//        val y2 = Math.ceil((aoi.y + aoi.height).toDouble()).toInt()
//        //collide with tiles
//        for (y in y1..y2) {
//            for (x in x1..x2) {
//                var c: Cell?
//                try {
//                    c = LyrTerrain?.getCell(x, y)
//                    if (c != null && c.tile != null) {
//                        val tmp = TMPCO()
//                        tmp.AABB = Rectangle(x.toFloat(), y.toFloat(), 1f, 1f)
//                        tmp.cell = c
//                        val tcenter = tmp.AABB.getCenter(Vector2())
//                        val pcenter = projected.getCenter(Vector2())
//                        if (pcenter.y > tcenter.y)
//                            tmp.CollisionFlags = (tmp.CollisionFlags+8).toByte()
//                        else if (pcenter.y < tcenter.y) tmp.CollisionFlags = (tmp.CollisionFlags+4).toByte()
//                        if (pcenter.x > tcenter.x)
//                            tmp.CollisionFlags = (tmp.CollisionFlags+2).toByte()
//                        else if (pcenter.x < tcenter.x) tmp.CollisionFlags = (tmp.CollisionFlags+1).toByte()
//                        q.add(tmp)
//                    }
//                } catch (e: Exception) {
//                    //nothing
//                }
//
//                try {
//                    c = LyrTileObjects?.getCell(x, y)
//                    if (c != null && c.tile != null) {
//                        val tmp = TMPCO()
//                        tmp.AABB = Rectangle(x.toFloat(), y.toFloat(), 1f, 1f)
//                        tmp.cell = c
//                        val tcenter = tmp.AABB.getCenter(Vector2())
//                        val pcenter = projected.getCenter(Vector2())
//                        if (pcenter.y > tcenter.y)
//                            tmp.CollisionFlags = (tmp.CollisionFlags+8).toByte()
//                        else if (pcenter.y < tcenter.y) tmp.CollisionFlags = (tmp.CollisionFlags+4).toByte()
//                        if (pcenter.x > tcenter.x)
//                            tmp.CollisionFlags = (tmp.CollisionFlags+2).toByte()
//                        else if (pcenter.x < tcenter.x) tmp.CollisionFlags = (tmp.CollisionFlags+1).toByte()
//                        q.add(tmp)
//                    }
//                } catch (e: Exception) {
//                    //nothing
//                }
//
//            }
//        }
//        //collide with non-tiles
//        try {
//            val mapobj = LyrNonTiles?.objects?.getByType(RectangleMapObject::class.java)
//            if (mapobj != null) for (r in mapobj) {
//                if (aoi.overlaps(r.rectangle)) {
//                    val tmp = TMPCO()
//                    tmp.AABB = r.rectangle
//                    tmp.NonTile = r
//                    val tcenter = tmp.AABB.getCenter(Vector2())
//                    val pcenter = projected.getCenter(Vector2())
//                    if (pcenter.y > tcenter.y)
//                        tmp.CollisionFlags = (tmp.CollisionFlags+8).toByte()
//                    else if (pcenter.y < tcenter.y) tmp.CollisionFlags = (tmp.CollisionFlags+4).toByte()
//                    if (pcenter.x > tcenter.x)
//                        tmp.CollisionFlags = (tmp.CollisionFlags+2).toByte()
//                    else if (pcenter.x < tcenter.x) tmp.CollisionFlags = (tmp.CollisionFlags+1).toByte()
//                    q.add(tmp)
//                }
//            }
//        } catch (e: Exception) {
//            //nothing
//        }
//
//    }

}
