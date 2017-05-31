package net.jorhlok.multiav

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import java.util.HashMap

/**
 * Multi-Function Audio-Visual Organization Engine
 * @author Jorhlok
 */
class MultiGfxRegister {
    //indexed gfx
    private var imagePal = HashMap<String, ImagePal>()
    private var framePal = HashMap<String, FramePal>()
    private var sequencePal = HashMap<String, SequencePal>()

    //rgb gfx
    private var imageRgb = HashMap<String, ImageRgb>()
    private var frameRgb = HashMap<String, FrameRgb>()
    private var sequenceRgb = HashMap<String, SequenceRgb>()

    //bitmap fonts
    private var fonts = HashMap<String, BitmapFont>()

    //shape gfx


//    private var Letters = HashMap<Char, TextureRegion>()
//    private var SFX = HashMap<String, SEffect>()
//    private var Mus = HashMap<String, MTrack>()
//    private var Font: BitmapFont? = null //assumes monospace fonts
//    private var DrawableChars: String? = null

    var batch: Batch? = null
    var sr: ShapeRenderer? = null
    var mainfb: FrameBuffer? = null
    var mainfbtex: TextureRegion? = null
    var fxfb: FrameBuffer? = null
    var fxfbtex: TextureRegion? = null
    var camera: OrthographicCamera? = null

    var palette = Array<Color>()
    var xyscale = Vector2(1f,1f)
    var camPos = Vector2(0f,0f)
//    var tabLength = 4f
//    var fontSampling = 1f

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

    fun newImageRgb(key: String, uri: String, tw: Int, th: Int) {
        imageRgb[key]?.dispose()
        imageRgb.put(key, ImageRgb(key, uri, tw, th))
    }

    fun newSpriteRgb(key: String, img: String, tx: Int, ty: Int, tw: Int = 1, th: Int = 1, hf: Boolean = false, vf: Boolean = false, rot90: Int = 0) {
        frameRgb.put(key, FrameRgb(key, img, tx, ty, tw, th, hf, vf, rot90))
    }

    fun newAnimRgb(key: String, frames: Array<String>, speed: Float = 0f, mode: Animation.PlayMode = Animation.PlayMode.LOOP) {
        sequenceRgb.put(key, SequenceRgb(key, frames, speed, mode))
    }

    fun newFont(key: String, f: BitmapFont) {
        fonts.put(key,f)
    }

//    fun setFont(f: BitmapFont, chars: String) {
//        if (Font != null) Font!!.dispose()
//        Font = f
//        DrawableChars = chars
//    }

//    fun newSFX(key: String, uri: String) {
//        val s = SFX[key]
//        s?.dispose()
//        SFX.put(key, SEffect(key, uri))
//    }
//
//    fun newMusic(key: String, uri: String) {
//        val m = Mus[key]
//        m?.dispose()
//        Mus.put(key, MTrack(key, uri))
//    }
//
//    fun newMusic(key: String, intro: String, body: String) {
//        val m = Mus[key]
//        m?.dispose()
//        Mus.put(key, MTrack(key, intro, body))
//    }

    fun Generate() {
        batch = SpriteBatch()
        //indexed color
        for (t in imagePal.values)
            t.Generate()
        for (s in framePal) {
            s.value.Generate(imagePal)
            val defaultpal = Array<Array<Short>?>(arrayOf(Array<Short>()))
            var maxi: Short = 0
            for (k in s.value.Tile.keys) if (k > maxi) maxi = k
            for (i in 0..maxi) defaultpal[0]!!.add(i.toShort())

            newAnimPal("_" + s.key, Array<String>(arrayOf(s.key)), defaultpal) //auto generate single frame animation for each framePal
        }
        for (a in sequencePal.values)
            a.Generate(framePal)
        //rgba color
        for (t in imageRgb.values)
            t.Generate()
        for (s in frameRgb) {
            s.value.Generate(imageRgb)
            newAnimRgb("_" + s.key, Array<String>(arrayOf(s.key))) //auto generate single frame animation for each frameRgb
        }
        for (a in sequenceRgb.values)
            a.Generate(frameRgb)

//        if (Font != null && DrawableChars != null && !DrawableChars!!.isEmpty()) {
//            val dat = Font!!.data
//            for (i in 0..DrawableChars!!.length - 1) {
//                val glyph = dat.getGlyph(DrawableChars!![i])
//                if (glyph != null) {
//                    Letters.put(DrawableChars!![i], TextureRegion(Font!!.getRegion(glyph.page).texture, glyph.u, glyph.v, glyph.u2, glyph.v2))
//                }
//            }
//        }

//        for (s in SFX.values)
//            s.Generate()
    }

//    fun drawRegion(reg: TextureRegion, x: Float, y: Float, sw: Float, sh: Float, rot: Float) {
//        //For some reson was drawing with width and height swapped and rotated 90 degrees when done normally
//        batch?.draw(reg, x, y, 0f, 0f, reg.regionHeight.toFloat() * scale.y * sh, reg.regionWidth.toFloat() * scale.x * sw, 1f, 1f, rot - 90, false)
//
//
//    }

    fun drawPal(anim: String, indexoffset: Short, statetime: Float, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2? = null) {
        if (batch != null) sequencePal[anim]?.draw(batch!!,palette,indexoffset,statetime,x,y,sw,sh,rot,center)
    }

    fun drawRgb(anim: String, statetime: Float, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2? = null, col: Color? = null) {
        if (batch != null) sequenceRgb[anim]?.draw(batch!!,statetime,x,y,sw,sh,rot,center, col)
    }

