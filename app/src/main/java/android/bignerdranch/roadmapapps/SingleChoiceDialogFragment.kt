package android.bignerdranch.roadmapapps

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner

class SingleChoiceDialogFragment: DialogFragment() {

    private val volume: Int
        get() = requireArguments().getInt(ARG_VOLUME)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val volumeItems = AvailableVolumeValues.createVolumeValues(volume)
        val volumeTextItems = volumeItems.values
            .map { "Volume: $it%" }
            .toTypedArray()

        return AlertDialog.Builder(requireContext())
            .setTitle("Volume setup")
            .setSingleChoiceItems(volumeTextItems, volumeItems.currentValue) { _, which ->
                    val chosenVolume = volumeItems.values[which]
                    parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(
                        KEY_VOLUME_RESPONSE to chosenVolume))
                dismiss()
            }
            .create()

    }



    companion object {
        @JvmStatic val TAG = SingleChoiceDialogFragment::class.java.simpleName

        @JvmStatic val REQUEST_KEY = "$TAG: default_request_key"

        const val ARG_VOLUME = "arg_volume"
        const val KEY_VOLUME_RESPONSE = "key_volume_response"


        fun show(manager: FragmentManager, volume: Int) {
            val dialogFragment = SingleChoiceDialogFragment()
            dialogFragment.arguments = bundleOf(ARG_VOLUME to volume)
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, listener: (Int) -> Unit) {
            manager.setFragmentResultListener(REQUEST_KEY, lifecycleOwner) { _, result ->
                listener.invoke(result.getInt(KEY_VOLUME_RESPONSE))
            }
        }
    }

}