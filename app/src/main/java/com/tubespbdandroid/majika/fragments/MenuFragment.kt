package com.tubespbdandroid.majika.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tubespbdandroid.majika.R
import com.tubespbdandroid.majika.adapters.MenuAdapter
import com.tubespbdandroid.majika.data.Menus
import com.tubespbdandroid.majika.databinding.FragmentMenuBinding

class MenuFragment : Fragment(), SensorEventListener {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private var temperature: Sensor? = null
    private var tempVal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.foodsRecyclerView.setHasFixedSize(true)

        binding.foodsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.beveragesRecyclerView.layoutManager = LinearLayoutManager(this.context)

        return view
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.temperature_appbar, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        val listFoods = ArrayList<Menus>()
        val listBeverages = ArrayList<Menus>()
        val listMenuFoods = arrayOf(
            "Makanan A", "Makanan B", "Makanan C", "Makanan D",
            "Makanan 1", "Makanan 2", "Makanan 3", "Makanan 4",
        )
        val listMenuBeverages = arrayOf(
            "Minuman A", "Minuman B", "Minuman C", "Minuman D",
            "Minuman 1", "Minuman 2", "Minuman 3", "Minuman 4",
        )

        for (i in 0 until listMenuFoods.size) {
            listFoods.add(Menus(listMenuFoods.get(i)))
            listBeverages.add(Menus(listMenuBeverages.get(i)))

            if(listMenuFoods.size - 1 == i){
                val foodAdapter = MenuAdapter(listFoods)
                val beveragesAdapter = MenuAdapter(listBeverages)
                foodAdapter.notifyDataSetChanged()
                beveragesAdapter.notifyDataSetChanged()

                binding.foodsRecyclerView.adapter = foodAdapter
                binding.beveragesRecyclerView.adapter = beveragesAdapter
            }
        }
    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        tempVal = p0!!.values[0].toInt()
        this.requireActivity().invalidateOptionsMenu()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val tempAppbar = menu.findItem(R.id.temperature)
        if (temperature == null) {
            tempAppbar!!.title = "-℃"
        } else {
            tempAppbar!!.title = "${tempVal.toString()} \u2103"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}