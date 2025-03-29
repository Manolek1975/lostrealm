package com.delek.lostrealm.ui.nav.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.MapDAO
import com.delek.lostrealm.database.dao.TileDAO
import java.lang.reflect.Field


class DrawMap(context: Context) : View(context) {

    private val tileImages = mutableListOf<Bitmap>()
    private val tileList = TileDAO(context).getAllTiles()
    private val mapList = MapDAO(context).getAllMap()

    init {
        for (map in mapList) {
            val id = getResId(tileList[map.tileId].image, R.drawable::class.java)
            val bm = BitmapFactory.decodeResource(resources, id)
            tileImages.add(bm)
            //tileImages.add(bm.rotate(map.rotate.toFloat()))
        }
    }

    private val paint = Paint()
    private val dm: DisplayMetrics = resources.displayMetrics
    private var x = (dm.widthPixels / 2f) - tileImages[0].width / 2
    private var y = (dm.heightPixels / 2f) - tileImages[0].height / 2
    private var mScaleFactor = 0.5f

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            mScaleFactor *= detector.scaleFactor
            //Max & Min map scale
            mScaleFactor = 0.4f.coerceAtLeast(mScaleFactor.coerceAtMost(1.2f))
            invalidate()
            return true
        }
    }

    private val mScaleDetector = ScaleGestureDetector(context, scaleListener)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //canvas.translate(cx, cy)
        canvas.apply {
            save()
            scale(mScaleFactor, mScaleFactor)
            for (map in mapList) {
                canvas.drawCircle(x + map.posX.toFloat(), y + map.posY.toFloat(), 10f, paint)
                canvas.drawBitmap(tileImages[map.id - 1],x + map.posX.toFloat(),y + map.posY.toFloat(), paint)
                //canvas.rotate(map.rotate.toFloat())

            }
            restore()
        }
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mScaleDetector.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
/*                x = (event.x / mScaleFactor - tileImages[0].width / 2)
                y = (event.y / mScaleFactor - tileImages[0].height / 2)*/
                performClick()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                x = (event.x / mScaleFactor - tileImages[0].width / 2)
                y = (event.y / mScaleFactor - tileImages[0].height / 2)
                invalidate()
                return true
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        println("X: $x, --- Y: $y")
        return true
    }

    private fun Bitmap.rotate(degrees: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(degrees) }
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

    private fun getResId(resName: String?, c: Class<*>): Int {
        try {
            val idField: Field = c.getDeclaredField(resName!!)
            return idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }
    }



    /*           MotionEvent.ACTION_DOWN -> {
                    x = (event.x - tile.width / 2) / mScaleFactor
                    y = (event.y - tile.height / 2) / mScaleFactor
                    x = (event.x  / mScaleFactor - tile.width / 2)
                    y = (event.y  / mScaleFactor - tile.height / 2)
                    if (event.x > dm.widthPixels - zoomIn.width * 2 &&
                        event.y > dm.heightPixels/2 - zoomIn.height/2 &&
                        event.y < dm.heightPixels/2 + zoomIn.height/2)
                        if (mScaleFactor in 0.5..1.3) {
                            mScaleFactor -= 0.1f
                        }
                    if (event.x > dm.widthPixels - zoomIn.width * 2 &&
                        event.y > dm.heightPixels/2 - zoomIn.height/2 - 120 &&
                        event.y < dm.heightPixels/2 + zoomIn.height/2 - 120)
                        if (mScaleFactor in 0.4..1.2) {
                            mScaleFactor += 0.1f
                        }
                    return true
                }*/

    /*            MotionEvent.ACTION_UP -> {
                    performClick()
                    return true
                }*/

    /*            MotionEvent.ACTION_MOVE -> {
                    x = event.x  / mScaleFactor - tile.width / 2
                    y = event.y  / mScaleFactor - tile.height / 2
                    return true
                }*/

    /*            MotionEvent.ACTION_POINTER_DOWN -> {
                    mScaleDetector.onTouchEvent(event)
                    return true
                }*/

    /*            MotionEvent.ACTION_POINTER_UP -> {
                    mScaleDetector.onTouchEvent(event)
                    return true
                }*/
}
