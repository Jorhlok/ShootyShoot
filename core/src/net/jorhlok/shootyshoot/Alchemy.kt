package net.jorhlok.shootyshoot

import com.badlogic.gdx.math.Vector2
import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister

/**
 * Created by joshm on 6/15/2017.
 */
class Alchemy(MGR: MultiGfxRegister, MAR: MultiAudioRegister) : SSEntity(MGR, MAR) {
    companion object {val TileNum = 35}
    override fun draw(deltatime: Float) {
        MGR.drawRgb("_alchemy",0f,Position.x,Position.y,1f,1f,0f, Vector2())
    }
}