package net.jorhlok.oops

import com.badlogic.gdx.maps.tiled.TiledMap
import java.util.HashMap
import net.jorhlok.multiav.MultiGfxRegister

/**
 * Object Oriented Gameplay Setup
 * Registers maps, levels, and kinds of entities
 * Meant to be used as-is
 * @author Jorhlok
 */
class ObjectOrientedPlaySet {
    //setup
    var TileMap: MutableMap<String, TiledMap> = HashMap()
    var EntityType: MutableMap<String, Class<out Entity>> = HashMap()
    var MasterScript: MutableMap<String, DungeonMaster> = HashMap()

    //runtime
    var MGR: MultiGfxRegister? = null
    var Here: DungeonMaster? = null

    fun addTileMap(key: String, map: TiledMap) {
        TileMap.put(key, map)
    }

    fun addEntityType(key: String, clazz: Class<out Entity>) {
        EntityType.put(key, clazz)
    }

    fun addMasterScript(key: String, script: DungeonMaster) {
        script.Parent = this
        MasterScript.put(key, script)
    }

//    fun setMGR(mgr: MultiGfxRegister) {
//        MGR = mgr
//    }

    fun step(deltatime: Float) {
        if (Here != null) Here!!.update(deltatime)
    }

    fun draw(deltatime: Float) {
        if (Here != null) Here!!.draw(deltatime, MGR)
    }

    fun launchScript(key: String) {
        if (Here != null) {
            Here!!.end()
            Here!!.dispose()
        }
        Here = MasterScript[key]
        if (Here != null) {
            Here!!.create(TileMap, EntityType)
            Here!!.begin()
        }
    }

    /**
     * Polymorphism to the max!
     */
    fun mkentity(key: String): Entity? {
        try {
            return EntityType[key]?.newInstance()
        } catch (e: Exception) {
            System.err.println("Unable to make a new " + key + " because:\n" + e.toString())
            return null
        }

    }

    fun dispose() {
        if (Here != null) {
            Here!!.end()
            Here!!.dispose()
        }
        for (t in TileMap.values) {
            t.dispose()
        }
        TileMap.clear()
        EntityType.clear()
        MasterScript.clear()
    }
}
