package de.lobianco.saftssh.mosh

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*

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