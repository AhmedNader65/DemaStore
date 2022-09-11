package com.dema.store.common.utils

import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dema.store.R
import com.dema.store.home.presentation.model.UIProductSlider
import com.dema.store.utils.Logger
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@BindingAdapter("app:setupImage")
fun ImageView.setImage(url: String) {
    Glide.with(this.context)
        .load(url.ifEmpty { null })
        .error(R.drawable.demo_product)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

@BindingAdapter("app:setupPrice")
fun TextView.setPriceWithCurrency(price: String) {
    text = price + resources.getText(R.string.currency)
}

@BindingAdapter("app:settingSelected")
fun MaterialCardView.setSettingSelected(uiItem: UIProductSlider) {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(
        com.google.android.material.R.attr.colorOnPrimary,
        typedValue,
        true
    )
    val color = typedValue.data
    if (uiItem.isSelected) {
        setCardBackgroundColor(ActivityCompat.getColor(context, R.color.MyrtleGreen))
    } else {
        setCardBackgroundColor(color)
    }
}

@BindingAdapter("app:settingTint")
fun ImageView.settingTint(uiItem: UIProductSlider) {

    if (uiItem.isSelected) {
        setColorFilter(
            ContextCompat.getColor(context, R.color.white),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
    } else {
        setColorFilter(ContextCompat.getColor(context, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}

inline fun CoroutineScope.createExceptionHandler(
    message: String,
    crossinline action: (throwable: Throwable) -> Unit
) = CoroutineExceptionHandler { _, throwable ->
    Logger.e(throwable, message)
    throwable.printStackTrace()

    /**
     * A [CoroutineExceptionHandler] can be called from any thread. So, if [action] is supposed to
     * run in the main thread, you need to be careful and call this function on the a scope that
     * runs in the main thread, such as a [viewModelScope].
     */
    launch {
        action(throwable)
    }
}
