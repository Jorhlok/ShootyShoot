package net.jorhlok.multiav

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap

/**
 * Created by joshm on 5/19/2017.
 */
class TexGrid (
    val Name: String = "",
    val URI: String = "",
    val TWidth: Int = 1,
    val THeight:Int = 1){

    var Tex = HashMap<Short, Texture>()

    fun Generate(maxIndex:Int = 4096) {
        val maxVal = if (maxIndex in 1..4096) maxIndex else 4096
        var pix = Gdx2DPixmap(Gdx.files.internal(URI).read(),Pixmap.Format.toGdx2DPixmapFormat(Pixmap.Format.RGB888))
        var buf = pix.pixels
        var layers = HashMap<Short, Pixmap>()
        for (i in 0..pix.width*pix.height-1) {
            //construct indexed color from the least significant nibble of each color channel
            //      images need to be created with this in mind ahead of time
            //      supports 16, 256, and 4096 color palettes
            var b = buf.get().toInt()
            var index = 0
            if (maxVal > 256) {
                b = if(b<0) b+256 else b
                index = b % 16 * 16 * 16
            }

            b = buf.get().toInt()
            if (maxVal > 16) {
                b = if(b<0) b+256 else b
                index +=  b % 16 * 16
            }

            b = buf.get().toInt()
            b = if(b<0) b+256 else b
            index = (index + b % 16) % maxVal

            var p = layers[index.toShort()]

            if (p == null) {
                p = Pixmap(pix.width,pix.height,Pixmap.Format.Alpha)
                p.setColor(0f,0f,0f,0f)
                p.fill()
                p.setColor(1f,1f,1f,1f)
                layers[index.toShort()] = p
            }

            p.drawPixel(i%pix.width, i/pix.width)
        }
        pix.dispose()

        for (p in layers) {
            Tex[p.key] = Texture(p.value)
            p.value.dispose()
        }
    }

    fun dispose() {
        for (t in Tex) t.value.dispose()
    }

}