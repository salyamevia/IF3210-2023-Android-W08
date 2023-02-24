package com.tubespbdandroid.majika.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.tubespbdandroid.majika.R
import com.tubespbdandroid.majika.databinding.FragmentTwibbonBinding
import kotlin.random.Random

class TwibbonFragment: Fragment() {
    private var _fragmentTwibbonBinding: FragmentTwibbonBinding? = null
    private val fragmentTwibbonBinding get() = _fragmentTwibbonBinding!!
    private val CAMERA_PERMS_CODE = 1000

    private var cameraButton: Button? = null
    private var cameraPreview: ImageView? = null

    private val IMAGE_CAPTURE_CODE = 1001
    private var imageUri: Uri? = null

    private var twibbons = arrayOf(
        R.drawable.risu,
        R.drawable.anya,
        R.drawable.kobokan,
        R.drawable.reine,
        R.drawable.yopi,
        R.drawable.zeta
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTwibbonBinding = FragmentTwibbonBinding.inflate(inflater, container, false)

        cameraButton = fragmentTwibbonBinding.twibbonButton
        cameraPreview = fragmentTwibbonBinding.twibbonCameraView

        cameraButton!!.setOnClickListener{
            val permissionGranted = requestCameraPerms()
            if (permissionGranted) {
                openCameraInterface()
            }

        }
        return fragmentTwibbonBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Twibbon"
    }

   private fun requestCameraPerms() : Boolean {
       var permissionGranted = false
       val cameraPermsNotGranted = checkSelfPermission(activity as Context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED

       if (cameraPermsNotGranted) {
           val permission = arrayOf(Manifest.permission.CAMERA)

           requestPermissions(permission, CAMERA_PERMS_CODE)
       } else {
           permissionGranted = true
       }

       return permissionGranted
   }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode === CAMERA_PERMS_CODE) {
            if(grantResults.size === 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCameraInterface()
            } else {
                showAlert("Camera permission was denied. Unable to take a picture.")
            }
        }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(activity as Context)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok, null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun Blend(bottomImage2:Uri): Bitmap {
        val randomValue = Random.nextInt(0, twibbons.size)

        val contentSolver = requireActivity().contentResolver
        val bottomImage1: Bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentSolver, bottomImage2))

        val workingBitmap: Bitmap = BitmapFactory.decodeResource(context?.resources, twibbons[randomValue])
        val topImage: Bitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true)

        val workingBitmap2: Bitmap = Bitmap.createBitmap(bottomImage1)
        val bottomImage: Bitmap = workingBitmap2.copy(Bitmap.Config.ARGB_8888, true)

        val dest: Rect = Rect(0, 0, bottomImage.width, bottomImage.height)
        BitmapFactory.Options().inPreferredConfig = Bitmap.Config.ARGB_8888

        val canvas: Canvas = Canvas(bottomImage)
        val paint: Paint = Paint()

        paint.isFilterBitmap = true
        canvas.drawBitmap(topImage, null, dest, paint)

        return bottomImage


    }

    private fun openCameraInterface() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, R.string.permission_required_title)
        values.put(MediaStore.Images.Media.DESCRIPTION, R.string.permission_required)

        imageUri = activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        cameraLauncher.launch(cameraIntent)
    }

    val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            cameraPreview?.setImageBitmap(imageUri?.let { Blend(it) })//imageUri)

        } else {
            showAlert("Failed to take camera picture")
        }
    }
}