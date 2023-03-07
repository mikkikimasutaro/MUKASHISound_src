package jp.mikkiki.mukashisound.automotive

import android.accounts.AccountManager
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.core.content.edit
import jp.mikkiki.mukashisound.media.MusicService
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import kotlinx.coroutines.ExperimentalCoroutinesApi

/** UAMP specific command for logging into the service. */
const val LOGIN = "jp.mikkiki.mukashisound.automotive.COMMAND.LOGIN"

/** UAMP specific command for logging out of the service. */
const val LOGOUT = "jp.mikkiki.mukashisound.automotive.COMMAND.LOGOUT"

const val LOGIN_EMAIL = "jp.mikkiki.mukashisound.automotive.ARGS.LOGIN_EMAIL"
const val LOGIN_PASSWORD = "jp.mikkiki.mukashisound.automotive.ARGS.LOGIN_PASSWORD"

typealias CommandHandler = (parameters: Bundle, callback: ResultReceiver?) -> Boolean

/**
 * Android Automotive specific extensions for [MusicService].
 *
 * UAMP for Android Automotive OS requires the user to login in order to demonstrate
 * how authentication works on the system. If this doesn't apply to your application,
 * this class can be skipped in favor of its parent, [MusicService].
 *
 * If you'd like to support authentication, but not prevent using the system,
 * comment out the calls to [requireLogin].
 */
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