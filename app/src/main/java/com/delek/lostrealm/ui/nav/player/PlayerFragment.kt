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
import com.delek.lostrealm.databinding.FragmentHomeBinding
import com.delek.lostrealm.ui.role.RoleActivity

class PlayerFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: PlayerAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[PlayerViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val player = PlayerDAO(requireContext()).getAllPlayers()
        adapter = PlayerAdapter(player)
        binding.playerRecyclerView.setHasFixedSize(true)
        binding.playerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.playerRecyclerView.adapter = adapter

/*        for (p in player) {
            binding.textPlayer.text = p.name
        }*/

        val textView: TextView = binding.playerHead
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.addPlayer.setOnClickListener {
            val intent = Intent(requireContext(), RoleActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}