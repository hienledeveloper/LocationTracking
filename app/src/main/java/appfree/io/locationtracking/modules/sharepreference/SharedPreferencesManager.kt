package appfree.io.locationtracking.modules.sharepreference

import android.content.Context
import androidx.core.content.edit

/**
 * Created By Ben on 7/11/20
 */
class SharedPreferencesManager(context: Context) {

    companion object {
        private const val PREFERENCE_KEY_FILE = "track_me_key_file"
    }

    private val sharedPref = context.getSharedPreferences(PREFERENCE_KEY_FILE, Context.MODE_PRIVATE)

    var currentSessionId
    get() = sharedPref.getString(SharedPreferenceKey.SESSION_ID.key, null)
    set(value) = sharedPref.edit().putString(SharedPreferenceKey.SESSION_ID.key, value).apply()

}

enum class SharedPreferenceKey(val key: String) {
    SESSION_ID("session_id")
}