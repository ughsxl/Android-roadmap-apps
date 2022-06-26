package android.bignerdranch.roadmapapps.baseadapter

import android.annotation.SuppressLint
import android.bignerdranch.roadmapapps.R
import android.bignerdranch.roadmapapps.databinding.DialogAddCharacterBinding
import android.bignerdranch.roadmapapps.databinding.SpinnerLayoutBinding
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class SpinnerWithBaseAdapterSample: AppCompatActivity() {
    private lateinit var binding: SpinnerLayoutBinding

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

        binding = SpinnerLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()

        binding.addButton.setOnClickListener { onAddPressed() }
    }

    private fun setupSpinner() {
        adapter = CharacterAdapter(characters = data) { deleteCharacter(it) }
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long, ) {
                val selectedCharacter = data[position]
                binding.characterDescribeTextView.text = "Name: ${selectedCharacter.name}\nid: ${selectedCharacter.id}"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { binding.characterDescribeTextView.text = "" }


        }
    }

    private fun onAddPressed() {
        val alertDialogBinding = DialogAddCharacterBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle("Create character")
            setView(alertDialogBinding.root)
            setPositiveButton("Add") { _, _ ->
                val name = alertDialogBinding.characterNameEditText.text.toString()
                if (name.isNotBlank()) createCharacter(name)
            }
            create()
        }
        alertDialog.show()
    }

    private fun createCharacter(characterName: String) {
        val createdCharacter = Character(
            id = Random.nextLong(),
            name = characterName
        )
        data.add(createdCharacter)
        adapter.notifyDataSetChanged()
    }

    private fun deleteCharacter(characterForDelete: Character) {
        val alertDialogListener = DialogInterface.OnClickListener { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE)
                data.remove(characterForDelete); adapter.notifyDataSetChanged()
        }

        val alertDialog = AlertDialog.Builder(this).apply {
            setTitle("Delete character")
            setMessage("Are you sure wanna delete the character \"${characterForDelete.name}\"?")
            setPositiveButton("Delete", alertDialogListener)
            setNegativeButton("Cancel", alertDialogListener)
            create()
        }
        alertDialog.show()
    }
}