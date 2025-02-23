package com.delek.lostrealm.ui.nav.player

import android.content.Context
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
import com.delek.lostrealm.database.dao.TileDAO
import com.delek.lostrealm.database.model.Map
import com.delek.lostrealm.databinding.FragmentPlayerBinding
import com.delek.lostrealm.ui.nav.PlayerActivity
import com.delek.lostrealm.ui.role.RoleActivity
import com.google.android.material.navigation.NavigationView

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private lateinit var adapter: PlayerAdapter
    private val binding get() = _binding!!
    private lateinit var context: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[PlayerViewModel::class.java]

        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        context = requireContext()

        val player = PlayerDAO(context).getAllPlayers()
        adapter = PlayerAdapter(player)
        binding.playerRecyclerView.setHasFixedSize(true)
        binding.playerRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.playerRecyclerView.adapter = adapter

        val textView: TextView = binding.playerHead
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.addPlayer.setOnClickListener {
            val intent = Intent(context, RoleActivity::class.java)
            startActivity(intent)
        }

        binding.fab.setOnClickListener {
            goMap()
        }

        return root
    }

    private fun goMap() {
        createMap()
        val nv: NavigationView = (context as PlayerActivity).findViewById(R.id.nav_view)
        val item = nv.menu.getItem(1) // To Map
        val navController = requireActivity().findNavController(R.id.nav_host)
        NavigationUI.onNavDestinationSelected(item, navController)
    }

    private fun createMap() {
        val mapList = mutableListOf<Map>()
        val rnd = (1..6).random() * 30f
        val tiles = TileDAO(context).getAllTiles()
        val bl = tiles[15]
        val map = Map(0, bl.id, 0, 0, rnd, 0)
        TileDAO(context).insertMap(map)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}