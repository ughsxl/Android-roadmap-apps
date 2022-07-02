package android.bignerdranch.roadmapapps

import android.bignerdranch.roadmapapps.databinding.ActivityHandlerBinding
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import kotlin.random.Random

class SecondLevelHandlerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHandlerBinding

    private val token = Any()

    private val handler = Handler(Looper.getMainLooper()) {
        Log.d(TAG, "Processing Message: ${it.what}")

        when(it.what) {
            MSG_TOGGLE_BUTTON -> toggleButtonState()
            MSG_NEXT_RANDOM_COLOR -> nextRandomColor()
            MSG_SHOW_TOAST -> showToast()
        }
        return@Handler true
   }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.forEach {
            if (it is Button) it.setOnClickListener(universalButtonListener)
        }
    }



    private fun toggleButtonState() { binding.testButton.isEnabled = !binding.testButton.isEnabled }
    private fun showToast() = Toast.makeText(this, TAG, Toast.LENGTH_SHORT).show()

    private fun nextRandomColor() {
        val randomColor = -Random.nextInt(255 * 255 * 255)
        binding.colorView.setBackgroundColor(randomColor)
    }



    private val universalButtonListener = View.OnClickListener {
        Thread {
            when(it.id) {

                R.id.enableDisableButton -> {   // simple way
                    val message = handler.obtainMessage(MSG_TOGGLE_BUTTON)
                    handler.sendMessage(message)
                }
                R.id.randomColorButton -> {     // second way
                    val message = Message()
                    message.what = MSG_NEXT_RANDOM_COLOR
                    handler.sendMessage(message)
                }

                R.id.enableDisableDelayedButton -> {    // third way
                    val message = Message.obtain(handler, MSG_TOGGLE_BUTTON)
                    handler.sendMessageDelayed(message, DELAY)
                }
                R.id.randomColorDelayedButton -> {      // way with callback (handler won't handle this message)
                    val message = Message.obtain(handler) {
                        Log.d(TAG, "Callback: Random color is called via callback")
                        nextRandomColor()
                    }
                    handler.sendMessageDelayed(message, DELAY)
                }

                R.id.randomColorTokenDelayedButton -> {     // way with token
                    val message = handler.obtainMessage(MSG_NEXT_RANDOM_COLOR)
                    message.obj = token
                    handler.sendMessageDelayed(message, DELAY)
                }
                R.id.showToastButton -> {
                    val message = handler.obtainMessage(MSG_SHOW_TOAST)
                    message.obj = token
                    handler.sendMessageDelayed(message, DELAY)
                }

                R.id.cancelButton -> handler.removeCallbacksAndMessages(token) // cancel messages to handle
            }
        }.start()
    }



    companion object {
        @JvmStatic val TAG: String = SecondLevelHandlerActivity::class.java.simpleName

        const val MSG_TOGGLE_BUTTON = 89748923
        const val MSG_NEXT_RANDOM_COLOR = 249285034
        const val MSG_SHOW_TOAST = 32990220

        const val DELAY = 2000L
    }
}