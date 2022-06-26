package android.bignerdranch.roadmapapps

import android.bignerdranch.roadmapapps.databinding.SimpleAdapterListViewBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AlertDialog

class SimpleAdapterSample: AppCompatActivity() {
    private lateinit var binding: SimpleAdapterListViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SimpleAdapterListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListView()
    }

    private fun setupListView() {
        val data = (1..100).map {
            mapOf(
                KEY_TITLE to "Item №$it",
                KEY_DESCRIPTION to "Description: this is number $it"
            )
        }

        val adapter = SimpleAdapter(this, data,
            R.layout.custom_list_item,
            arrayOf(KEY_TITLE, KEY_DESCRIPTION),
            intArrayOf(R.id.title, R.id.description)
        )
        binding.listView.adapter = adapter

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val title = data[position][KEY_TITLE]
            val description = data[position][KEY_DESCRIPTION]

            val alertDialog = AlertDialog.Builder(this).apply {
                setTitle(title)
                setMessage(description)
                setPositiveButton("OK") { _, _ -> }
                create()
            }.show()
        }
    }


    companion object {
        const val KEY_TITLE = "key_title"
        const val KEY_DESCRIPTION = "key_description"
    }
}