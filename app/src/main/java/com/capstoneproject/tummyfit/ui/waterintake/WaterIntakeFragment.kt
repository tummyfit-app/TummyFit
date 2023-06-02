package com.capstoneproject.tummyfit.ui.waterintake

import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.capstoneproject.tummyfit.R
import com.capstoneproject.tummyfit.databinding.FragmentWaterIntakeBinding
import com.capstoneproject.tummyfit.utils.showSnackbar
import com.capstoneproject.tummyfit.utils.showTimePickerNotification
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaterIntakeFragment : Fragment() {

    private var _binding: FragmentWaterIntakeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WaterIntakeViewModel by viewModels()
    private var selectedOption: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaterIntakeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toBack()
        selectOption()
        notification()
    }

    override fun onStart() {
        super.onStart()
        observeData()
    }

    private fun notification() {
        binding.fabNotif.setOnClickListener {
            showTimePickerNotification(parentFragmentManager)
        }
    }

    private fun observeData() {
        viewModel.getStatsWaterIntake()
        viewModel.water.observe(viewLifecycleOwner) {
            binding.apply {
                waterWave.setAnimationSpeed(50)
                waterWave.progress =
                    if (it.now_intake > it.total_intake) 100 else it.now_intake * 100 / it.total_intake
                waterWave.max = 100
                waterWave.startAnimation()
                resultRemaining.text = String.format(
                    resources.getString(R.string.ml_template),
                    it.now_intake.toString()
                )
                resultTarget.text = String.format(
                    resources.getString(R.string.ml_template),
                    it.total_intake.toString()
                )
            }
        }
    }

    private fun selectOption() {
        val outValue = TypedValue()
        requireContext().theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue,
            true
        )
        binding.cardWater.apply {
            ic50ml.setOnClickListener {
                selectedOption = 50
                ic50ml.background = getDrawable(requireContext(), R.drawable.option_select_bg)
                ic100ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic150ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic200ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic250ml.background = getDrawable(requireContext(), outValue.resourceId)
                icCustomMl.background = getDrawable(requireContext(), outValue.resourceId)
            }
            ic100ml.setOnClickListener {
                selectedOption = 100
                ic50ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic100ml.background = getDrawable(requireContext(), R.drawable.option_select_bg)
                ic150ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic200ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic250ml.background = getDrawable(requireContext(), outValue.resourceId)
                icCustomMl.background = getDrawable(requireContext(), outValue.resourceId)
            }
            ic150ml.setOnClickListener {
                selectedOption = 150
                ic50ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic100ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic150ml.background = getDrawable(requireContext(), R.drawable.option_select_bg)
                ic200ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic250ml.background = getDrawable(requireContext(), outValue.resourceId)
                icCustomMl.background = getDrawable(requireContext(), outValue.resourceId)
            }
            ic200ml.setOnClickListener {
                selectedOption = 200
                ic50ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic100ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic150ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic200ml.background = getDrawable(requireContext(), R.drawable.option_select_bg)
                ic250ml.background = getDrawable(requireContext(), outValue.resourceId)
                icCustomMl.background = getDrawable(requireContext(), outValue.resourceId)
            }
            ic250ml.setOnClickListener {
                selectedOption = 250
                ic50ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic100ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic150ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic200ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic250ml.background = getDrawable(requireContext(), R.drawable.option_select_bg)
                icCustomMl.background = getDrawable(requireContext(), outValue.resourceId)
            }
            icCustomMl.setOnClickListener {
                showCustomDialog()
                ic50ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic100ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic150ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic200ml.background = getDrawable(requireContext(), outValue.resourceId)
                ic250ml.background = getDrawable(requireContext(), outValue.resourceId)
                icCustomMl.background = getDrawable(requireContext(), R.drawable.option_select_bg)
            }
        }
        binding.apply {
            fabAdd.setOnClickListener {
                if (selectedOption != null) {
                    viewModel.updateIntake(selectedOption.toString().toInt())
                    showSnackbar(requireView(), "Your water intake was saved!")
                    selectedOption = null
                    cardWater.tvCustomMl.text = resources.getString(R.string.custom)
                    cardWater.ic50ml.background = getDrawable(requireContext(), outValue.resourceId)
                    cardWater.ic100ml.background =
                        getDrawable(requireContext(), outValue.resourceId)
                    cardWater.ic150ml.background =
                        getDrawable(requireContext(), outValue.resourceId)
                    cardWater.ic200ml.background =
                        getDrawable(requireContext(), outValue.resourceId)
                    cardWater.ic250ml.background =
                        getDrawable(requireContext(), outValue.resourceId)
                    cardWater.icCustomMl.background =
                        getDrawable(requireContext(), outValue.resourceId)
                } else {
                    showSnackbar(requireView(), "Please select an option!")
                }
            }
        }
    }

    private fun showCustomDialog() {
        val content = LayoutInflater.from(
            requireContext()
        ).inflate(R.layout.custom_input_dialog, null)
        MaterialAlertDialogBuilder(requireContext()).setView(
            content
        ).setPositiveButton("OK") { dialog, id ->
            val userInput = content.findViewById<TextInputLayout>(R.id.etCustomInput)
            val inputText = userInput.editText!!.text.toString()
            if (!TextUtils.isEmpty(inputText)) {
                binding.cardWater.tvCustomMl.text =
                    String.format(resources.getString(R.string.ml_template), inputText)
                selectedOption = inputText.toInt()
            }
        }.setNegativeButton("CANCEL") { dialog, id ->
            selectedOption = null
            dialog.cancel()
        }.show()
    }

    private fun toBack() {
        binding.icBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}