package net.jorhlok.shootyshoot

import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister

/**
 * Created by joshm on 6/15/2017.
 */
class ThruPlat(MGR: MultiGfxRegister, MAR: MultiAudioRegister) : SSEntity(MGR, MAR) {
    companion object {val TileNum = 67; val TypeName = "ThruPlat"}
    init {
        Type = ThruPlat.TypeName
        CollEntities = true //allow collisions
        CollEntWhite = emptySet() //but I don't pay attention
        AABB.set(0f,6/16f,1f,4/16f)
    }
    override fun draw(deltatime: Float) {
        MGR.drawRgb("_bluebar",0f,Position.x+0.5f,Position.y+0.5f)
    }
}