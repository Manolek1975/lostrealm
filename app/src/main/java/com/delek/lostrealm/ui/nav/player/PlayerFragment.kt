package com.delek.lostrealm.ui.nav.player

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.lostrealm.database.dao.PlayerDAO
import com.delek.lostrealm.databinding.FragmentPlayerBinding
import com.delek.lostrealm.ui.role.RoleActivity

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
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}