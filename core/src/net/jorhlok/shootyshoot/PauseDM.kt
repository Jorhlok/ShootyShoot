package net.jorhlok.shootyshoot

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.DungeonMaster
import net.jorhlok.oops.LabelledObject

/**
 * Created by joshm on 7/20/2017.
 */
class PauseDM(var mapname: String,
              var MGR: MultiGfxRegister,
              var MAR: MultiAudioRegister) : DungeonMaster(mapname) {

    var enter = true
    var x = 0f
    var y = 0f

    override fun begin() {
        val cam = MGR.getBufCam("main")
        if (cam != null) {
            x = cam.position.x
            y = cam.position.y
        }
    }

    override fun prestep(deltatime: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Parent!!.GlobalData.put("quitting", LabelledObject("y"))
            ScriptStop = true
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            if (!enter) ScriptStop = true
        }
        else enter = false
    }

    override fun draw(deltatime: Float) {
        Parent!!.drawPrevious(deltatime)
        MGR.startBuffer("main")
        MGR.drawString("libmono","PAUSE",x,y)
        MGR.stopBuffer()
    }

    override fun flip() {
        MGR.drawBuffer("main")
        MGR.flush()
    }

    override fun clone() = PauseDM(mapname, MGR,MAR)
}