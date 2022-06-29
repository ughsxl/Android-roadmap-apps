package android.bignerdranch.roadmapapps

import android.bignerdranch.roadmapapps.databinding.DialogsLayoutBinding
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import kotlin.properties.Delegates.notNull

class DialogFragmentsActivity: AppCompatActivity() {
    private lateinit var binding: DialogsLayoutBinding

    private var volume by notNull<Int>()
    private var color by notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogsLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.defaultButton.setOnClickListener { showDefaultDialog() }
        binding.withSingleChoiceButton.setOnClickListener { showSingleChoiceDialog() }
        binding.singleChoiceWithConfirmationButton.setOnClickListener { showSingleChoiceWithConfirmationDialog() }
        binding.withMultipleChoiceButton.setOnClickListener { showMultipleChoiceDialogFragment() }


        volume = savedInstanceState?.getInt(KEY_VOLUME) ?: 50
        color = savedInstanceState?.getInt(KEY_COLOR) ?: Color.RED
        updateUi()


        setupDefaultDialogListener()
        setupSingleChoiceDialogListener()
        setupSingleChoiceWithConfirmationDialogListener()
        setupMultipleChoiceDialogFragmentListener()
    }


    private fun showDefaultDialog() {
        val dialogFragment = DefaultDialogFragment()
        dialogFragment.show(supportFragmentManager, DefaultDialogFragment.TAG)
    }

    private fun setupDefaultDialogListener() {
        supportFragmentManager.setFragmentResultListener(DefaultDialogFragment.REQUEST_KEY, this) { _, result ->
            val which = result.getInt(DefaultDialogFragment.KEY_RESPONSE)

            when(which) {
                DialogInterface.BUTTON_POSITIVE -> Toast.makeText(this, "Android will be uninstalled soon :)", Toast.LENGTH_SHORT).show()
                DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(this, "No :(", Toast.LENGTH_SHORT).show()
                DialogInterface.BUTTON_NEUTRAL -> Toast.makeText(this, "Ignored..", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun showSingleChoiceDialog() {
        SingleChoiceDialogFragment.show(supportFragmentManager, volume)
    }

    private fun setupSingleChoiceDialogListener() {
        SingleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.volume = it
            updateUi()
        }
    }


    private fun showSingleChoiceWithConfirmationDialog() {
        SingleChoiceWithConfirmationDialogFragment.show(supportFragmentManager, volume)
    }

    private fun setupSingleChoiceWithConfirmationDialogListener() {
        SingleChoiceWithConfirmationDialogFragment.setupListener(supportFragmentManager, this) {
            this.volume = it
            updateUi()
        }
    }


    private fun showMultipleChoiceDialogFragment() {
        MultipleChoiceDialogFragment.show(supportFragmentManager, color)
    }

    private fun setupMultipleChoiceDialogFragmentListener() {
        MultipleChoiceDialogFragment.setupListener(supportFragmentManager, this) {
            this.color = it
            updateUi()
        }
    }


    private fun updateUi() {
        binding.currentVolumeTextView.text = getString(R.string.volume_state, volume)
        binding.colorView.background = color.toDrawable()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_VOLUME, volume)
        outState.putInt(KEY_COLOR, color)
    }


    companion object {
        const val KEY_VOLUME = "key_volume"
        const val KEY_COLOR = "key_color"
    }
}
