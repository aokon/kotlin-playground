package com.example.android.navigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentTitleBinding
import kotlinx.android.synthetic.main.fragment_title.*
import java.util.logging.Logger

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        binding.playButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
//             This is not working...
//            Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment)
        }

        return binding.root
    }

}
