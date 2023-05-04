import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val SHARED_PREF_NAME = "worklk_madproject_pref"
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }

    fun setLoggedIn(loggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, loggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}
