package net.jorhlok.oops

import com.badlogic.gdx.maps.tiled.TiledMap
import java.util.HashMap

/**
 * Object Oriented Gameplay Setup
 * Registers maps, levels, and kinds of entities
 * Meant to be used as-is
 * @author Jorhlok
 */
class ObjectOrientedPlaySet : DungeonMaster.OOPS {
    //setup
    var TileMap: MutableMap<String, TiledMap> = HashMap()
    var MasterScript: MutableMap<String, DungeonMaster> = HashMap()
    override var GlobalData: MutableMap<String, LabelledObject> = HashMap()
    var FrameThreshold = 0.25f

    //runtime
    var Here: DungeonMaster? = null

    fun addTileMap(key: String, map: TiledMap) {
        TileMap.put(key, map)
    }

    fun addMasterScript(key: String, script: DungeonMaster) {
        script.Parent = this
        MasterScript.put(key, script)
    }

    fun step(deltatime: Float) {
        if (deltatime < FrameThreshold) Here?.update(deltatime)
        else System.err.println("Whoa, a frame took ${deltatime}s which is waaaaay longer than the threshold of ${FrameThreshold}.\n" +
                "Physics surely would have broken down had I let this frame run.")
    }

    fun draw(deltatime: Float) {
        if (Here != null) Here!!.draw(deltatime)
    }

    override fun launchScript(key: String) {
        if (Here != null) {
            Here!!.end()
            Here!!.dispose()
        }
        Here = MasterScript[key]
        if (Here != null) {
            Here!!.create(TileMap,this)
            Here!!.begin()
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
        GlobalData.clear()
        MasterScript.clear()
    }
}
