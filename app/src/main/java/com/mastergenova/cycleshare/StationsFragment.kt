package com.mastergenova.cycleshare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.mastergenova.cycleshare.adapters.StationAdapter
import com.mastergenova.cycleshare.models.UserModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StationsFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val userModel: UserModel by activityViewModels()

    lateinit var rvStations:RecyclerView
    lateinit var adapter: StationAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var googleMap: GoogleMap

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
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_stations, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        rvStations = root.findViewById(R.id.statios_recycler_view)
        rvStations.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)
        rvStations.layoutManager = layoutManager

        val onClickStationSubject = PublishSubject.create<Pair<View, StationsAPIResponse?>>()
        onClickStationSubject.filter {
            it.second != null
        }.subscribeBy {
            userModel.selectStation(it.second!!)
            val action = StationsFragmentDirections.actionStationsFragmentToBikesFragment(it.second?.id!!)
            this.findNavController().navigate(action);
        }

        adapter = StationAdapter(context, onClickStationSubject)
        rvStations.adapter = adapter

        fetchStations()

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val seatle = LatLng(47.62139, -122.33040)

        this.googleMap = googleMap
        this.googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(seatle, 10f))
        this.googleMap?.setOnInfoWindowClickListener(this)
    }

    override fun onInfoWindowClick(marker: Marker?) {
        Toast.makeText(context, marker?.title, Toast.LENGTH_LONG).show()

        val action = StationsFragmentDirections.actionStationsFragmentToBikesFragment(1)
        this.findNavController().navigate(action);
    }

    private fun fetchStations(){
        userModel.getStationsList().observe(viewLifecycleOwner, Observer<List<StationsAPIResponse>> { stations ->
            adapter.setDataset(stations)
            addMarkersToMap(stations)
        })
    }

    private fun addMarkersToMap(stationsList: List<StationsAPIResponse>){
        var observable: Observable<StationsAPIResponse> = stationsList.toObservable()

        observable.subscribeBy (
            onNext = {
                googleMap?.addMarker(MarkerOptions()
                        .position(LatLng(it.lat, it.lon))
                        .title(it.name)
                        .snippet("Tap to get information")
                )
            },
            onError = {
                e -> e.printStackTrace()
            }
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StationsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StationsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}