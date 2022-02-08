package com.example.superheroapp.model

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.superheroapp.Retrofit.MyApiAdapter
import com.example.superheroapp.Retrofit.MyApiService
import com.example.superheroapp.viewmodel.HomeViewModel
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.*

data class Superhero (
    var response: String,
    var id: Int,
    var name: String,
    var powerstats: PowerStats,
    var biography: Biography,
    var appearance: Appearance,
    var work: Work,
    var connections: Connections,
    var image: Image
) {
    companion object {
        fun downloadRange(viewmodel: HomeViewModel) {
            var heros: MutableList<Superhero> = if (viewmodel.superheros.value != null) viewmodel.superheros.value!! else ArrayList()
            var herosDownload: MutableList<Superhero> = ArrayList()
            GlobalScope.launch{
                superheroDownload(heros, herosDownload, viewmodel)
            }
        }
        suspend fun superheroDownload(heros: MutableList<Superhero>, herosDownload: MutableList<Superhero>, viewmodel: HomeViewModel) = coroutineScope {  // this: CoroutineScope
            var countResponse: MutableList<Boolean> = ArrayList<Boolean>()
            for (i in 0..24) {
                countResponse.add(i, false)
            }
            val jobs = List(25) {
                launch {
                    val call = MyApiAdapter.APIService?.getSuperHero(heros.size + it+1)
                    val response = call?.body()
                    if (call != null) {
                        if (call.isSuccessful) {
                            if (response != null) {
                                herosDownload.add(response)
                            }
                        }
                    }
                    countResponse.set(it, true)
                    var finish = true
                    for(value in countResponse){
                        if (!value) {
                            finish = false
                            break
                        }
                    }
                    if (finish) {
                        mergeAndSetDownload(heros, herosDownload, viewmodel)
                    }
                }
            }
        }
        fun mergeAndSetDownload(heros: MutableList<Superhero>, herosDownload: MutableList<Superhero>, viewmodel: HomeViewModel) {
            heros.addAll(herosDownload)
            heros.sortBy { it.id }
            viewmodel.setSuperheros(heros)
        }
    }
}

data class PowerStats (
    var intelligence: String,
    var strength: String,
    var speed: String,
    var durability: String,
    var power: String,
    var combat: String
)


data class Biography (
    @SerializedName("full-name") var fullName: String,
    @SerializedName("alter-egos") var alterEgos: String,
    var aliases: Array<String>,
    @SerializedName("place-of-birth") var placeBirth: String,
    @SerializedName("first-appearance") var firstAppearance: String,
    var publisher: String,
    var alignment: String
)

data class Appearance (
    var gender: String,
    var race: String,
    var height: Array<String>,
    var weight: Array<String>,
    @SerializedName("eye-color") var eyeColor: String,
    @SerializedName("hair-color") var hairColor: String
)
data class Work (
    var occupation: String,
    var base: String
)
data class Connections (
    @SerializedName("group-affiliation") var groupAffiliation: String,
    var relatives: String
)
data class Image (
    var url: String
)