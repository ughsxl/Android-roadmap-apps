package android.bignerdranch.roadmapapps.baseadapter

import android.bignerdranch.roadmapapps.databinding.ItemCharacterBinding
import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

typealias OnDeletePressedListener = (Character) -> Unit

class CharacterAdapter(
    private val characters: List<Character>,
    private val onDeletePressedListener: OnDeletePressedListener
): BaseAdapter(), View.OnClickListener {

    override fun getCount(): Int = characters.size

    override fun getItem(position: Int): Character = characters[position]

    override fun getItemId(position: Int): Long = characters[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDefaultView(position, convertView, parent, isDropDownView = false)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getDefaultView(position, convertView, parent, isDropDownView = true)

    }

    private fun getDefaultView(position: Int, convertView: View?, parent: ViewGroup, isDropDownView: Boolean): View {
        val binding = convertView?.tag as ItemCharacterBinding? ?: createBinding(parent.context)

        val character = getItem(position)

        binding.titleTextView.text = character.name
        binding.deleteImageView.tag = character
        binding.deleteImageView.visibility = if (isDropDownView) View.GONE else View.VISIBLE

        return binding.root
    }

    override fun onClick(view: View) {
        val character = view.tag as Character
        onDeletePressedListener.invoke(character)
    }

    private fun createBinding(context: Context): ItemCharacterBinding {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(context))
        binding.deleteImageView.setOnClickListener(this)
        binding.root.tag = binding

        return binding
    }

}