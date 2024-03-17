package com.vn000.android.passnotes.presentation

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.vn000.android.passnotes.R
import com.vn000.android.passnotes.presentation.listpass.ListPassFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, ListPassFragment.newInstance())
                .commit()
            checkBiometricSupport()
        }
    }

    private fun checkBiometricSupport(): Boolean {
        val keyquardManager = getSystemService(Context.KEYGUARD_SERVICE)
                as KeyguardManager
        if (!keyquardManager.isDeviceSecure) {
            notifyUser("Футентификация по отпечатку авльца отключена в настройках")
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            notifyUser("Разрешение на аутентификацию по отпечатку пальца не включено")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    private fun notifyUser(massage: String) {
        val toast = Toast.makeText(this, massage, Toast.LENGTH_SHORT)
        toast.show()
    }

}


