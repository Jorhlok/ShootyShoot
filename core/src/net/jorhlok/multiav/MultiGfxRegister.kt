package net.jorhlok.multiav

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import java.util.HashMap

/**
 * Multi-Function Audio-Visual Organization Engine
 * @author Jorhlok
 */
class MultiGfxRegister {
    enum class DrawingState {sprite,shape,off}

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
    private var fontsampling = HashMap<String, Float>()

    //framebuffers
    private var buffers = HashMap<String, PixBuf>()
    private var mybuf = ""


//    private var SFX = HashMap<String, SEffect>()
//    private var Mus = HashMap<String, MTrack>()

    var batch: Batch? = null
    var shape: ShapeRenderer? = null
    var camera = OrthographicCamera()
    var palette = Array<Color>()
    private var status = DrawingState.off

    fun drawingSprite() {
        when (status) {
            DrawingState.off -> {
                batch?.begin()
                status = DrawingState.sprite
            }
            DrawingState.shape -> {
                shape?.flush()
                shape?.end()
                batch?.begin()
                status = DrawingState.sprite
            }
        }
    }

    fun drawingShape() {
        when (status) {
            DrawingState.off -> {
                shape?.begin()
                status = DrawingState.shape
            }
            DrawingState.sprite -> {
                batch?.flush()
                batch?.end()
                shape?.begin()
                status = DrawingState.shape
            }
        }
    }

    fun drawingOff() {
        when (status) {
            DrawingState.sprite -> {
                batch?.flush()
                batch?.end()
                status = DrawingState.off
            }
            DrawingState.shape -> {
                shape?.flush()
                shape?.end()
                status = DrawingState.off
            }
        }
    }

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

    fun newFont(key: String, f: BitmapFont, sampling: Float = 1f) {
        fonts.put(key,f)
        fontsampling.put(key,sampling)
    }

    fun newBuffer(key: String, width: Int, height: Int, camwidth: Float, camheight: Float, format: Pixmap.Format = Pixmap.Format.RGBA8888) {
        buffers[key] = PixBuf(width,height,camwidth,camheight,format)
    }

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
        shape = ShapeRenderer()
        shape!!.setAutoShapeType(true)
        camera.setToOrtho(false, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        batch!!.projectionMatrix = camera.combined
        shape!!.projectionMatrix = camera.combined
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
    }

    fun drawPal(anim: String, indexoffset: Short, statetime: Float, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2? = null) {
        drawingSprite()
        if (batch != null) sequencePal[anim]?.draw(batch!!,palette,indexoffset,statetime,x,y,sw,sh,rot,center)
    }

    fun drawRgb(anim: String, statetime: Float, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2? = null, col: Color = Color(1f,1f,1f,1f)) {
        drawingSprite()
        if (batch != null) sequenceRgb[anim]?.draw(batch!!,statetime,x,y,sw,sh,rot,center, col)
    }

    fun drawString(f: String, str: String, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2 = Vector2(0.5f,0.5f), col: Color = Color(1f,1f,1f,1f)) {
        drawGlyphLayout(f,stringLayout(f,str),x,y,sw,sh,rot,center,col)
    }

    fun drawGlyphLayout(font: String, lay: GlyphLayout, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2 = Vector2(0.5f,0.5f), col: Color = Color(1f,1f,1f,1f)) {
        drawingSprite()
        val f = fonts[font]
        val sampling = fontsampling[font]
        if (f != null && sampling!= null && batch != null) {
            val begin = Vector2(x-lay.width*center.x,y+f.lineHeight+lay.height*center.y)
            val pages = f.regions
            val oldcol = batch!!.color
            batch!!.color = col
            for (run in lay.runs) {
                var xadv = 0f
                for (i in 0..run.glyphs.size-1) {
                    val g = run.glyphs[i]
                    xadv += run.xAdvances[i]
                    val pt = transformPoint(Vector2(run.x+xadv+g.xoffset,run.y+g.yoffset).add(begin),sw*sampling,sh*sampling,rot,Vector2(x,y))
                    batch!!.draw(TextureRegion(pages[g.page],g.srcX,g.srcY,g.width,g.height),
                            pt.x,pt.y,0f,0f,g.width.toFloat(),g.height.toFloat(),sw*sampling,sh*sampling,rot)

                }
            }
            batch!!.color = oldcol
        }
    }

