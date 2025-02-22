package com.delek.lostrealm.ui.nav.player

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.lostrealm.R
import com.delek.lostrealm.database.dao.PlayerDAO
import com.delek.lostrealm.databinding.FragmentPlayerBinding
import com.delek.lostrealm.ui.nav.PlayerActivity
import com.delek.lostrealm.ui.role.RoleActivity
import com.google.android.material.navigation.NavigationView

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private lateinit var adapter: PlayerAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[PlayerViewModel::class.java]

        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val player = PlayerDAO(requireContext()).getAllPlayers()
        adapter = PlayerAdapter(player)
        binding.playerRecyclerView.setHasFixedSize(true)
        binding.playerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.playerRecyclerView.adapter = adapter

        val textView: TextView = binding.playerHead
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.addPlayer.setOnClickListener {
            val intent = Intent(requireContext(), RoleActivity::class.java)
            startActivity(intent)
        }

        binding.fab.setOnClickListener {
            createMap()
        }

        return root
    }

    private fun createMap() {
        val nv: NavigationView = (context as PlayerActivity).findViewById(R.id.nav_view)
        val item = nv.menu.getItem(1) // To Map
        val navController = requireActivity().findNavController(R.id.nav_host)
        NavigationUI.onNavDestinationSelected(item, navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}