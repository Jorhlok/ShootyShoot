package net.jorhlok.shootyshoot

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import net.jorhlok.multiav.MultiGfxRegister
import net.jorhlok.oops.Physical
import net.jorhlok.oops.Postage
import net.jorhlok.oops.TMPCO

class TestPlatformer : Physical() {
    var Gravity: Vector2
    var Grounded: Boolean = false

    protected var jump: Boolean = false
    protected var left: Boolean = false
    protected var right: Boolean = false

    protected var drawdir: Boolean = false

    init {
        AABB.set(0.125f, 0f, 0.75f, 1f)
        Tolerance.set(0.5f, 0.5f)
        Position.set(5f, 8f)
        Gravity = Vector2(0f, -20f)
    }

    override fun prestep(deltatime: Float) {

        Velocity.add(Gravity.cpy().scl(deltatime))

        for (p in Mailbox) {
            if (p.Type == "control") {
                if (p.Name == "jump") {
                    val oldjump = jump
                    jump = p.iValue != 0
                    if (/*Grounded &&*/ jump && !oldjump) {
                        //jump!
                        Velocity.add(0f, 20f)
                        if (Velocity.y > 20) Velocity.y = 20f
                        Grounded = false
                    }
                } else if (p.Name == "left") {
                    left = p.iValue != 0
                    if (left) drawdir = false
                } else if (p.Name == "right") {
                    right = p.iValue != 0
                    if (right) drawdir = true
                }

                if (left && !right)
                    Velocity.x = -8f
                else if (right && !left)
                    Velocity.x = 8f
                else
                    Velocity.x = 0f
            }
        }
    }

    override fun step(deltatime: Float) {
        val len = CollisionTiles.size
        //ignore non-tiles
        for (i in 0..len - 1) {
            val tmp = CollisionTiles.poll()
            if (tmp != null && tmp.cell != null) {
                CollisionTiles.add(tmp)
            }
        }
    }

    override fun poststep(deltatime: Float) {
        if (Velocity.y < 0)
            Grounded = false
        else if (Velocity.y == 0f) Grounded = true
    }

    override fun draw(mgr: MultiGfxRegister?) {
        if (drawdir) {
            mgr?.drawRgb("_gunrt", 0f, (Position.x + 0.75f) * 16, Position.y * 16,1f,1f,0f,Vector2())
            mgr?.drawRgb("_guyrt", 0f, Position.x * 16, Position.y * 16,1f,1f,0f,Vector2())
        } else {
            mgr?.drawRgb("_gunlf", 0f, (Position.x - 0.75f) * 16, Position.y * 16,1f,1f,0f,Vector2())
            mgr?.drawRgb("_guylf", 0f, Position.x * 16, Position.y * 16,1f,1f,0f,Vector2())
        }
    }


}
