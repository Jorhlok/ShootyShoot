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
        AABB.set(0f,0f,1f,1f)
        val s = HashSet<String>()
        s.add("TestPlatformer")
        CollEntWhite = s
        CollEntities = true
    }

    override fun poststep(deltatime: Float) {
        for (m in Mailbox) {
            if (hidetime >= hidelen && m.label.startsWith("CollEnt/")) {
                hidetime = deltatime*-1
                statetime = hidetime-hidelen
                MAR.playSFX("pew")
            }
        }
        if (hidetime < hidelen) hidetime += deltatime
    }

    override fun draw(deltatime: Float) {
        statetime += deltatime
        if (statetime > animlen) statetime -= animlen
        if (hidetime >= hidelen) MGR.drawRgb(anim,0f,Position.x+0.5f,Position.y+0.5f+Math.sin(statetime/animlen*Math.PI*2).toFloat()*hoverdepth)
    }
}