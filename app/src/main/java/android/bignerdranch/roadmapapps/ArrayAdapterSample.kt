package android.bignerdranch.roadmapapps

import android.bignerdranch.roadmapapps.databinding.AdapterListViewBinding
import android.bignerdranch.roadmapapps.databinding.DialogAddCharacterBinding
import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class ArrayAdapterSample: AppCompatActivity() {

    private lateinit var binding: AdapterListViewBinding
    private lateinit var adapter: ArrayAdapter<Character>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AdapterListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListViewWithArrayAdapter()
        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this).apply {
            title = "Create character"
            setView(dialogBinding.root)
            setPositiveButton("Add") { _,  _ ->
                val name =  dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) createCharacter(name)
            }
            create()
        }.show()

    }

    private fun createCharacter(name: String) {
        val character = Character(
            id = UUID.randomUUID().toString(),
            name = name
        )
        adapter.add(character)
    }

    private fun setupListViewWithArrayAdapter() {
        val data = mutableListOf(
            Character(id = UUID.randomUUID().toString(), name = "Reptile"),
            Character(id = UUID.randomUUID().toString(), name = "Subzero"),
            Character(id = UUID.randomUUID().toString(), name = "Scorpion"),
            Character(id = UUID.randomUUID().toString(), name = "Raider"),
            Character(id = UUID.randomUUID().toString(), name = "Smoker")
        )

        adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1,
            android.R.id.text1, data
        )

        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            adapter.getItem(position)?.let { deleteCharacter(it) }
        }
    }


    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { _, which ->  
            if (which == DialogInterface.BUTTON_POSITIVE)
                adapter.remove(character)
        }
        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle("Delete character")
            setMessage("Are you sure you wanna delete the character $character?")
            setPositiveButton("Delete", listener)
            setNegativeButton("Cancel", listener)
            create()
        }.show()
    }

    data class Character(val id: String, val name: String) {
        override fun toString(): String {
            return name
        }
    }
}