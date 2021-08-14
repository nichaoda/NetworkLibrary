package com.chaoda.network.environment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.chaoda.network.R

/**
 * 网络环境(测试环境、正式环境等)配置的Activity
 */
class NetworkEnvironmentActivity : AppCompatActivity() {

    companion object {
        private const val KEY = "network_settings"
        const val DEBUG_ENVIRONMENT = "1"
        const val RELEASE_ENVIRONMENT = "2"

        /**
         * 每次进入时的网络环境
         */
        private var defaultNetworkEnvironment = DEBUG_ENVIRONMENT


        fun getNetworkEnvironmentFromSP(context: Context): String {
            return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY, DEBUG_ENVIRONMENT)!!
        }

        fun startEnvironmentActivity(context: Context) {
            context.startActivity(Intent(context, NetworkEnvironmentActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_environment)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, EnvironmentSettingsFragment())
            .commit()
        defaultNetworkEnvironment = getNetworkEnvironmentFromSP(this)
    }

    override fun onBackPressed() {
        val currentNetworkEnvironment = getNetworkEnvironmentFromSP(this)
        if (defaultNetworkEnvironment == currentNetworkEnvironment) {
            finish()
        } else {
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }


    class EnvironmentSettingsFragment : PreferenceFragmentCompat(),
        Preference.OnPreferenceChangeListener {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            findPreference<ListPreference>(KEY)!!.onPreferenceChangeListener = this
        }

        override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
            if (defaultNetworkEnvironment != newValue.toString()) {
                Toast.makeText(context, "网络环境改变,后退键退出App后重启", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "网络环境未改变,后退键回到原来界面", Toast.LENGTH_LONG).show()
            }
            return true
        }
    }
}
