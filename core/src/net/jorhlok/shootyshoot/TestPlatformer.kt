package net.jorhlok.shootyshoot

import com.badlogic.gdx.math.Vector2
import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.Entity
import net.jorhlok.oops.LabelledObject

class TestPlatformer(
            var MGR: MultiGfxRegister,
            var MAR: MultiAudioRegister) : Entity() {
    var Gravity = Vector2(0f, -20f)
    var Grounded = false
    var JumpVelo = 20f
    var WalkVelo = 16f
    var WalkAccl = 12f

    var jump = false
    var left = false
    var right = false
    var drawdir = false

    var wobbletime = 0f
    val wobbledepth = 20f
    val wobbleperiod = 0.25f

    init {
        AABB.set(0.125f, 0f, 0.75f, 1f)
        Tolerance.set(0.5f, 0.5f)
        Position.set(5f, 8f)
        Friction.set(8f,0f)
        Physics = true
        CollTiles = true
        Type = "TestPlatformer"
    }

    override fun prestep(deltatime: Float) {

        Velocity.add(Gravity.cpy().scl(deltatime))

        for (p in Mailbox) {
            if (p.label.startsWith("Ctrl")) {
                val b = p.obj as Boolean
                when (p.label) {
                    "CtrlJump" -> {
                        val oldjump = jump
                        jump = b
                        if (/*Grounded &&*/ jump && !oldjump) {
                            //jump!
                            Velocity.add(0f, JumpVelo)
                            if (Velocity.y > JumpVelo) Velocity.y = JumpVelo
                            Grounded = false
                            MAR.playSFX("jump")
                        }
                    }
                    "CtrlLf" -> left = b
                    "CtrlRt" -> right = b
                }

                if (left && !right) {
                    Velocity.x -= WalkAccl * deltatime
                    if (Velocity.x < -1*WalkVelo) Velocity.x = -1*WalkVelo
                    drawdir = false
                } else if (right && !left) {
                    Velocity.x += WalkAccl * deltatime
                    if (Velocity.x > WalkVelo) Velocity.x = WalkVelo
                    drawdir = true
                }
            }
        }
        Mailbox.clear()
    }

    override fun poststep(deltatime: Float) {
        if (Velocity.y < 0)
            Grounded = false
        else if (Velocity.y == 0f) Grounded = true
//        for (m in Mailbox) {
//            System.out.println(m.label)
//        }
    }

    override fun draw(deltatime: Float) {
        var rot = 0f
        if (Velocity.x != 0f) {
            wobbletime += deltatime*Velocity.x/WalkVelo
            if (wobbletime > wobbleperiod) wobbletime -= wobbleperiod
            rot = Math.sin(wobbletime/wobbleperiod*2*Math.PI).toFloat()*wobbledepth
            if (Velocity.x > 0) rot *= -1
        }
        else wobbletime = 0f

        if (drawdir) {
            MGR.drawRgb("_guyrt", 0f, Position.x + 0.5f, Position.y,1f,1f,rot,Vector2(0.5f,0f))
            MGR.drawRgb("_gunrt", 0f, Position.x + 10/16f, Position.y,1f,1f,0f,Vector2())
        } else {
            MGR.drawRgb("_guylf", 0f, Position.x + 0.5f, Position.y,1f,1f,rot,Vector2(0.5f,0f))
            MGR.drawRgb("_gunlf", 0f, Position.x - 10/16f, Position.y,1f,1f,0f,Vector2())
        }
    }


}
