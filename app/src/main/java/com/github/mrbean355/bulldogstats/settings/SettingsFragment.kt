package com.github.mrbean355.bulldogstats.settings

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.mrbean355.bulldogstats.R
import com.github.mrbean355.bulldogstats.showError
import com.github.mrbean355.bulldogstats.showSuccess
import com.google.android.material.snackbar.Snackbar

class SettingsFragment : PreferenceFragmentCompat() {
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        findPreference<Preference>(getString(R.string.key_pref_shut_down))?.setOnPreferenceClickListener {
            showConfirmDialog()
            true
        }
    }

    private fun showConfirmDialog() {
        AlertDialog.Builder(requireContext())
                .setTitle(R.string.title_confirm)
                .setMessage(R.string.message_confirm_shut_down)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(R.string.action_shut_down) { _, _ ->
                    viewModel.onShutDownClicked {
                        showSnackBar(success = it)
                    }
                }
                .show()
    }

    private fun showSnackBar(success: Boolean) {
        val view = view ?: return
        if (success) {
            Snackbar.make(view, R.string.message_shut_down_success, Snackbar.LENGTH_LONG).showSuccess()
        } else {
            Snackbar.make(view, R.string.message_shut_down_error, Snackbar.LENGTH_LONG).showError()
        }
    }
}