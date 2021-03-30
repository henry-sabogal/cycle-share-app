package com.mastergenova.cycleshare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mastergenova.cycleshare.adapters.SpinnerStationsAdapter
import com.mastergenova.cycleshare.models.Account
import com.mastergenova.cycleshare.models.Bike
import com.mastergenova.cycleshare.models.TripAPIResponse
import com.mastergenova.cycleshare.models.UserModel
import com.mastergenova.cycleshare.utils.BookSuccessDialogFragment
import com.mastergenova.cycleshare.utils.DatePickerFragment
import com.mastergenova.cycleshare.utils.TimePickerFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookBikeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookBikeFragment : Fragment(), AdapterView.OnItemSelectedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvStationName: TextView
    private lateinit var tvBikeName: TextView
    private lateinit var spStations: Spinner
    private lateinit var tvSelectedDate: TextView
    private lateinit var tvSelectedTime: TextView

    private lateinit var stationSelected: StationsAPIResponse

    private lateinit var adapter: SpinnerStationsAdapter

    private val userModel: UserModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_book_bike, container, false)

        tvStationName = root.findViewById(R.id.tvNameStation)
        tvBikeName = root.findViewById(R.id.tvNameBike)
        tvName = root.findViewById(R.id.tvDisplayName)
        tvEmail = root.findViewById(R.id.tvEmail)
        spStations = root.findViewById(R.id.stations_spinner)
        tvSelectedDate = root.findViewById(R.id.tvSelectedDate)
        tvSelectedTime = root.findViewById(R.id.tvSelectedTime)

        tvSelectedTime.text = ""
        tvSelectedDate.text = ""

        spStations.onItemSelectedListener = this

        val btnBack = root.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        val btnSelectTime = root.findViewById<Button>(R.id.btnSelectTime)
        btnSelectTime.setOnClickListener {
            showTimePickerDialog()
        }

        val btnSelectDate = root.findViewById<Button>(R.id.btnSelectDate)
        btnSelectDate.setOnClickListener {
            showDateTimePickerDialog()
        }

        val btnBookTrip = root.findViewById<Button>(R.id.btnBook)
        btnBookTrip.setOnClickListener {
            if(!tvSelectedDate.text.equals("")
                    && !tvSelectedTime.text.equals("")
                    && spStations.selectedItem != null){
                        it.isClickable = false
                        userModel.bookTrip()
            }else{
                Toast.makeText(context, "Please select data, time and one station for the trip.", Toast.LENGTH_LONG).show()
            }
        }

        setStationInfo()
        setBikeInfo()
        setUserInfo()

        fetchStations()

        userModel.selectedDate.observe(viewLifecycleOwner, Observer<String>{ date ->
            tvSelectedDate.text = date
        })

        userModel.selectedTime.observe(viewLifecycleOwner, Observer<String> { time ->
            tvSelectedTime.text = time
        })

        showAlertDialog()

        return root
    }

    override fun onResume() {
        super.onResume()
        tvSelectedTime.text = ""
        tvSelectedDate.text = ""
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val station: StationsAPIResponse = parent?.getItemAtPosition(position) as StationsAPIResponse
        userModel.selectToStation(station)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //nothin selected
    }

    private fun fetchStations(){
        userModel.getStationsList().observe(viewLifecycleOwner, Observer<List<StationsAPIResponse>> { stations ->
            adapter = SpinnerStationsAdapter(context, stations.filter { it != stationSelected })
            spStations.adapter = adapter
        })
    }

    private fun setStationInfo(){
        userModel.selectedStation.observe(viewLifecycleOwner, Observer<StationsAPIResponse> { station ->
            this.stationSelected = station
            tvStationName.text = station.name
        })
    }

    private fun setBikeInfo(){
        userModel.selectedBike.observe(viewLifecycleOwner, Observer<Bike> { bike ->
            tvBikeName.text = bike.name
        })
    }

    private fun setUserInfo(){
        userModel.userInfo.observe(viewLifecycleOwner, Observer<Account> { account ->
            tvName.text = account.displayName
            tvEmail.text = account.email
        })
    }

    private fun showTimePickerDialog(){
        TimePickerFragment().show(childFragmentManager, "timePicker")
    }

    private fun showDateTimePickerDialog(){
        DatePickerFragment(context).show(childFragmentManager, "datePicker")
    }

    private fun showAlertDialog(){
        userModel.tripAPIResponse.observe(viewLifecycleOwner, Observer<TripAPIResponse> { tripResponse ->
            if(tripResponse.success){
                val alert = BookSuccessDialogFragment()
                alert.show(childFragmentManager, "alertDialog")
                userModel.resetTripAPIResponse()
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookBikeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                BookBikeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}