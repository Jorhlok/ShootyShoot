package net.jorhlok.shootyshoot

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.DungeonMaster

/**
 * Created by joshm on 6/17/2017.
 */
class Title(mapname: String,
            var MGR: MultiGfxRegister,
            var MAR: MultiAudioRegister) : DungeonMaster(mapname) {

    override fun begin() {
        MGR.camera.setToOrtho(false,640f,360f)
        MGR.updateCam()
        MGR.getBufCam("main")?.setToOrtho(false,640f,360f)
        MGR.setBufScalar("main")
    }

    override fun prestep(deltatime: Float) {

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Keys.ENTER))
            ScriptSwap = "testdm"
    }

    override fun draw(deltatime: Float) {
        MGR.startBuffer("main")
        MGR.clear(0.1f,0.1f,0.1f,1f)
        MGR.drawString("libmono","Press Start",320f,180.5f)
        MGR.stopBuffer()
//        MGR.drawBuffer("main")
//        MGR.flush()
    }

    override fun flip() {
        MGR.drawBuffer("main")
        MGR.flush()
    }

    override fun clone() = Title(MapName,MGR,MAR)
}