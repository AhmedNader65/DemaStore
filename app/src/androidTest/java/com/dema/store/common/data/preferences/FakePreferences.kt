package com.raywenderlich.android.petsave.common.data.preferences

import com.dema.store.common.data.preferences.Preferences
import com.dema.store.common.data.preferences.PreferencesConstants

class FakePreferences : Preferences {
  private val preferences = mutableMapOf<String, Any>()

  override fun putToken(token: String) {
    preferences[PreferencesConstants.KEY_TOKEN] = token
  }

  override fun putLogged(logged: Boolean) {
    preferences[PreferencesConstants.KEY_LOGGED] = logged
  }

  override fun getToken(): String {
    return preferences[PreferencesConstants.KEY_TOKEN] as String
  }

  override fun getLogged(): Boolean {
    return preferences[PreferencesConstants.KEY_LOGGED] as Boolean
  }

  override fun deleteTokenInfo() {
    preferences.clear()
  }

}