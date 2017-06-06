package net.jorhlok.oops

import com.badlogic.gdx.maps.MapLayer
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Rectangle
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
    var MapName = ""
    var StrTerrain = "Terrain"
//    var StrTileObjects = "TileObjects"
//    var StrNonTiles = "NonTiles"

    //runtime
    var Parent: OOPS? = null
    var Level: TiledMap? = null
    var LyrTerrain: TiledMapTileLayer? = null
//    var LyrTileObjects: TiledMapTileLayer? = null
//    var LyrNonTiles: MapLayer? = null
    var Living: LinkedList<Entity> = LinkedList()

    constructor(mapname: String, terr: String? = null) {
        MapName = mapname
        if (terr != null) StrTerrain = terr
//        if (tile != null) StrTileObjects = tile
//        if (nontile != null) StrNonTiles = nontile
    }

    fun create(maps: Map<String, TiledMap>, parent: OOPS) {
        Parent = parent
        Level = maps[MapName]
        LyrTerrain = Level?.layers?.get(StrTerrain) as TiledMapTileLayer?
//        LyrTileObjects = Level?.layers?.get(StrTileObjects) as TiledMapTileLayer?
//        LyrNonTiles = Level?.layers?.get(StrNonTiles)
    }

    open fun begin() {}
    open fun end() {}
    open fun dispose() {}

    open fun prestep(deltaTime: Float) {}
    open fun poststep(deltaTime: Float) {}

    fun update(deltatime: Float) {
        prestep(deltatime)
        for (e in Living) e.prestep(deltatime)
        for (e in Living) e.doSimplePhysics(deltatime)

        //check for collisions with entities
        //check for collisions with tiles if they wish and engage mid-step if they need it
        val q: Queue<LabelledObject> = LinkedList()
        val iter = Living.listIterator()
        while (iter.hasNext()) { //everybody handshake  sum(1..n)  n(n-1)/2  O(n^2)
            val obj = iter.next()
            if (obj.CollEntities) {
                iter.forEachRemaining { e ->
                    if (e.CollEntities && e.AOI.overlaps(obj.AOI)) {
                        if (obj.CollEntWhite == null && obj.CollEntGray == null ||
                                obj.CollEntWhite != null && e.Type in obj.CollEntWhite!! ||
                                    obj.CollEntGray != null && e.Type in obj.CollEntGray!!
                                        && obj.checkCollEntity(deltatime,e)) {
                            obj.Mailbox.add(LabelledObject("CollEnt/${e.Type}", e))
                            if (obj.CollEntAsTile != null && e.Type in obj.CollEntAsTile!!)
                                obj.CollQueue.add(LabelledObject("Ent/${e.Type}",e))
                        }

                        if (e.CollEntWhite == null && e.CollEntGray == null ||
                                e.CollEntWhite != null && obj.Type in e.CollEntWhite!! ||
                                    e.CollEntGray != null && obj.Type in e.CollEntGray!!
                                        && e.checkCollEntity(deltatime,obj)) {
                            e.Mailbox.add(LabelledObject("CollEnt/${obj.Type}", obj))
                            if (e.CollEntAsTile != null && obj.Type in e.CollEntAsTile!!)
                                e.CollQueue.add(LabelledObject("Ent/${obj.Type}",e))
                        }
                    }
                }
            }
            if (obj.CollTiles) {
                CollectTiles(q, obj.AOI)
                for (o in q) {
                    val tile = o.obj as Array<Any>
                    val c = tile[0] as TiledMapTileLayer.Cell
                    val r = tile[1] as Rectangle
                    if (obj.CollTileWhite == null && obj.CollTileGray == null ||
                            obj.CollTileWhite != null && c.tile.id in obj.CollTileWhite!! ||
                                obj.CollTileGray != null && c.tile.id in obj.CollTileGray!! &&
                                    obj.checkCollTile(deltatime,c,r.x.toInt(),r.y.toInt()))
                        obj.CollQueue.add(LabelledObject("Tile/${c.tile.id}",r))
                }
                q.clear()
            }
        }

        for (e in Living) e.collideWithTiles(deltatime)
        for (e in Living) e.poststep(deltatime)
        poststep(deltatime)
        for (e in Living) {
            e.Mailbox.clear()
            e.CollQueue.clear()
        }
    }

    open fun draw(deltatime: Float, obj: LabelledObject) {

    }

    fun CollectTiles(q: Queue<LabelledObject>, aoi: Rectangle) {
        val x1 = Math.floor(aoi.x.toDouble()).toInt()
        val y1 = Math.floor(aoi.y.toDouble()).toInt()
        val x2 = Math.ceil((aoi.x + aoi.width).toDouble()).toInt()
        val y2 = Math.ceil((aoi.y + aoi.height).toDouble()).toInt()
        //collide with tiles
        for (y in y1..y2) {
            for (x in x1..x2) {
                val c = LyrTerrain?.getCell(x, y)
                if (c != null && c.tile != null) //TODO: px->world
                    q.add(LabelledObject("Tile",arrayOf(c,Rectangle(x.toFloat(),y.toFloat(),1f,1f)))) //LyrTerrain!!.tileWidth,LyrTerrain!!.tileHeight))))

//                c = LyrTileObjects?.getCell(x, y)
//                if (c != null && c.tile != null)
//                    q.add(LabelledObject("TObj",arrayOf(c,Rectangle(x.toFloat(),y.toFloat(),LyrTileObjects!!.tileWidth,LyrTileObjects!!.tileHeight))))
            }
        }
//        //collide with non-tiles
//        val mapobj = LyrNonTiles?.objects?.getByType(RectangleMapObject::class.java)
//        if (mapobj != null) for (r in mapobj) {
//            if (aoi.overlaps(r.rectangle))
//                q.add(LabelledObject("Non/${r.name}",r.rectangle))
//        }
    }

}
