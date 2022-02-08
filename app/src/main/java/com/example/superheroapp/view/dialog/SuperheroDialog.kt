package com.example.superheroapp.view.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.superheroapp.R
import com.example.superheroapp.databinding.DialogSuperheroBinding
import com.example.superheroapp.databinding.FragmentHomeBinding
import com.example.superheroapp.model.Superhero
import com.squareup.picasso.Picasso

class SuperheroDialog(var superhero: Superhero) : DialogFragment() {

    companion object {
        const val TAG = "Superhero_View"
        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"
    }
    private lateinit var binding: DialogSuperheroBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSuperheroBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()

        val metrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
        val width = metrics.widthPixels // ancho absoluto en pixels
        val height = metrics.heightPixels // alto absoluto en pixels

        var dialogWidth = width * .9 // specify a value here
        var dialogHeight = height * .85 // specify a value here
        dialog?.window?.setLayout(
            dialogWidth.toInt(),
            dialogHeight.toInt()
        )
        //dialog.getWindow().setLayout(dialogWidth, dialogHeight);
    }

    private fun setupView(view: View) {
        if(superhero!=null) {
            binding.name.text = superhero.name
            if (superhero.image != null && superhero.image.url != null) {
                Picasso.get()
                    .load(superhero.image.url)
                    .placeholder(R.drawable.empty)
                    .error(R.drawable.empty)
                    .into(binding.photo)
            }
            if (superhero.powerstats != null) {
                binding.intelligence.text = superhero.powerstats.intelligence
                binding.strength.text = superhero.powerstats.strength
                binding.speed.text = superhero.powerstats.speed
                binding.durability.text = superhero.powerstats.durability
                binding.power.text = superhero.powerstats.power
                binding.combat.text = superhero.powerstats.combat
            }
            if (superhero.biography != null) {
                binding.fullName.text = superhero.biography.fullName
                binding.alterEgos.text = superhero.biography.alterEgos
                binding.aliases.text = arraytoString(superhero.biography.aliases)
                binding.placeOfBirth.text = superhero.biography.placeBirth
                binding.firstAppearance.text = superhero.biography.firstAppearance
                binding.publisher.text = superhero.biography.publisher
                binding.alignment.text = superhero.biography.alignment
            }
            if (superhero.appearance != null) {
                binding.gender.text = superhero.appearance.gender
                binding.race.text = superhero.appearance.race
                binding.height.text = arraytoString(superhero.appearance.height)
                binding.weight.text = arraytoString(superhero.appearance.weight)
                binding.eyeColor.text = superhero.appearance.eyeColor
                binding.hairColor.text = superhero.appearance.hairColor
            }
            if (superhero.work != null) {
                binding.occupation.text = superhero.work.occupation
                binding.base.text = superhero.work.base
            }
            if (superhero.connections != null) {
                binding.groupAffiliation.text = superhero.connections.groupAffiliation
                binding.relatives.text = superhero.connections.relatives
            }
        }
    }

    private fun setupClickListeners(view: View) {

    }

    fun arraytoString(array: Array<String>): String {
        var response = ""
        if (array != null) {
            for (value in array) {
                if(!response.equals("")){
                    response += ", "
                }
                response += value
            }
        }
        return response
    }
}