package net.jorhlok.oops

import java.util.LinkedList
import java.util.Queue
import net.jorhlok.multiav.MultiGfxRegister

/**
 * Some sort of entity. May or may not exist in worldspace.
 * @author Jorhlok
 */
open class Entity {
    var Name = ""
    var Type = ""
    var Mailbox: Queue<Postage> = LinkedList()
    var Maestro: DungeonMaster? = null

    open fun update(deltatime: Float) {
        prestep(deltatime)
        step(deltatime)
        poststep(deltatime)
        Mailbox.clear()
    }

    open fun create() {}
    open fun prestep(deltatime: Float) {}
    open fun step(deltatime: Float) {}
    open fun poststep(deltatime: Float) {}
    open fun draw(mgr: MultiGfxRegister?) {}
}
