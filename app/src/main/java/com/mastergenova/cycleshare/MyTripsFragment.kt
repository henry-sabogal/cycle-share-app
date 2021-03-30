package com.mastergenova.cycleshare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mastergenova.cycleshare.adapters.UserTripsAdapter
import com.mastergenova.cycleshare.models.Account
import com.mastergenova.cycleshare.models.TripUser
import com.mastergenova.cycleshare.models.UserModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyTripsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyTripsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val userModel: UserModel by activityViewModels()

    lateinit var rvTrips: RecyclerView
    lateinit var adapter: UserTripsAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_my_trips, container, false)

        rvTrips = root.findViewById(R.id.trips_recycler_view)
        rvTrips.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)
        rvTrips.layoutManager = layoutManager

        adapter = UserTripsAdapter(context)
        rvTrips.adapter = adapter

        fetchTrips()

        userModel.userInfo.observe(viewLifecycleOwner, Observer<Account> { user ->
            userModel.fetchUserTrips(user.id)
        })

        return root
    }

    private fun fetchTrips(){
        userModel.userTrips.observe(viewLifecycleOwner, Observer<List<TripUser>> { trips ->
            adapter.setDataset(trips)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyTripsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyTripsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}