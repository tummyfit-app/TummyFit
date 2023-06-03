@file:Suppress("BlockingMethodInNonBlockingContext", "BlockingMethodInNonBlockingContext",
    "BlockingMethodInNonBlockingContext"
)

package com.capstoneproject.tummyfit.ui.profile.editprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
//noinspection ExifInterface
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.data.remote.model.auth.UpdateUserRequestBody
import com.capstoneproject.tummyfit.data.remote.model.user.UserDescription
import com.capstoneproject.tummyfit.databinding.FragmentEditProfileBinding
import com.capstoneproject.tummyfit.utils.Constants
import com.capstoneproject.tummyfit.utils.createCustomTempFile
import com.capstoneproject.tummyfit.utils.imageMultipart
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.utils.uriToFile
import com.capstoneproject.tummyfit.wrapper.Resource
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext", "BlockingMethodInNonBlockingContext",
    "BlockingMethodInNonBlockingContext"
)
@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProfileViewModel by viewModels()
    private var getFile: File? = null
    private lateinit var pathPhoto: String

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val file = File(pathPhoto).also { file -> getFile = file }
            val os: OutputStream

            // Rotate image to correct orientation
            val bitmap = BitmapFactory.decodeFile(getFile?.path)
            val exif = ExifInterface(pathPhoto)
            val orientation: Int = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED
            )

            val rotatedBitmap: Bitmap = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> TransformationUtils.rotateImage(bitmap, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> TransformationUtils.rotateImage(bitmap, 180)
                ExifInterface.ORIENTATION_ROTATE_270 -> TransformationUtils.rotateImage(bitmap, 270)
                ExifInterface.ORIENTATION_NORMAL -> bitmap
                else -> bitmap
            }

            // Convert rotated image to file
            try {
                os = FileOutputStream(file)
                rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
                os.flush()
                os.close()

                getFile = file
            } catch (e: Exception) {
                e.printStackTrace()
            }

            binding.pictUser.setImageBitmap(rotatedBitmap)
            showUpdatePhotoDialog()
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())

            getFile = myFile
            binding.pictUser.setImageURI(selectedImg)
            showUpdatePhotoDialog()
        }
    }

    private fun showUpdatePhotoDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle("Update Photo Confirmation")
            .setMessage("Are you sure want to update photo?")
            .setPositiveButton("Yes") { _, _ ->
                getFile?.let { file ->
                    viewModel.updatePhotoUser(
                        imageMultipart(file)
                    )
                }
            }
            .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()
        back()
        observeData()
        editProfile()
        chooseImageDialog()
    }

    private fun chooseImageDialog() {
        binding.icEdit.setOnClickListener {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                202
            )
        ) {
            MaterialAlertDialogBuilder(
                requireContext(),
                com.google.android.material.R.style.MaterialAlertDialog_Material3_Body_Text_CenterStacked
            ).setMessage("Select your picture")
                .setNeutralButton("Camera") { _, _ -> launchCamera() }
                .setNeutralButtonIcon(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.baseline_photo_camera_24
                    )
                )
                .setPositiveButton("Gallery") { _, _ -> launchGallery() }
                .setPositiveButtonIcon(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.baseline_image_24
                    )
                )
                .show()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String, //for camera
        permissions: Array<String>, //for read write storage/gallery
        request: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        MaterialAlertDialogBuilder(
            requireContext(),
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Permission denied")
            .setMessage("Permission is denied, Please allow app permission from App Settings")
            .setPositiveButton("App Settings") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun launchCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                Constants.MY_PACKAGE,
                it
            )
            pathPhoto = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun launchGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, "Choose a picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun editProfile() {
        binding.apply {
            btnEditProfile.setOnClickListener {
                val username = etUsername.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val pw = etPw.text.toString().trim()
                val firstName = etFirstname.text.toString().trim()
                val lastName = etLastname.text.toString().trim()
                if (validateInput()) {
                    viewModel.updateUser(
                        UpdateUserRequestBody(username, firstName, lastName, email, pw)
                    )
                }
            }
        }
    }

    private fun observeData() {
        viewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Empty -> {
                    showLoading(false)
                }

                is Resource.Success -> {
                    showLoading(false)
                    FancyToast.makeText(
                        requireContext(),
                        it.data?.response?.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
                }

                is Resource.Error -> {
                    showLoading(false)
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
        viewModel.updatePhoto.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Empty -> {
                    showLoading(false)
                }

                is Resource.Success -> {
                    showLoading(false)
                    FancyToast.makeText(
                        requireContext(),
                        it.data?.response?.message,
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                }

                is Resource.Error -> {
                    showLoading(false)
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Empty -> {
                    showLoading(false)
                }

                is Resource.Success -> {
                    showLoading(false)
                    it.payload?.data?.userDescription?.let { it1 ->
                        bindToView(it1)
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    showSnackbar(requireView(), it.message.toString())
                }
            }
        }
    }

    private fun bindToView(userDescription: UserDescription) {
        binding.apply {
            Glide.with(pictUser).load(userDescription.user.urlprofile).into(pictUser)
            etFirstname.setText(userDescription.user.firstname)
            etLastname.setText(userDescription.user.lastname)
            etUsername.setText(userDescription.user.username)
            etEmail.setText(userDescription.user.email)
        }
    }


    private fun validateInput(): Boolean {
        var flag = true
        binding.apply {
            if (etUsername.text.toString().isEmpty()) {
                flag = false
                tilUsername.error = getString(R.string.error_field_empty)
                etUsername.requestFocus()
            }
            if (etEmail.text.toString().isEmpty()) {
                flag = false
                tilEmail.error = getString(R.string.error_field_empty)
                etEmail.requestFocus()
            }
            if (etPw.text.toString().isEmpty()) {
                flag = false
                tilPw.error = getString(R.string.error_field_empty)
                etPw.requestFocus()
            }
            if (etFirstname.text.toString().isEmpty()) {
                flag = false
                tilFirstname.error = getString(R.string.error_field_empty)
                etFirstname.requestFocus()
            }
            if (tilUsername.isErrorEnabled) {
                flag = false
            } else if (tilPw.isErrorEnabled) {
                flag = false
            } else if (tilFirstname.isErrorEnabled) {
                flag = false
            } else if (tilEmail.isErrorEnabled) {
                flag = false
            }
        }
        return flag
    }

    private fun showLoading(boolean: Boolean) {
        binding.bgView.isVisible = boolean
        binding.loadState.isVisible = boolean
    }

    private fun back() {
        binding.icBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}