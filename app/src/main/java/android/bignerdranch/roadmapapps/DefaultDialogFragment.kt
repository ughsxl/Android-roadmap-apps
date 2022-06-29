package android.bignerdranch.roadmapapps

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment

class DefaultDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DialogInterface.OnClickListener { _, which ->
            parentFragmentManager.setFragmentResult(REQUEST_KEY, bundleOf(KEY_RESPONSE to which))
        }

        return AlertDialog.Builder(requireContext())
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle("Uninstall Android")
            .setMessage("Would you like to uninstall Android?")
            .setPositiveButton("Delete", listener)
            .setNegativeButton("Cancel", listener)
            .setNeutralButton("Ignore", listener)
            .create()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Toast.makeText(requireContext(), "Dialog cancelled", Toast.LENGTH_SHORT).show()
    }

    companion object {
        @JvmStatic val TAG = DefaultDialogFragment::class.java.simpleName

        @JvmStatic val REQUEST_KEY = "$TAG: default_request_key"
        const val KEY_RESPONSE = "response"
    }
}