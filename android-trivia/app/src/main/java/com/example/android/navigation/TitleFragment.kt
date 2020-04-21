package com.example.android.navigation

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
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

        setHasOptionsMenu(true) // Enable menu in the action bar

        return binding.root
    }

    // Apply the menu resource for menu in the action bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
               || super.onOptionsItemSelected(item)
    }
}
