package appfree.io.locationtracking.modules.sharepreference

import android.content.Context

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

    var durationStartTracking
    get() = sharedPref.getLong(SharedPreferenceKey.DURATION_TRACING_START.key, 0L)
    set(value) = sharedPref.edit().putLong(SharedPreferenceKey.DURATION_TRACING_START.key, value).apply()

}

enum class SharedPreferenceKey(val key: String) {
    SESSION_ID("session_id"),
    DURATION_TRACING_START("duration_tracking_start")
}