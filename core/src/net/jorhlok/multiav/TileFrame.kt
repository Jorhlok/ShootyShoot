package net.jorhlok.multiav

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

/**
 * Created by joshm on 5/20/2017.
 */

class TileFrame (
        var Name: String = "",
        var Image: String = "",
        var x: Int = 0,
        var y: Int = 0,
        var width: Int = 1,
        var height: Int = 1,
        var HFlip: Boolean = false,
        var VFlip: Boolean = false,
        var Rot90: Int = 0) {
    //input

    //generated
    var Tile = HashMap<Short, Sprite>()

    fun Generate(map: Map<String,TexGrid>) {
        var tex = map[Image]
        if (tex != null) for (t in tex.Tex) {
            var l = t.value
            var s = Sprite(l, x*tex.TWidth, y*tex.THeight, width*tex.TWidth, height*tex.THeight)
            s.setFlip(HFlip,VFlip)
            for (i in 0..Rot90-1) s.rotate90(false)
            s.setOriginCenter() //default in the center
            Tile[t.key] = s
        }
    }

    fun draw(batch: Batch, pal: Array<Short>, bigpal: Array<Color>, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2? = null) {
        for (i in 0..pal.size-1) {
            var spr = Tile[i.toShort()]
            var j = pal[i]
            if (spr != null && j != null) {
                var col = bigpal[j.toInt()]
                if (col != null && col.a > 0) {
                    var s = Sprite(spr)
                    if (center != null) s.setOrigin(center.x, center.y)
                    s.translate(x, y)
                    s.setScale(sw, sh)
                    s.rotate(rot)
                    s.color = col
                    try {
                        s.draw(batch)
                    } catch (e: Exception) {/*oh, well*/}
                }
            }
        }
    }

    fun dispose() {
        Tile = HashMap<Short, Sprite>()
    }
}