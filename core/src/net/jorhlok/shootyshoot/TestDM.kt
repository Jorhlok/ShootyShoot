package net.jorhlok.shootyshoot

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Rectangle
import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.DungeonMaster
import net.jorhlok.oops.LabelledObject

class TestDM(mapname: String,
             var MGR: MultiGfxRegister,
             var MAR: MultiAudioRegister) : DungeonMaster(mapname) {
    var cam = OrthographicCamera()
    var cambounds = Rectangle()
    var statetime = 0f
    var Player: TestPlatformer? = null

    override fun begin() {
        val LyrTileObj = Level!!.layers["TileObjects"] as TiledMapTileLayer
        for (y in 0..LyrTileObj.height-1)
            for (x in 0..LyrTileObj.width-1) {
                val obj = LyrTileObj.getCell(x,y)
//                System.out.println("${obj?.tile?.id}")
                if (obj != null && obj.tile != null) when (obj.tile.id) {
                    //49-54 objects
                    //71 black box
                    //72 moving
                    Door.TileNum -> {
                        val o = Door(MGR,MAR)
                        o.Position.set(x.toFloat(),y.toFloat())
                        Living.add(o)
                    }
                    Pedestal.TileNum -> {
                        val o = Pedestal(MGR,MAR)
                        o.Position.set(x.toFloat(),y.toFloat())
                        Living.add(o)
                    }
                    Alchemy.TileNum -> {
                        val o = Alchemy(MGR,MAR)
                        o.Position.set(x.toFloat(),y.toFloat())
                        Living.add(o)
                    }
                    ThruPlat.TileNum -> {
                        val o = ThruPlat(MGR,MAR)
                        o.Position.set(x.toFloat(),y.toFloat())
                        Living.add(o)
                    }
                }
            }

        MGR.setBufScalar("main",1/16f)
        cam = MGR.getBufCam("main")!!
        cam.setToOrtho(false,640/16f,360/16f)
        cambounds.set(320/16f,180/16f,LyrTerrain!!.width-640/16f,LyrTerrain!!.height-360/16f)

        MAR.setMusVolume(0.125f)
        MAR.setSFXVolume(0.25f)

        Player = TestPlatformer(MGR,MAR)
        Player!!.Parent = this
        Player!!.Name = "Player"
        Living.add(Player!!)
    }

    override fun prestep(deltaTime: Float) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.UP)
                    || Gdx.input.isKeyPressed(Input.Keys.W))
                Player!!.Mailbox.add(LabelledObject("CtrlJump",true))
            else
                Player!!.Mailbox.add(LabelledObject("CtrlJump",false))

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
                Player!!.Mailbox.add(LabelledObject("CtrlRt",true))
            else
                Player!!.Mailbox.add(LabelledObject("CtrlRt",false))

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
                Player!!.Mailbox.add(LabelledObject("CtrlLf",true))
            else
                Player!!.Mailbox.add(LabelledObject("CtrlLf",false))
    }

    override fun draw(deltatime: Float) {
        statetime += deltatime

        cam.position.set(keepPointInBox(Player!!.Position.cpy().add(0.5f,0.5f),cambounds),0f)

        cam.update()

        MGR.startBuffer("main")
//        MGR.clear(0.1f,0.1f,0.1f,1f)
        MGR.clear(0f,1f,1f,1f)
        MGR.drawTileLayer(LyrTerrain!!,"sprites",1f,statetime)
        for (e in Living) {
            e.draw(deltatime)
        }
        MGR.stopBuffer()
        MGR.clear()
        MGR.drawBuffer("main")
        MGR.flush()
    }
}
