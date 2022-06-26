package android.bignerdranch.roadmapapps.baseadapter

import android.bignerdranch.roadmapapps.databinding.AdapterListViewBinding
import android.bignerdranch.roadmapapps.databinding.DialogAddCharacterBinding
import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.BaseAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class BaseAdapterSample: AppCompatActivity() {
    private lateinit var binding: AdapterListViewBinding

    private val data = mutableListOf(
        Character(id = 0, name = "Reptile"),
        Character(id = 1, name = "Subzero"),
        Character(id = 2, name = "Scorpion"),
        Character(id = 3, name = "Raiden"),
        Character(id = 4, name = "Smoker")
    )
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AdapterListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupList()

        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupList() {
        adapter = CharacterAdapter(characters = data) {
            deleteCharacter(it)
        }
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            showCharacterInfo(adapter.getItem(position))
        }
    }

    private fun onAddPressed() {
        val dialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle("Create character")
            setView(dialogBinding.root)
            setPositiveButton("Add") { _, _ ->
                val name = dialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) createCharacter(name)
            }
            create()
        }
        alertDialog.show()
    }

    private fun createCharacter(name: String) {
        val character = Character(
            id = Random.nextLong(),
            name = name
        )

        data.add(character)
        adapter.notifyDataSetChanged()
    }

    private fun showCharacterInfo(character: Character) {
        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle(character.name)
            setMessage("Name: ${character.name}\nid: ${character.id}")
            setPositiveButton("Ok") { _, _ ->}
            create()
        }
        alertDialog.show()
    }

    private fun deleteCharacter(character: Character) {
        val listener = DialogInterface.OnClickListener { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE)
                data.remove(character); adapter.notifyDataSetChanged()
        }

        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle("Delete character")
            setMessage("Are you sure wanna delete the character \"${character.name}\"?")
            setPositiveButton("Delete", listener)
            setNegativeButton("Cancel", listener)
            create()
        }
        alertDialog.show()
    }
}