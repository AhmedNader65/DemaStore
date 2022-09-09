package com.dema.store.common.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dema.store.R
import com.dema.store.utils.Logger
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
