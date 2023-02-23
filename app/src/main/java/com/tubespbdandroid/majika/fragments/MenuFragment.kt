package com.tubespbdandroid.majika.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var queryArgs: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        queryArgs = arguments?.getString("query")
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
        inflater.inflate(R.menu.temperature_appbar, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        val (listMenuFoods, listMenuBeverages) = fetchData()

        if (listMenuFoods.size == 0) {
            binding.foodsNoData.visibility = View.VISIBLE
        } else {
            initAdapter(listMenuFoods, binding.foodsRecyclerView)
        }

        if (listMenuBeverages.size == 0) {
            binding.beveragesNoData.visibility = View.VISIBLE
        } else {
            initAdapter(listMenuBeverages, binding.beveragesRecyclerView)
        }
    }


    override fun onResume() {
        super.onResume()
        if (temperature == null) {
            return
        }
        sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onPause() {
        super.onPause()
        if (temperature == null) {
            return
        }
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        tempVal = p0!!.values[0].toInt()
        this.requireActivity().invalidateOptionsMenu()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
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

    private fun initAdapter(listMenuName: ArrayList<String>, recyclerView: RecyclerView) {
        val listMenu = ArrayList<Menus>()
        for (i in 0 until listMenuName.size) {
            listMenu.add(Menus(listMenuName.get(i)))

            if(listMenuName.size - 1 == i){
                val menuAdapter = MenuAdapter(listMenu)
                menuAdapter.notifyDataSetChanged()
                recyclerView.adapter = menuAdapter
            }
        }
    }

    private fun fetchData(): Array<ArrayList<String>> {
        var foods = ArrayList<String>()
        var beverages = ArrayList<String>()

        foods.addAll(arrayOf("Makanan A", "Makanan B", "Makanan C", "Makanan D",
            "Makanan 1", "Makanan 2", "Makanan 3", "Makanan 4",
            "Minuman A", "Minuman B", "Minuman C", "Minuman D",
            "Minuman 1", "Minuman 2", "Minuman 3", "Minuman 4"))

        beverages.addAll(arrayOf("Minuman A", "Minuman B", "Minuman C", "Minuman D",
            "Minuman 1", "Minuman 2", "Minuman 3", "Minuman 4",
            "Makanan A", "Makanan B", "Makanan C", "Makanan D",
            "Makanan 1", "Makanan 2", "Makanan 3", "Makanan 4",))

        if (queryArgs != null) {
            foods = filterUsingQuery(foods)
            beverages = filterUsingQuery(beverages)
        }

        return arrayOf(foods, beverages)
    }

    private fun filterUsingQuery(menus: ArrayList<String>): ArrayList<String> {
        val filteredMenus = ArrayList<String>()
        for (i in 0 until menus.size) {
            if (menus[i].contains(queryArgs!!, true)){
                filteredMenus.add(menus[i])
            }
        }

        return filteredMenus
    }
}