package net.jorhlok.shootyshoot

import com.badlogic.gdx.math.Vector2
import net.jorhlok.multiav.MultiAudioRegister
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.Entity
import net.jorhlok.oops.LabelledObject

class TestPlatformer : Entity() {
    var Gravity = Vector2(0f, -20f)
    var Grounded = false
    var JumpVelo = 20f

    var jump = false
    var left = false
    var right = false
    var drawdir = false
    var newjump = false

    init {
        AABB.set(0.125f, 0f, 0.75f, 1f)
        Tolerance.set(0.5f, 0.5f)
        Position.set(5f, 8f)
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
                            newjump = true
                        }
                    }
                    "CtrlLf" -> left = b
                    "CtrlRt" -> right = b
                }

                if (left && !right) {
                    Velocity.x = -8f
                    drawdir = false
                } else if (right && !left) {
                    Velocity.x = 8f
                    drawdir = true
                } else
                    Velocity.x = 0f
            }
        }
        Mailbox.clear()
    }

//    override fun step(deltatime: Float) {
//        val len = CollisionTiles.size
//        //ignore non-tiles
//        for (i in 0..len - 1) {
//            val tmp = CollisionTiles.poll()
//            if (tmp != null && tmp.cell != null) {
//                CollisionTiles.add(tmp)
//            }
//        }
//    }

    override fun poststep(deltatime: Float) {
        if (Velocity.y < 0)
            Grounded = false
        else if (Velocity.y == 0f) Grounded = true
        for (m in Mailbox) {
            System.out.println(m.label)
        }
    }

    override fun draw(deltatime: Float, obj: LabelledObject) {
        var o = obj.obj as Array<Any>
        var mgr = o[0] as MultiGfxRegister
        var mar = o[1] as MultiAudioRegister
        if (drawdir) {
            mgr?.drawRgb("_gunrt", 0f, (Position.x + 0.75f) * 16, Position.y * 16,1f,1f,0f,Vector2())
            mgr?.drawRgb("_guyrt", 0f, Position.x * 16, Position.y * 16,1f,1f,0f,Vector2())
        } else {
            mgr?.drawRgb("_gunlf", 0f, (Position.x - 0.75f) * 16, Position.y * 16,1f,1f,0f,Vector2())
            mgr?.drawRgb("_guylf", 0f, Position.x * 16, Position.y * 16,1f,1f,0f,Vector2())
        }
        if (newjump) {
            newjump = false
            mar.playSFX("jump")
        }
    }


}
