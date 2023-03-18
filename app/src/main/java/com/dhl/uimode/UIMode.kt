package com.dhl.uimode

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.lifecycle.MutableLiveData
import com.google.android.material.floatingactionbutton.FloatingActionButton


/**
 * @Title: $
 * @Package $
 * @Description: $(用一句话描述)
 * @author dhl
 * @date $
 * @version V1.0
 */

private const val DEFAULT:Int = 0
private const val DAY :Int= 1
private const val NIGHT:Int = 2

data class UIMode(val content:Int, val background:Int,val type:Int)

object Mode{
    val UIModelDefault = UIMode(R.color.colorAccent,R.color.colorPrimary, DEFAULT)
    val UIModeDay = UIMode(Color.BLUE,Color.WHITE,DAY)
    val UIModeNight = UIMode(Color.GRAY,Color.BLACK,NIGHT)
   // val uiModelStr = UIModeString(R.color.black)
}

object AppMode{
    val background = MutableLiveData<ColorStateList>()
    val content = MutableLiveData<ColorStateList>()
    val nightMode = MutableLiveData<Boolean>()

    var currentMode = Mode.UIModelDefault
    init {
        update(Mode.UIModelDefault)
    }
    fun update(uiMode: UIMode) {
        background.value = ColorStateList.valueOf(uiMode.background)
        content.value = ColorStateList.valueOf(uiMode.content)
        nightMode.value = uiMode.type == NIGHT
        currentMode = uiMode

    }
}

@SuppressLint("RestrictedApi")
@BindingMethods(
    BindingMethod(type = ImageView::class, attribute = "tint", method = "setImageTintList")
)
object ModeAdapter {
    @BindingAdapter("background")
    @JvmStatic
    fun adaptBackground(view: View, value: ColorStateList?) {
        //Log.e("TAG","background value = ${value.toString()}")
        view.setBackgroundColor(Color.WHITE)
        view.backgroundTintList = value
    }
    @BindingAdapter("drawableTint")
    @JvmStatic
    fun adaptDrawableTint(view: TextView, value: ColorStateList?) {
        if (view is AppCompatTextView) {
            view.supportCompoundDrawablesTintList = value
        }
    }

    @BindingAdapter("android:progressBackgroundTint")
    @JvmStatic
    fun adaptProgressBackgroundTint(view: SeekBar, value: ColorStateList?) {
        view.progressBackgroundTintList = value
    }
    @BindingAdapter("imageNight")
    @JvmStatic
    fun adaptImageNight(view: ImageView,nightMode: Boolean) {

        if(nightMode){
            val cm = ColorMatrix()
            cm.setSaturation(0f) // 设置饱和度
            val grayColorFilter = ColorMatrixColorFilter(cm)
            view.colorFilter = grayColorFilter // 如果想恢复
        }else{
            view.colorFilter = null
        }

    }


    @SuppressLint("ResourceAsColor")
    @BindingAdapter("fbbackground")
    @JvmStatic
    fun adaptFbBackground(fab: FloatingActionButton, value: ColorStateList?) {
       // fab.backgroundTintList = value
       // fab.setBackgroundColor(Color.WHITE)
        fab.backgroundTintList = value
        Log.e("TAG","fab value = ${value.toString()}")
    }


}
