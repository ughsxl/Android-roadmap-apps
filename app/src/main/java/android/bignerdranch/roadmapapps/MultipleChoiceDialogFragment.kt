package android.bignerdranch.roadmapapps

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class MultipleChoiceDialogFragment: DialogFragment() {

    private val color: Int
        get() = requireArguments().getInt(ARG_COLOR)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val colorItems = arrayOf("Red", "Green", "Blue")
        val colorComponents = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color)
        )

        val checkboxes = colorComponents.map {
            it > 0 && savedInstanceState == null
        }.toBooleanArray()


        return AlertDialog.Builder(requireContext())
            .setTitle("Rgb color setup")
            .setMultiChoiceItems(colorItems, checkboxes) { dialog, _, _ ->
                val checkedPositions = (dialog as AlertDialog).listView.checkedItemPositions
                val color = Color.rgb(
                    booleanToColorComponent(checkedPositions[0]),
                    booleanToColorComponent(checkedPositions[1]),
                    booleanToColorComponent(checkedPositions[2])
                )
                if (color != Color.WHITE)
                    parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_COLOR_RESPONSE to color))
                else parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_COLOR_RESPONSE to Color.BLACK))
            }
            .setPositiveButton("Close", null)
            .create()
    }

    private fun booleanToColorComponent(value: Boolean) = if (value) 255 else 0



    companion object {
        @JvmStatic val TAG = MultipleChoiceDialogFragment::class.java.simpleName

        @JvmStatic val REQUEST_KEY = "$TAG: default_request_key"

        const val ARG_COLOR = "arg_color"
        const val KEY_COLOR_RESPONSE = "key_color_response"


        fun show(manager: FragmentManager, color: Int) {
            val dialogFragment = MultipleChoiceDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_COLOR to color)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, result ->
                listener.invoke(result.getInt(KEY_COLOR_RESPONSE))
            }
        }
    }
}