package com.example.probazaprvuspiralu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class GameDetailsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.game_details_fragment, container, false)
    companion object {
        fun newInstance(): GameDetailsFragment = GameDetailsFragment()
    }
}