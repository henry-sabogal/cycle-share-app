package com.mastergenova.cycleshare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mastergenova.cycleshare.adapters.BikesByStationAdapter
import com.mastergenova.cycleshare.models.BikesByStation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BikesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BikesFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val args:BikesFragmentArgs by navArgs()

    private var idStation: Int = 0

    lateinit var rvBikes: RecyclerView
    lateinit var adapter: BikesByStationAdapter
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var googleMap: GoogleMap

    lateinit var tvName: TextView
    lateinit var tvDockCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_bikes, container, false)
        idStation = args.idStation

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_station) as SupportMapFragment
        mapFragment.getMapAsync(this)

        tvName = root.findViewById(R.id.tvNameStation)
        tvDockCount = root.findViewById(R.id.tvDockCountStation)

        rvBikes = root.findViewById(R.id.bikes_recycler_view)
        rvBikes.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)
        rvBikes.layoutManager = layoutManager

        adapter = BikesByStationAdapter(context)
        rvBikes.adapter = adapter

        val btnBack = root.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val seatle = LatLng(47.62139, -122.33040)

        this.googleMap = googleMap
        this.googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(seatle, 10f))
        fetchStation(idStation)
    }

    private fun fetchStation(id: Int){
        APIClient()
                .getAPIService()
                .getBikesByStation(id)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onNext = { response ->
                            System.out.println(response)
                            adapter.setDataset(response.bikes)
                            addMarker(response)
                            addStationInfo(response)
                        },
                        onError = {
                            e -> e.printStackTrace()
                        }
                )
    }

    private fun addMarker(station: BikesByStation){
        val stationLocation = LatLng(station.lat, station.lon)

        googleMap?.addMarker(MarkerOptions()
                .position(stationLocation)
        )

        googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
        googleMap?.animateCamera(CameraUpdateFactory.zoomTo(8f), 2000, null)

        val cameraPosition = CameraPosition.Builder()
                .target(stationLocation)
                .zoom(14f)
                .tilt(30f)
                .build()

        googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    private fun addStationInfo(station: BikesByStation){
        tvName.text = station.name
        tvDockCount.text = "Current Dock Count: " + station.currentDockCount
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BikesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                BikesFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}