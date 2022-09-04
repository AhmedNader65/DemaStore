package com.dema.store.data.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.dema.store.utils.Logger
import java.io.IOException
import java.io.InputStream

object JsonReader {
  fun getJson(path: String): String {
    return try {
      val context = ApplicationProvider.getApplicationContext<Context>()
      val jsonStream: InputStream = context.assets.open("networkresponses/$path")
      String(jsonStream.readBytes())
    } catch (exception: IOException) {
      Logger.e(exception, "Error reading network response json asset")
      throw exception
    }
  }
}