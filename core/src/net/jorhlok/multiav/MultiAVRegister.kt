package net.jorhlok.multiav

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import java.util.HashMap

/**
 * Multi-Function Audio-Visual Organization Engine
 * @author Jorhlok
 */
class MultiAVRegister {
    //indexed gfx
    private var imagePal = HashMap<String, ImagePal>()
    private var framePal = HashMap<String, FramePal>()
    private var sequencePal = HashMap<String, SequencePal>()

    private var Letters = HashMap<Char, TextureRegion>()
    private var SFX = HashMap<String, SEffect>()
    private var Mus = HashMap<String, MTrack>()
    private var Font: BitmapFont? = null //assumes monospace font
    private var DrawableChars: String? = null

    var palette = Array<Color>()
    var batch: Batch? = null
    var scale = Vector2(1f,1f)
    var camPos = Vector2(0f,0f)
    var tabLength = 4f
    var fontSampling = 1f

    fun newImagePal(key: String, uri: String, tw: Int, th: Int) {
        imagePal[key]?.dispose()
        imagePal.put(key, ImagePal(key, uri, tw, th))
    }

    fun newSpritePal(key: String, img: String, tx: Int, ty: Int, tw: Int = 1, th: Int = 1, hf: Boolean = false, vf: Boolean = false, rot90: Int = 0) {
        framePal.put(key, FramePal(key, img, tx, ty, tw, th, hf, vf, rot90))
    }

    fun newAnimPal(key: String, frames: Array<String>, palseq: Array<Array<Short>?> = Array<Array<Short>?>(), speed: Float = 0f, mode: Animation.PlayMode = Animation.PlayMode.LOOP) {
        sequencePal.put(key, SequencePal(key, frames, palseq, speed, mode))
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
        for (t in imagePal.values)
            t.Generate()
        for (s in framePal) {
            s.value.Generate(imagePal)
            val arr = Array<String>()
            arr.add(s.key)

            val defaultpal = Array<Array<Short>?>()
            defaultpal.add(Array<Short>())
            var maxi: Short = 0
            for (k in s.value.Tile.keys) if (k > maxi) maxi = k
            for (i in 0..maxi) defaultpal[0]!!.add(i.toShort())

            newAnimPal("_" + s.key, arr, defaultpal) //auto generate single framePal animation for each framePal
        }
        for (a in sequencePal.values)
            a.Generate(framePal)

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

    fun drawPal(anim: String, indexoffset: Short, statetime: Float, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2? = null) {
        if (batch != null) sequencePal[anim]?.draw(batch!!,palette,indexoffset,statetime,x,y,sw,sh,rot,center)
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
        for (t in imagePal.values)
            t.dispose()
        for (s in framePal.values)
            s.dispose()
        for (a in sequencePal.values)
            a.dispose()
        for (s in SFX.values)
            s.dispose()
        for (m in Mus.values)
            m.dispose()
    }
}
