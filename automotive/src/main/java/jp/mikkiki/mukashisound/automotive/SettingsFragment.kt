package jp.mikkiki.mukashisound.automotive

import android.app.Application
import android.content.ComponentName
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import jp.mikkiki.mukashisound.common.MusicServiceConnection

/**
 * Preference fragment hosted by [SettingsActivity]. Handles events to various preference changes.
 */
class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var viewModel: SettingsFragmentViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        viewModel = ViewModelProvider(this)
            .get(SettingsFragmentViewModel::class.java)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        return when (preference?.key) {
            "logout" -> {
                viewModel.logout()
                requireActivity().finish()
                true
            }
            else -> {
                super.onPreferenceTreeClick(preference)
            }
        }
    }
}

/**
 * Basic ViewModel for [SettingsFragment].
 */
class SettingsFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val applicationContext = application.applicationContext
    private val musicServiceConnection = MusicServiceConnection(
        applicationContext,
        ComponentName(applicationContext, AutomotiveMusicService::class.java)
    )

    fun logout() {
        // Logout is fire and forget.
        musicServiceConnection.sendCommand(LOGOUT, null)
    }
}
