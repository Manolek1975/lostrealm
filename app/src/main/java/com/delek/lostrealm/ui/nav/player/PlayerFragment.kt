package com.delek.lostrealm.ui.nav.player

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Point
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
import com.delek.lostrealm.database.dao.MapDAO
import com.delek.lostrealm.database.dao.PlayerDAO
import com.delek.lostrealm.database.dao.TileDAO
import com.delek.lostrealm.database.model.Map
import com.delek.lostrealm.database.model.Tile
import com.delek.lostrealm.databinding.FragmentPlayerBinding
import com.delek.lostrealm.ui.nav.PlayerActivity
import com.delek.lostrealm.ui.role.RoleActivity
import com.google.android.material.navigation.NavigationView
import java.lang.reflect.Field

class PlayerFragment : Fragment() {

    private var _binding: FragmentPlayerBinding? = null
    private lateinit var adapter: PlayerAdapter
    private val binding get() = _binding!!
    private lateinit var context: Context
    private lateinit var data: android.content.SharedPreferences

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

        data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        data.getInt("initial", 0)
        if (data.getInt("initial", 0) == 1) {
            binding.fab.visibility = View.GONE
        }

        binding.fab.setOnClickListener {
            data.edit().putInt("initial", 1).apply()
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

        val mapList = mutableListOf<Tile>()
        val tiles = TileDAO(context).getAllTiles()
        for (tile in tiles)
            mapList.add(tile)
        val firstTile = tiles[14] //Borderland Tile
        val map = Map(0, firstTile.id, 0, 0, rotate(), 0)
        MapDAO(context).insertMap(map)

        mapList.remove(firstTile)
        mapList.shuffle()

        val secondTile = tiles[0]
        val side = (1..6).random() // Random to Tile side
        println(side)
        val point = setSide(side, secondTile)
        val map2 = Map(0, secondTile.id, point.x, point.y, rotate(), 0)
        MapDAO(context).insertMap(map2)
        mapList.remove(secondTile)

    }

    private fun rotate(): Int {
        val rnd = (1..6).random() * 60 // Random to Tile rotate
        return rnd
    }

    private fun setSide(side: Int, tile: Tile): Point {
        val id = getResId(tile.image, R.drawable::class.java)
        val bm = BitmapFactory.decodeResource(resources, id)
        val w = bm.width
        val h = bm.height
        val r = w/2 //The radius is equal to the side that is half the width
        var point = Point()
        when (side) {
            0 -> point = Point(0, h)
            1 -> point = Point(-3*r/2, h/2)
            2 -> point = Point(-3*r/2, -h/2)
            3 -> point = Point(0, -h)
            4 -> point = Point(3*r/2, -h/2)
            5 -> point = Point(3*r/2, h/2)
        }
        return point
    }

    private fun getResId(resName: String?, c: Class<*>): Int {
        try {
            val idField: Field = c.getDeclaredField(resName!!)
            return idField.getInt(idField)
        } catch (e: Exception) {
            e.printStackTrace()
            return -1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}