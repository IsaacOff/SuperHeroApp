package com.example.superheroapp.view

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroapp.R
import com.example.superheroapp.databinding.FragmentHomeBinding
import com.example.superheroapp.model.Superhero
import com.example.superheroapp.view.adapter.HeroAdapter
import com.example.superheroapp.view.dialog.SuperheroDialog
import com.example.superheroapp.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private var mAdapter : HeroAdapter? = null
    var heros: MutableList<Superhero> = ArrayList()
    var isDownload: Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        homeViewModel.superheros.observe(viewLifecycleOwner, Observer {
            binding.dialogChargin.visibility = View.GONE
            isDownload = false
            heros = it
            setUpRecyclerView()
        })
        getSuperHeroes()
        return binding.root
    }

    private fun setUpRecyclerView(){
        if (heros.size>0) {
            if (mAdapter == null) {
                mAdapter = HeroAdapter()
                binding.recyclerSuperhero.setHasFixedSize(true)
                binding.recyclerSuperhero.layoutManager = LinearLayoutManager(context)
                mAdapter!!.HeroAdapter(heros, requireContext()) { item ->
                    if (item != null) {
                        var dialog = SuperheroDialog(item)
                        dialog.show(parentFragmentManager, SuperheroDialog.TAG)
                    } else {
                        Toast.makeText(context, "No encontrado", Toast.LENGTH_SHORT).show()
                    }
                }
                binding.recyclerSuperhero.adapter = mAdapter
            } else {
                mAdapter!!.notifyDataSetChanged()
            }
        }
        binding.recyclerSuperhero.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !isDownload){
                    getSuperHeroes()
                }
            }

        })
    }

    fun getSuperHeroes() {
        binding.dialogChargin.visibility = View.VISIBLE
        isDownload = true
        homeViewModel.getSuperHerosFromServer()
    }

}