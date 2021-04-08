package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ItemBinding

class MainAdapter (val callback: MainFragment.AsteroidClick) : RecyclerView.Adapter<MainFragment.AsteroidHolder>() {

    var asteroids: List<Asteroid> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragment.AsteroidHolder {
        val withDataBinding: ItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MainFragment.AsteroidHolder.LAYOUT,
            parent,
            false
        )
        return MainFragment.AsteroidHolder(withDataBinding)
    }


    override fun getItemCount() = asteroids.size

    override fun onBindViewHolder(holder: MainFragment.AsteroidHolder, position: Int) {
        holder.viewDataBinding.also {
            it.asteroid = asteroids[position]
            it.asteroidCallback = callback

        }
    }
}