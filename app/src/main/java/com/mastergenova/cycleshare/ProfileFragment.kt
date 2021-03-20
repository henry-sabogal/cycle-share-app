package com.mastergenova.cycleshare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mastergenova.cycleshare.models.Account
import com.mastergenova.cycleshare.models.UserModel
import com.mastergenova.cycleshare.utils.DownloadImageTask

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var displayName: TextView
    lateinit var email: TextView
    lateinit var photo: ImageView

    private val userModel: UserModel by activityViewModels()

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
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        displayName = root.findViewById(R.id.tvDisplayName)
        email = root.findViewById(R.id.tvEmail)
        photo = root.findViewById(R.id.imvPhotoProfile)

        val signOut = root.findViewById<Button>(R.id.btnSignOut)
        signOut.setOnClickListener {
            userModel.logout(true)
        }

        getUserInfo()
        return root
    }

    private fun getUserInfo(){
        userModel.userInfo.observe(viewLifecycleOwner, Observer<Account> { userInfo ->
            displayName.text = userInfo.displayName
            email.text = userInfo.email
            getPhotoUser(userInfo.photo.toString())
        })
    }

    private fun getPhotoUser(url: String){
        if(url != null){
            val imageTask = DownloadImageTask(photo, context)
            imageTask.execute(url)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}