package net.jorhlok.shootyshoot

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.DungeonMaster
import net.jorhlok.oops.LabelledObject

class TestDM(mapname: String) : DungeonMaster(mapname) {
//    internal var render: OrthogonalTiledMapRenderer? = null
//    var cam = OrthographicCamera()
    var statetime = 0f
    var Player: TestPlatformer? = null

    override fun begin() {
//        cam.setToOrtho(false,640f,360f)
//        render = OrthogonalTiledMapRenderer(Level, 1f)
//        render!!.setView(cam!!)

        Player = TestPlatformer()
        Player!!.Parent = this
        Player!!.Name = "Player"
        Living.add(Player!!)
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
    }

    override fun draw(deltatime: Float, obj: LabelledObject) {
//        render?.render()
        statetime += deltatime
        ((obj.obj as Array<Any>)[0] as MultiGfxRegister)?.drawTileLayer(LyrTerrain!!,"sprites",1f,statetime)
        for (e in Living) {
            e.draw(deltatime,obj)
        }

    }
}