    fun transformPoint(pt: Vector2, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2 = Vector2()): Vector2 {
        return pt.cpy().sub(center).scl(sw,sh).rotate(rot).add(center)
    }

    fun stringLayout(font: String, str: String): GlyphLayout {
        var f = fonts[font]
        if (f != null) return GlyphLayout(f,str)
        return GlyphLayout()
    }

    fun stringLayout(font: String, str: String, col: Color, targetWidth: Float, halign: Int, wrap: Boolean): GlyphLayout {
        var f = fonts[font]
        if (f != null) return GlyphLayout(f,str,col,targetWidth,halign,wrap)
        return GlyphLayout()
    }

    //TODO: put drawing shapes functions here

    fun getBufCam(key: String): OrthographicCamera? {
        val buf = buffers[key]
        if (buf != null) return buf.cam
        return null
    }

    fun clear(r: Float = 0f, g: Float = 0f, b: Float = 0f, a: Float = 1f) {
        Gdx.gl.glClearColor(r,g,b,a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    fun clear(col: Color) {
        clear(col.r,col.g,col.b,col.a)
    }

    fun blank() {
        clear(0f,0f,0f,0f)
    }

    fun startBuffer(key: String) {
        stopBuffer()
        val buf = buffers[key]
        if (buf != null) {
            if (status != DrawingState.off) drawingOff()
            mybuf = key
            buf.begin()
            buf.cam.update()
            batch!!.projectionMatrix = buf.cam.combined
            shape!!.projectionMatrix = buf.cam.combined
        }
    }

    fun stopBuffer() {
        if (mybuf != "") {
            drawingOff()
            buffers[mybuf]?.end()
            camera.update()
            batch!!.projectionMatrix = camera.combined
            shape!!.projectionMatrix = camera.combined
        }
    }

    fun drawBuffer(key:String, x: Float = 0f, y: Float = 0f, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2 = Vector2(), col: Color = Color(1f,1f,1f,1f)) {
        var buf = buffers[key]
        if (buf != null && batch != null) {
            drawingSprite()
            val oldcol = batch!!.color
            batch!!.color = col
            batch!!.draw(TextureRegion(buf.tex),x,y,center.x,center.y,buf.tex.regionWidth.toFloat(),buf.tex.regionHeight.toFloat(),sw,sh,rot)
            batch!!.color = oldcol
        }
    }

    fun drawBuffer(key:String, srcx: Int, srcy: Int, srcw: Int, srch: Int, x: Float, y: Float, sw: Float = 1f, sh: Float = 1f, rot: Float = 0f, center: Vector2 = Vector2(), col: Color = Color(1f,1f,1f,1f)) {
        var buf = buffers[key]
        if (buf != null && batch != null) {
            drawingSprite()
            val oldcol = batch!!.color
            batch!!.color = col
            batch!!.draw(TextureRegion(buf.tex,srcx,srcy,srcw,srch),x,y,center.x,center.y,buf.tex.regionWidth.toFloat(),buf.tex.regionHeight.toFloat(),sw,sh,rot)
            batch!!.color = oldcol
        }
    }



    fun dispose() {
        drawingOff()
        batch?.dispose()
        batch = null

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

        //framebuffers
        for (b in buffers.values)
            b.dispose()
        buffers = HashMap<String, PixBuf>()
    }
}


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

//        for (s in SFX.values)
//            s.dispose()
//        for (m in Mus.values)
//            m.dispose()
