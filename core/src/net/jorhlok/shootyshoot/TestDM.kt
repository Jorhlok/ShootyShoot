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
        val LyrTileObj = Level!!.layers["TileObjects"] as TiledMapTileLayer?
        if (LyrTileObj != null) for (y in 0..LyrTileObj.height-1)
            for (x in 0..LyrTileObj.width-1) {
                val obj = LyrTileObj.getCell(x,y)
                if (obj != null && obj.tile != null) when (obj.tile.id) {
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
                    49 -> Living.add(Collectible("_mineral",x.toFloat(),y.toFloat(),MGR,MAR))
                    50 -> Living.add(Collectible("_herb",x.toFloat(),y.toFloat(),MGR,MAR))
                    51 -> Living.add(Collectible("_eyeball",x.toFloat(),y.toFloat(),MGR,MAR))
                    52 -> Living.add(Collectible("_worm",x.toFloat(),y.toFloat(),MGR,MAR))
                    53 -> Living.add(Collectible("_dollar",x.toFloat(),y.toFloat(),MGR,MAR))
                    54 -> Living.add(Collectible("_potion",x.toFloat(),y.toFloat(),MGR,MAR))
                }
            }

        MGR.camera.setToOrtho(false,640f,360f)
        MGR.updateCam()
        MGR.setBufScalar("main",1/16f)
        cam = MGR.getBufCam("main")!!
        cam.setToOrtho(false,640/16f,360/16f)
        cambounds.set(320/16f,180/16f,LyrTerrain!!.width-640/16f,LyrTerrain!!.height-360/16f)

        MAR.setMusVolume(0.125f)
        MAR.setSFXVolume(0.25f)
        val m = MAR.getMus("frcasio")
        if (m != null) {
            m.Generate()
            m.play(true)
        }

        Player = TestPlatformer(this,MGR,MAR)
        Player!!.Parent = this
        Player!!.Name = "Player"
        Living.add(Player!!)
    }

    override fun end() {
        val m = MAR.getMus("frcasio")
        if (m != null) {
            m.stop()
            m.dispose()
        }
        Living.clear()
        Player = null
    }

    override fun prestep(deltatime: Float) {
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

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S))
            Player!!.Mailbox.add(LabelledObject("CtrlDn",true))
        else
            Player!!.Mailbox.add(LabelledObject("CtrlDn",false))
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
        MGR.drawBuffer("main")
        MGR.flush()
    }
}