    fun drawString(f: String, str: String, x: Float, y: Float, center: Vector2? = null, col: Color? = null) {
        var font = fonts[f]
        if (font != null) {
            if (center != null && center.x == 0f && center.y == 0f) {
                if (col != null) batch?.color = col
                font.draw(batch,str,x,y)
            }
            else {
                var lay = GlyphLayout(font,str)
                var pos = Vector2()
                if (center == null) {
                    pos.x = lay.width/2
                    pos.y = lay.height
                } else {
                    pos.x = lay.width*center.x
                    pos.y = lay.height*center.y*2
                }
                if (col != null) batch?.color = col
                font.draw(batch,lay,x-pos.x,y+pos.y)
            }
        }
    }

    fun drawStringExt(f: String, str: String, x: Float, y: Float, center: Vector2? = null, col: Color? = null, targetWidth: Float, halign: Int, wrap: Boolean) {
//        fonts[f]
    }

    fun drawStringFb(f: String, str: String, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2? = null, col: Color? = null) {
//        fonts[f]
    }

    fun drawStringFbExt(f: String, str: String, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2? = null, col: Color? = null, targetWidth: Float, halign: Int, wrap: Boolean) {
//        fonts[f]
    }

//    @JvmOverloads fun drawString(str: String, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f) {
//        var x = x
//        var y = y
//        x /= 2f
//        y /= 2f
//        val w = Font!!.spaceWidth
//        val h = Font!!.lineHeight
//        x += (h.toDouble() * scale.y.toDouble() * sh.toDouble() * 0.5 * Math.sin(Math.toRadians((-1 * rot).toDouble())) / fontSampling).toFloat()
//        y += (h.toDouble() * scale.y.toDouble() * sh.toDouble() * 0.5 * Math.cos(Math.toRadians((-1 * rot).toDouble())) / fontSampling).toFloat()
//        val xtrav = w.toDouble() * Math.cos(Math.toRadians(rot.toDouble())) * sw.toDouble() * scale.x.toDouble() / fontSampling
//        val ytrav = w.toDouble() * Math.sin(Math.toRadians(rot.toDouble())) * sw.toDouble() * scale.y.toDouble() / fontSampling
//        val xjmp = h.toDouble() * Math.sin(Math.toRadians(rot.toDouble())) * sh.toDouble() * scale.x.toDouble() / fontSampling
//        val yjmp = h.toDouble() * Math.cos(Math.toRadians(rot.toDouble())) * sh.toDouble() * scale.y.toDouble() / fontSampling
//
//        var xcur = x.toDouble()
//        var ycur = y.toDouble()
//        var linesdown = 0
//        for (i in 0..str.length - 1) {
//            val c = str[i]
//            when (c) {
//                '\n' -> {
//                    ++linesdown
//                    xcur = x + xjmp * linesdown
//                    ycur = y - yjmp * linesdown
//                }
//                '\t' -> {
//                    xcur += xtrav * tabLength
//                    ycur += ytrav * tabLength
//                }
//                else -> {
//                    val reg = Letters[c]
//                    val xoff = (Font!!.data.getGlyph(c).xoffset / w).toDouble()
//                    val yoff = (Font!!.data.getGlyph(c).yoffset / h).toDouble()
//                    if (reg != null)
//                        drawRegion(reg, x + (xcur + xoff * xtrav - yoff * xjmp).toFloat(), y + (ycur + xoff * ytrav + yoff * yjmp).toFloat(), sw / fontSampling, sh * -1 / fontSampling, rot)
//                    xcur += xtrav
//                    ycur += ytrav
//                }
//            }
//        }
//    }

//    fun getSFX(key: String): SEffect? {
//        return SFX[key]
//    }
//
//    fun getMus(key: String): MTrack? {
//        return Mus[key]
//    }
//
//    fun playSFX(key: String) {
//        SFX[key]?.play()
//    }
//
//    fun setSFXVolume(v: Float) {
//        for (s in SFX.values)
//            s.setVolume(v)
//    }
//
//    fun setMusVolume(v: Float) {
//        for (m in Mus.values)
//            m.setVolume(v)
//    }
//
//    fun killMusic() {
//        for (m in Mus.values) {
//            m.stop()
//            m.dispose()
//        }
//    }

    fun dispose() {
        batch?.dispose()
        batch = null
        mainfb?.dispose()
        mainfb = null
        fxfb?.dispose()
        fxfb = null

        //indexed gfx
        for (t in imagePal.values)
            t.dispose()
        for (s in framePal.values)
            s.dispose()
        for (a in sequencePal.values)
            a.dispose()
        imagePal = HashMap<String, ImagePal>()
        framePal = HashMap<String, FramePal>()
        sequencePal = HashMap<String, SequencePal>()

        //rgb gfx
        for (t in imageRgb.values)
            t.dispose()
        for (s in frameRgb.values)
            s.dispose()
        for (a in sequenceRgb.values)
            a.dispose()
        imageRgb = HashMap<String, ImageRgb>()
        frameRgb = HashMap<String, FrameRgb>()
        sequenceRgb = HashMap<String, SequenceRgb>()

        //bitmap fonts
        for (f in fonts.values)
            f.dispose()
        fonts = HashMap<String, BitmapFont>()


//        for (s in SFX.values)
//            s.dispose()
//        for (m in Mus.values)
//            m.dispose()
    }
}
