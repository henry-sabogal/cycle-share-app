package com.mastergenova.cycleshare

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mastergenova.cycleshare.models.Account
import com.mastergenova.cycleshare.models.UserModel
import com.mastergenova.cycleshare.utils.DownloadImageTask
import com.mastergenova.cycleshare.utils.ProfilePicture
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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
    lateinit var toggle: Switch

    lateinit var profilePictureManagement:ProfilePicture
    lateinit var urlPhotoGoogleAccount:String

    private val userModel: UserModel by activityViewModels()
    private var currentImagePath = ""

    lateinit var uri: Uri

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

        profilePictureManagement = ProfilePicture(context)

        val signOut = root.findViewById<Button>(R.id.btnSignOut)
        signOut.setOnClickListener {
            userModel.logout(true)
        }

        toggle = root.findViewById(R.id.toggleButton)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked && this::urlPhotoGoogleAccount.isInitialized){
                setGooglePhoto()
            }else{
                getPhotoUser()
            }
        }

        getUserInfo()



        val getCameraImage = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if(success){
                Log.i("CAMERA", "Got image at: $uri")
                photo.setImageURI(uri)
                profilePictureManagement.storeUri(uri = uri)
            }
        }

        val requestPermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultsMap ->
            var granted = true
            resultsMap.forEach{
                if(!it.value){
                    granted = false
                }
            }
            if(granted){
                uri = FileProvider.getUriForFile(requireActivity(), "com.mastergenova.cycleshare", createImageFile())
                getCameraImage.launch(uri)
            }
        }

        val changePicture = root.findViewById<Button>(R.id.btnChangeImage)
        changePicture.setOnClickListener {
            requestPermissions.launch(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA))
        }

        return root
    }

    private fun getUserInfo(){
        userModel.userInfo.observe(viewLifecycleOwner, Observer<Account> { userInfo ->
            displayName.text = userInfo.displayName
            email.text = userInfo.email
            urlPhotoGoogleAccount = userInfo.photo.toString()
            getPhotoUser()
        })
    }

    private fun getPhotoUser(){
        val pictureUriStore = profilePictureManagement.retrieveUri()
        if(pictureUriStore.equals("")){
            setGooglePhoto()
            Toast.makeText(context, "There is not a custom profile picture for the user", Toast.LENGTH_LONG).show()
        }else{
            val imageUri = Uri.parse(pictureUriStore)
            photo.setImageURI(imageUri)
        }
    }

    private fun setGooglePhoto(){
        val imageTask = DownloadImageTask(photo, context)
        imageTask.execute(urlPhotoGoogleAccount)
    }

    private fun createImageFile(): File{
        val timeStamp = "my_custom_photo"
        val storageDir = context?.getExternalFilesDir(null)

        return File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
        ).apply {
            currentImagePath = absolutePath
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