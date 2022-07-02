package android.bignerdranch.roadmapapps

import android.bignerdranch.roadmapapps.databinding.ActivityHandlerBinding
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import kotlin.random.Random


class FirstLevelHandlerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHandlerBinding

    private val handler = Handler(Looper.getMainLooper())
   private val token = Any()


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
            when (it.id) {

                R.id.enableDisableButton -> handler.post { toggleButtonState() }
                R.id.randomColorButton -> handler.post { nextRandomColor() }

                R.id.enableDisableDelayedButton -> handler.postDelayed({ toggleButtonState() }, DELAY)
                R.id.randomColorDelayedButton -> handler.postDelayed({ nextRandomColor() }, DELAY)

                R.id.randomColorTokenDelayedButton -> handler.postDelayed({ nextRandomColor() }, token, DELAY)
                R.id.showToastButton -> handler.postDelayed({ showToast() }, token, DELAY)

                R.id.cancelButton -> handler.removeCallbacksAndMessages(token)

            }
        }.start()
    }



    companion object {
        @JvmStatic val TAG: String = FirstLevelHandlerActivity::class.java.simpleName

        private const val DELAY = 2000L
    }
}