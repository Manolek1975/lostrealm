package com.delek.lostrealm.ui.nav.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.TileDAO
import java.lang.reflect.Field


class DrawMap(context: Context) : View(context) {


    private val tileImages = mutableListOf<Bitmap>()

    init {
        val tileList = TileDAO(context).getAllTiles()
        for (tile in tileList) {
            val id = getResId(tile.image, R.drawable::class.java)
            tileImages.add(BitmapFactory.decodeResource(resources,id))
        }
    }


    private val tile = BitmapFactory.decodeResource(resources, R.drawable.t_borderland)
    private val zoomIn = BitmapFactory.decodeResource(resources, R.drawable.zoom_in)
    private val zoomOut = BitmapFactory.decodeResource(resources, R.drawable.zoom_out)
    private val paint = Paint()
    //Set Borderland at middle of Screen
    private val dm: DisplayMetrics = resources.displayMetrics
    private var x = (dm.widthPixels / 2) - tile.width.toFloat()
    private var y = (dm.heightPixels / 2) -tile.height.toFloat()
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
        println("${tileImages[15]}")
        canvas.apply {
            save()
            scale(mScaleFactor, mScaleFactor)
            canvas.drawBitmap(tile, x, y, paint)
            for (id in tileImages) {
                canvas.drawBitmap(id, x + 590, y + 340, paint)
            }

            restore()

        }

        canvas.drawBitmap(
            zoomIn,
            dm.widthPixels - zoomIn.width * 2F,
            dm.heightPixels / 2F - 120, paint
        )

        canvas.drawBitmap(
            zoomOut,
            dm.widthPixels - zoomIn.width * 2F,
            dm.heightPixels / 2F, paint
        )

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mScaleDetector.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                x = (event.x / mScaleFactor - tile.width / 2)
                y = (event.y / mScaleFactor - tile.height / 2)
                performClick()
                return true
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
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
