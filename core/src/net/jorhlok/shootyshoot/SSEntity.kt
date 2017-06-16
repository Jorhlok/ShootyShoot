package net.jorhlok.shootyshoot

import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.Entity

/**
 * Created by joshm on 6/15/2017.
 */
abstract class SSEntity(
        var MGR: MultiGfxRegister,
        var MAR: MultiAudioRegister) : Entity()  {
    companion object {
        open fun getId() = 0
    }
}