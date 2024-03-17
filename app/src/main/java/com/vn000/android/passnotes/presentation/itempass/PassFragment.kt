package com.vn000.android.passnotes.presentation.itempass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.vn000.android.passnotes.R
import com.vn000.android.passnotes.databinding.FragmentPassBinding
import com.vn000.android.passnotes.presentation.listpass.ListPassFragment
import com.vn000.android.passnotes.presentation.mode.PassItemState

class PassFragment : Fragment() {

    private var _binding: FragmentPassBinding? = null
    private val binding: FragmentPassBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentPassBinding ==Null")

    private lateinit var viewModel: PassItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PassItemViewModel::class.java]
        viewModel.isCloseScreen.observe(viewLifecycleOwner) {
            if (it) {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        val state = arguments?.getString(passKey).orEmpty()
        if (PassItemState.valueOf(state) == PassItemState.ADD_MODE) {
            launchADDMode()
        } else {
            launchWatchMode()
        }
    }

    private fun launchWatchMode() {
        val id = arguments?.getLong(passIdKey)
        viewModel.getPassItem(id ?: 0L)
        with(binding) {
            saveButton.isEnabled = false
            editTextFieldURL.isFocusable = false
            editTextFieldPassword.isFocusable = false
        }
        viewModel.passItem.observe(viewLifecycleOwner){
            with(binding) {
                editTextFieldPassword.setText(it.password)
                editTextFieldURL.setText(it.url)
                titleText.text = it.name
                imageView.load(it.iconUrl)
            }
        }
    }

    private fun launchADDMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addPassItem(
                binding.editTextFieldPassword.text?.toString(),
                binding.editTextFieldURL.text?.toString()
            )
        }
    }

    companion object {
        private const val passKey = "passKey"
        private const val passIdKey = "passIdKey"

        fun newInstance(passItemState: PassItemState, passItemId: Long?) = PassFragment().apply {
            arguments = bundleOf(
                passKey to passItemState.name, passIdKey to passItemId
            )
        }
    }
}