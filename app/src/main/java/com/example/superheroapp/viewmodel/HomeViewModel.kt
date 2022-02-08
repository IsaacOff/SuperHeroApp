package com.example.superheroapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.superheroapp.model.Superhero

class HomeViewModel : ViewModel() {

    val superheros = MutableLiveData<MutableList<Superhero>>()

    fun setSuperheros(item: MutableList<Superhero>) {
        superheros.postValue(item)
    }

    fun getSuperHerosFromServer() {
        Superhero.downloadRange(this)
    }
}
