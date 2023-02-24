package com.tubespbdandroid.majika.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tubespbdandroid.majika.R
import com.tubespbdandroid.majika.adapters.MenuAdapter
import com.tubespbdandroid.majika.data.DefaultResponse
import com.tubespbdandroid.majika.data.RestaurantMenu
import com.tubespbdandroid.majika.databinding.FragmentMenuBinding
import com.tubespbdandroid.majika.retrofit.menus.MenusClient
import com.tubespbdandroid.majika.viewmodels.MenuViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuFragment : Fragment(), SensorEventListener {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private var temperature: Sensor? = null
    private var tempVal: Int = 0
    private var queryArgs: String? = null
    private val viewModel: MenuViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, MenuViewModel.Factory(activity.application))
            .get(MenuViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        queryArgs = arguments?.getString("query")?.trim()
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
        (activity as AppCompatActivity).supportActionBar?.title = "Menu"

        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

        val foodCall = MenusClient.service.getAllFoods()
        val drinkCall = MenusClient.service.getAllDrinks()

        var foods = ArrayList<RestaurantMenu>()
        var beverages = ArrayList<RestaurantMenu>()

        val foodRecyclerView = binding.foodsRecyclerView
        val beveragesRecyclerView = binding.beveragesRecyclerView

        foodCall.enqueue(object: Callback<DefaultResponse<RestaurantMenu>> {
            override fun onResponse(call: Call<DefaultResponse<RestaurantMenu>>, response: Response<DefaultResponse<RestaurantMenu>>) {
                val data = response.body()!!.data

                for(i in 0 until data.size){
                    foods.add(data[i])
                }

                if (queryArgs != null) {
                    foods = filterUsingQuery(foods)
                }

                if (foods.size == 0) {
                    binding.foodsNoData.visibility = View.VISIBLE
                } else {
                    val foodAdapter = MenuAdapter(foods)
                    foodAdapter.notifyDataSetChanged()
                    foodRecyclerView.adapter = foodAdapter
                }
            }

            override fun onFailure(call: Call<DefaultResponse<RestaurantMenu>>, t: Throwable) {
                println(t.message)
            }
        })

        drinkCall.enqueue(object: Callback<DefaultResponse<RestaurantMenu>> {
            override fun onResponse(call: Call<DefaultResponse<RestaurantMenu>>, response: Response<DefaultResponse<RestaurantMenu>>) {
                val data = response.body()!!.data

                for(i in 0 until data.size){
                    beverages.add(data[i])
                }

                if (queryArgs != null) {
                    beverages = filterUsingQuery(beverages)
                }

                if (beverages.size == 0) {
                    binding.beveragesNoData.visibility = View.VISIBLE
                } else {
                    val beveragesAdapter = MenuAdapter(beverages)
                    beveragesAdapter.notifyDataSetChanged()
                    beveragesRecyclerView.adapter = beveragesAdapter
                }
            }

            override fun onFailure(call: Call<DefaultResponse<RestaurantMenu>>, t: Throwable) {
                println(t.message)
            }
        })

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


    private fun filterUsingQuery(restaurantMenus: ArrayList<RestaurantMenu>): ArrayList<RestaurantMenu> {
        val filteredRestaurantMenus = ArrayList<RestaurantMenu>()
        for (i in 0 until restaurantMenus.size) {
            if (restaurantMenus[i].name.contains(queryArgs!!, true)){
                filteredRestaurantMenus.add(restaurantMenus[i])
            }
        }

        return filteredRestaurantMenus
    }
}