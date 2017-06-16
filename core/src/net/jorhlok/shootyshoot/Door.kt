package net.jorhlok.shootyshoot

import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister

/**
 * Created by joshm on 6/15/2017.
 */
class Door(MGR: MultiGfxRegister, MAR: MultiAudioRegister) : SSEntity(MGR, MAR) {
    companion object {val TileNum = 33}
    override fun draw(deltatime: Float) {
        MGR.drawRgb("_door",0f,Position.x+0.5f,Position.y+0.5f)
    }
}