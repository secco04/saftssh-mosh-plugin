package de.lobianco.saftssh.mosh

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.browser.customtabs.CustomTabsIntent

class PluginInfoActivity : Activity() {

    private val handler = Handler(Looper.getMainLooper())
    private val startTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(buildRoot())
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    private fun buildRoot(): View {
        val root = FrameLayout(this).apply { setBackgroundColor(C_BG) }
        root.addView(buildInfoPanel(), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        return root
    }

    private fun buildInfoPanel(): View {
        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20.dp, 40.dp, 20.dp, 40.dp)
        }

        content.addView(TextView(this).apply {
            text = "🖥️  Mosh Plugin"
            textSize = 26f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        })

        content.addView(space(20))

        content.addView(TextView(this).apply {
            text = "This plugin provides the mosh-client binary (GPLv3) to the LobiShell Secure Shell client app.\n\n" +
                    "You don't need to open this app manually — just install it and LobiShell will detect it automatically."
            textSize = 14f
            setTextColor(Color.parseColor("#C9D1D9"))
        })

        content.addView(space(40))

        content.addView(TextView(this).apply {
            text = "Plugin v1.0.11 • GPLv3 • mosh.org"
            textSize = 11f
            setTextColor(Color.parseColor("#484F58"))
            gravity = Gravity.CENTER
        })

        content.addView(space(20))

        // Source Code Section
        content.addView(TextView(this).apply {
            text = "Source Code"
            textSize = 16f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        })

        content.addView(space(10))

        content.addView(TextView(this).apply {
            text = "This plugin is open source and available at:"
            textSize = 12f
            setTextColor(Color.parseColor("#C9D1D9"))
        })

        content.addView(space(5))

        val sourceLink = TextView(this).apply {
            text = "https://github.com/secco04/saftssh-mosh-plugin"
            textSize = 12f
            setTextColor(Color.parseColor("#6CB6FF"))
            setOnClickListener {
                val uri = Uri.parse("https://github.com/secco04/saftssh-mosh-plugin")
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(this@PluginInfoActivity, uri)
            }
            isClickable = true
            isFocusable = true
        }
        content.addView(sourceLink)

        content.addView(space(20))

        // GPLv3 License Section
        content.addView(TextView(this).apply {
            text = "License Information"
            textSize = 16f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        })

        content.addView(space(10))

        content.addView(TextView(this).apply {
            text = "This program is free software: you can redistribute it and/or modify " +
                    "it under the terms of the GNU General Public License as published by " +
                    "the Free Software Foundation, either version 3 of the License, or " +
                    "(at your option) any later version.\n\n" +
                    "This program is distributed in the hope that it will be useful, " +
                    "but WITHOUT ANY WARRANTY; without even the implied warranty of " +
                    "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the " +
                    "GNU General Public License for more details.\n\n" +
                    "You should have received a copy of the GNU General Public License " +
                    "along with this program.  If not, see " +
                    "<https://www.gnu.org/licenses/>."
            textSize = 12f
            setTextColor(Color.parseColor("#C9D1D9"))
        })

        content.addView(space(10))

        content.addView(TextView(this).apply {
            text = "Full License Text:"
            textSize = 14f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        })

        content.addView(space(5))

        val licenseLink = TextView(this).apply {
            text = "https://raw.githubusercontent.com/mobile-shell/mosh/refs/heads/master/COPYING"
            textSize = 12f
            setTextColor(Color.parseColor("#6CB6FF"))
            setOnClickListener {
                val uri = Uri.parse("https://raw.githubusercontent.com/mobile-shell/mosh/refs/heads/master/COPYING")
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(this@PluginInfoActivity, uri)
            }
            isClickable = true
            isFocusable = true
        }
        content.addView(licenseLink)

        scroll.addView(content)
        return scroll
    }

    private val timerRunnable = object : Runnable {
        override fun run() {
            handler.postDelayed(this, 1000)
        }
    }

    private fun startTimer() {
        handler.post(timerRunnable)
    }

    private fun space(dp: Int) = Space(this).apply { minimumHeight = dp.dp }

    private fun roundRect(fill: Int, radius: Int = 12) =
        GradientDrawable().apply {
            setColor(fill)
            cornerRadius = radius.dp.toFloat()
        }

    private val Int.dp get() =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), resources.displayMetrics).toInt()

    private val C_BG = Color.parseColor("#0D1117")
}
