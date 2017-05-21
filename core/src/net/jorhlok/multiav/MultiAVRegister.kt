package net.jorhlok.multiav

import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import java.util.HashMap

/**
 * Multi-Function Audio-Visual Organization Engine
 * @author Jorhlok
 */
class MultiAVRegister {
    protected var image: MutableMap<String, TexGrid>
    var frame = HashMap<String, TileFrame>()
    protected var jAnim: MutableMap<String, jAnimSeq>
    protected var Letters: MutableMap<Char, TextureRegion>
    protected var SFX: MutableMap<String, SEffect>
    protected var Mus: MutableMap<String, MTrack>
    protected var Font: BitmapFont? = null //assumes monospace font
    protected var DrawableChars: String? = null

    var batch: Batch? = null
    var scale = Vector2(1f,1f)
    var camPos = Vector2(0f,0f)
    var tabLength = 4f
    var fontSampling = 1f

    init {
        image = HashMap<String, TexGrid>()
//        frame = HashMap<String, TileFrame>()
        jAnim = HashMap<String, jAnimSeq>()
        Letters = HashMap<Char, TextureRegion>()
        SFX = HashMap<String, SEffect>()
        Mus = HashMap<String, MTrack>()
    }

    fun newImage(key: String, uri: String, tw: Int, th: Int) {
        image[key]?.dispose()
        image.put(key, TexGrid(key, uri, tw, th))
    }

    fun newSprite(key: String, img: String, tx: Int, ty: Int, tw: Int = 1, th: Int = 1, hf: Boolean = false, vf: Boolean = false, rot90: Int = 0) {
        frame.put(key, TileFrame(key, img, tx, ty, tw, th, hf, vf, rot90))
    }

    fun newAnim(key: String, frames: Array<String>, speed: Float, mode: Animation.PlayMode?) {
        jAnim.put(key, jAnimSeq(key, frames, speed, mode))
    }

    fun setFont(f: BitmapFont, chars: String) {
        if (Font != null) Font!!.dispose()
        Font = f
        DrawableChars = chars
    }

    fun newSFX(key: String, uri: String) {
        val s = SFX[key]
        s?.dispose()
        SFX.put(key, SEffect(key, uri))
    }

    fun newMusic(key: String, uri: String) {
        val m = Mus[key]
        m?.dispose()
        Mus.put(key, MTrack(key, uri))
    }

    fun newMusic(key: String, intro: String, body: String) {
        val m = Mus[key]
        m?.dispose()
        Mus.put(key, MTrack(key, intro, body))
    }

    fun Generate() {
        batch = SpriteBatch()
        for (t in image.values)
            t.Generate()
        for (s in frame.values) {
            s.Generate(image)
            newAnim("_" + s.Name, arrayOf(s.Name), 0f, null) //auto generate single frame animation for each frame
        }
        for (a in jAnim.values)
//            a.Generate(frame)
        if (Font != null && DrawableChars != null && !DrawableChars!!.isEmpty()) {
            val dat = Font!!.data
            for (i in 0..DrawableChars!!.length - 1) {
                val glyph = dat.getGlyph(DrawableChars!![i])
                if (glyph != null) {
                    Letters.put(DrawableChars!![i], TextureRegion(Font!!.getRegion(glyph.page).texture, glyph.u, glyph.v, glyph.u2, glyph.v2))
                }
            }
        }
        for (s in SFX.values)
            s.Generate()
    }

    fun drawRegion(reg: TextureRegion, x: Float, y: Float, sw: Float, sh: Float, rot: Float) {
        //For some reson was drawing with width and height swapped and rotated 90 degrees when done normally
        batch?.draw(reg, x, y, 0f, 0f, reg.regionHeight.toFloat() * scale.y * sh, reg.regionWidth.toFloat() * scale.x * sw, 1f, 1f, rot - 90, false)


    }

    @JvmOverloads fun draw(anim: String, statetime: Float, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f) {
        var x = x
        var y = y
        try {
            val reg = jAnim[anim]?.getKeyFrame(statetime)
            if (reg != null) {
                x += (reg.regionHeight.toDouble() * scale.y.toDouble() * sh.toDouble() * Math.sin(Math.toRadians((-1 * rot).toDouble()))).toFloat()
                y += (reg.regionHeight.toDouble() * scale.y.toDouble() * sh.toDouble() * Math.cos(Math.toRadians((-1 * rot).toDouble()))).toFloat()
                drawRegion(reg, x, y, sw, sh, rot)
            }
        } catch (e: Exception) {
            //nothing
        }

    }

    @JvmOverloads fun drawString(str: String, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f) {
        var x = x
        var y = y
        x /= 2f
        y /= 2f
        val w = Font!!.spaceWidth
        val h = Font!!.lineHeight
        x += (h.toDouble() * scale.y.toDouble() * sh.toDouble() * 0.5 * Math.sin(Math.toRadians((-1 * rot).toDouble())) / fontSampling).toFloat()
        y += (h.toDouble() * scale.y.toDouble() * sh.toDouble() * 0.5 * Math.cos(Math.toRadians((-1 * rot).toDouble())) / fontSampling).toFloat()
        val xtrav = w.toDouble() * Math.cos(Math.toRadians(rot.toDouble())) * sw.toDouble() * scale.x.toDouble() / fontSampling
        val ytrav = w.toDouble() * Math.sin(Math.toRadians(rot.toDouble())) * sw.toDouble() * scale.y.toDouble() / fontSampling
        val xjmp = h.toDouble() * Math.sin(Math.toRadians(rot.toDouble())) * sh.toDouble() * scale.x.toDouble() / fontSampling
        val yjmp = h.toDouble() * Math.cos(Math.toRadians(rot.toDouble())) * sh.toDouble() * scale.y.toDouble() / fontSampling

        var xcur = x.toDouble()
        var ycur = y.toDouble()
        var linesdown = 0
        for (i in 0..str.length - 1) {
            val c = str[i]
            when (c) {
                '\n' -> {
                    ++linesdown
                    xcur = x + xjmp * linesdown
                    ycur = y - yjmp * linesdown
                }
                '\t' -> {
                    xcur += xtrav * tabLength
                    ycur += ytrav * tabLength
                }
                else -> {
                    val reg = Letters[c]
                    val xoff = (Font!!.data.getGlyph(c).xoffset / w).toDouble()
                    val yoff = (Font!!.data.getGlyph(c).yoffset / h).toDouble()
                    if (reg != null)
                        drawRegion(reg, x + (xcur + xoff * xtrav - yoff * xjmp).toFloat(), y + (ycur + xoff * ytrav + yoff * yjmp).toFloat(), sw / fontSampling, sh * -1 / fontSampling, rot)
                    xcur += xtrav
                    ycur += ytrav
                }
            }
        }
    }

    fun getSFX(key: String): SEffect? {
        return SFX[key]
    }

    fun getMus(key: String): MTrack? {
        return Mus[key]
    }

    fun playSFX(key: String) {
        SFX[key]?.play()
    }

    fun setSFXVolume(v: Float) {
        for (s in SFX.values)
            s.setVolume(v)
    }

    fun setMusVolume(v: Float) {
        for (m in Mus.values)
            m.setVolume(v)
    }

    fun killMusic() {
        for (m in Mus.values) {
            m.stop()
            m.dispose()
        }
    }

    fun dispose() {
        Font?.dispose()
        batch?.dispose()
        for (t in image.values)
            t.dispose()
        for (s in frame.values)
            s.dispose()
        for (a in jAnim.values)
            a.dispose()
        for (s in SFX.values)
            s.dispose()
        for (m in Mus.values)
            m.dispose()
    }
}
