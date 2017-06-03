package net.jorhlok.shootyshoot

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.DungeonMaster
import net.jorhlok.oops.Postage

class TestDM(mapname: String, temap: Map<Int, String>?) : DungeonMaster(mapname, temap) {
//    internal var render: OrthogonalTiledMapRenderer? = null
//    var cam = OrthographicCamera()
    var statetime = 0f

    override fun begin() {
//        cam.setToOrtho(false,640f,360f)
//        render = OrthogonalTiledMapRenderer(Level, 1f)
//        render!!.setView(cam!!)

        val e = mkEntity("testplat", "player", "player")
    }

    override fun update(deltatime: Float) {
        //poll input
        try {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP)
                    || Gdx.input.isKeyPressed(Input.Keys.W)) {
                for (e in Living!!["player"]!!) {
                    e.Mailbox.add(Postage(null, "jump", "control", 1))
                }
            } else {
                for (e in Living!!["player"]!!) {
                    e.Mailbox.add(Postage(null, "jump", "control", 0))
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                for (e in Living!!["player"]!!) {
                    e.Mailbox.add(Postage(null, "right", "control", 1))
                }
            } else {
                for (e in Living!!["player"]!!) {
                    e.Mailbox.add(Postage(null, "right", "control", 0))
                }
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                for (e in Living!!["player"]!!) {
                    e.Mailbox.add(Postage(null, "left", "control", 1))
                }
            } else {
                for (e in Living!!["player"]!!) {
                    e.Mailbox.add(Postage(null, "left", "control", 0))
                }
            }
            //update objects
            for (e in Living!!["player"]!!) {
                e.update(deltatime)
            }
        } catch (e: Exception) {
            System.err.println("Unable to update in TestDM because:\n" + e.toString() + "\n" + e.message)
        }

    }

    override fun draw(deltatime: Float, mgr: MultiGfxRegister?) {
//        render?.render()
        statetime += deltatime
        mgr?.drawTileLayer(LyrTerrain!!,"sprites",1f,statetime)
        try {
            for (e in Living!!["player"]!!) {
                e.draw(mgr)
            }
        } catch (e: Exception) {
            //meh
            e.printStackTrace()
        }

    }
}
