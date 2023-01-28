package com.orbitalsonic.scientificcalculator

import android.app.Activity
import android.hardware.display.DisplayManager
import android.view.Display
import androidx.core.content.getSystemService

object ScreenUtils {

    private const val defaultScreenWidth = 350
    private const val defaultScreenHeight = 700

   /*
    binding.myLayout.requestLayout()
    binding.myLayout.layoutParams.width =  (activity.getScreenWidth() * .90).toInt()
    binding.myLayout.layoutParams.height =  (activity.getScreenHeight() * .90).toInt()
    */

    fun Activity?.getScreenWidth(): Int {
        this?.let {
            try {
                val defaultDisplay = it.getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
                defaultDisplay?.let { display ->
                    val displayContext = it.createDisplayContext(display)
                    return displayContext.resources.displayMetrics.widthPixels
                }
            } catch (ex: Exception) {}
        }
        return defaultScreenWidth
    }

    fun Activity?.getScreenHeight(): Int {
        this?.let {
            try {
                val defaultDisplay = it.getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)
                defaultDisplay?.let { display ->
                    val displayContext = it.createDisplayContext(display)
                    return displayContext.resources.displayMetrics.heightPixels
                }
            } catch (ex: Exception) {}
        }
        return defaultScreenHeight
    }
}