package com.merxury.blocker.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.merxury.blocker.BlockerApplication
import com.merxury.blocker.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportFragmentManager.beginTransaction()
                .replace(R.id.root, HomeFragment())
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //TODO reserved code
    private fun showReportScreen() {
        val logFile = filesDir.resolve(BlockerApplication.LOG_FILENAME)
        val emailIntent = Intent(Intent.ACTION_SEND)
                .setType("vnd.android.cursor.dir/email")
                .putExtra(Intent.EXTRA_EMAIL, arrayOf("mercuryleee@gmail.com"))
                .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.report_subject_template))
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.report_content_template))
        if (logFile.exists()) {
            val logUri = FileProvider.getUriForFile(
                    this,
                    "com.merxury.blocker.provider", //(use your app signature + ".provider" )
                    logFile);
            emailIntent.putExtra(Intent.EXTRA_STREAM, logUri)
        }
        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)))
    }
}
