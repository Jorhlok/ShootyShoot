package net.jorhlok.shootyshoot

import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister

/**
 * Created by joshm on 6/16/2017.
 */
class Collectible(val anim: String, x: Float, y:Float, MGR: MultiGfxRegister, MAR: MultiAudioRegister) : SSEntity(MGR, MAR) {
    val animlen = 2f
    var statetime = Math.random().toFloat()*animlen
    val hoverdepth = 4/16f
    val hidelen = 5f
    var hidetime = hidelen

    init {
        Position.set(x,y)
        AABB.set(2/16f,2/16f,12/16f,12/16f)
        val s = HashSet<String>()
        s.add("TestPlatformer")
        CollEntWhite = s
        CollEntities = true
    }

    override fun poststep(deltatime: Float) {
        for (m in Mailbox) {
            if (hidetime >= hidelen && m.label.startsWith("CollEnt/")) {
                hidetime = deltatime*-1
                statetime = hidetime
                MAR.playSFX("pew")
            }
        }
        if (hidetime < hidelen) hidetime += deltatime
        statetime += deltatime
        if (statetime > animlen) statetime -= animlen
    }

    override fun draw(deltatime: Float) {
        if (hidetime >= hidelen) MGR.drawRgb(anim,0f,Position.x+0.5f,Position.y+0.5f+Math.sin(statetime/animlen*Math.PI*2).toFloat()*hoverdepth)
    }
}