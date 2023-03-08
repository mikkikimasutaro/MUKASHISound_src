package jp.mikkiki.mukashisound.automotive


import android.content.Context
import android.util.Log
import androidx.core.content.edit
import jp.mikkiki.mukashisound.media.MusicService
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val LOGOUT = "jp.mikkiki.mukashisound.automotive.COMMAND.LOGOUT"

class AutomotiveMusicService : MusicService() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
    }

    private fun onLogin(email: String, password: String): Boolean {
        Log.i(TAG, "User logged in: $email")
        getSharedPreferences(AutomotiveMusicService::class.java.name, Context.MODE_PRIVATE).edit {
            putString(USER_TOKEN, "$email:${password.hashCode()}")
        }
        return true
    }

    private fun onLogout(): Boolean {
        Log.i(TAG, "User logged out")
        getSharedPreferences(AutomotiveMusicService::class.java.name, Context.MODE_PRIVATE).edit {
            remove(USER_TOKEN)
        }
        return false
    }

}

private const val TAG = "AutomotiveMusicService"
private const val ERROR_RESOLUTION_ACTION_LABEL =
    "android.media.extras.ERROR_RESOLUTION_ACTION_LABEL"
private const val ERROR_RESOLUTION_ACTION_INTENT =
    "android.media.extras.ERROR_RESOLUTION_ACTION_INTENT"

private const val USER_TOKEN = "jp.mikkiki.mukashisound.automotive.PREFS.USER_TOKEN"