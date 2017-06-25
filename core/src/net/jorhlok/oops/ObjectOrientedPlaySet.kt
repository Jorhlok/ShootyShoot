package net.jorhlok.oops

import com.badlogic.gdx.maps.tiled.TiledMap
import java.util.*

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
    var FrameThreshold = 0.05f
    var SwitchAfterDraw = true

    //runtime
    var CallStack = LinkedList<DungeonMaster>()
    var iter: ListIterator<DungeonMaster>? = null

    fun addTileMap(key: String, map: TiledMap) {
        TileMap.put(key, map)
    }

    fun addMasterScript(key: String, script: DungeonMaster) {
        script.Parent = this
        MasterScript.put(key, script)
    }

    fun step(deltatime: Float) {
        if (deltatime < FrameThreshold) CallStack.first.update(deltatime)
        else {
            CallStack.first.update(FrameThreshold)
            System.err.println("Whoa, a frame took ${deltatime}s which is waaaaay longer than the threshold of ${FrameThreshold}s. " +
                    "Physics surely would have broken down had I let this frame run.")
        }
        if (!SwitchAfterDraw) switch()
    }

    fun switch() {
        while (CallStack.size > 0) {
            var first = CallStack.first
            val stop = first.ScriptStop
            val swap = MasterScript[first.ScriptSwap]
            if (stop) {
                first.end()
                first.dispose()
                CallStack.pop()
                if (CallStack.size > 0) {
                    CallStack.first.unpause()
                }
            } else if (swap != null) {
                first.end()
                first.dispose()
                CallStack.pop()
                CallStack.push(swap.clone())
                first = CallStack.first
                first.create(TileMap,this)
                first.begin()
            } else {
                val launch = MasterScript[first.ScriptLaunch]
                if (launch != null) {
                    first.pause()
                    CallStack.push(launch.clone())
                    first = CallStack.first
                    first.create(TileMap,this)
                    first.begin()
                }
                break
            }
        }
    }

    fun draw(deltatime: Float) {
        iter = CallStack.listIterator()
        iter!!.next().draw(deltatime)
        iter = null
        CallStack.first.flip()
        if (SwitchAfterDraw) switch()
    }

    override fun drawPrevious(deltatime: Float) {
            iter?.next()?.draw(deltatime)
    }

    fun launchScript(key: String) {
        if (CallStack.isEmpty()) {
            val s = MasterScript[key]
            if (s != null) {
                CallStack.add(s.clone())
                s.begin()
            }
        }
    }

    fun dispose() {
        while (CallStack.size > 0) {
            val s = CallStack.pop()
            s.end()
            s.dispose()
        }
        for (t in TileMap.values) {
            t.dispose()
        }
        TileMap.clear()
        GlobalData.clear()
        MasterScript.clear()
    }
}
