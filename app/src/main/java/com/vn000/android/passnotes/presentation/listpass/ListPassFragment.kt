package com.vn000.android.passnotes.presentation.listpass

import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vn000.android.passnotes.R
import com.vn000.android.passnotes.databinding.FragmentListPassBinding
import com.vn000.android.passnotes.domain.PassItem
import com.vn000.android.passnotes.presentation.itempass.PassFragment
import com.vn000.android.passnotes.presentation.mode.PassItemState

class ListPassFragment : Fragment() {

    private var cancellationSignal: CancellationSignal? = null


    private lateinit var passListAdapter: PassListAdapter
    private lateinit var viewModel: PassListViewModel


    private var _binding: FragmentListPassBinding? = null
    private val binding: FragmentListPassBinding
        get() = _binding ?: throw java.lang.RuntimeException("FragmentListPassBinding == Null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[PassListViewModel::class.java]
        viewModel.passList.observe(viewLifecycleOwner) {
            passListAdapter.submitList(it)
        }
        binding.buttonAddPassItem.setOnClickListener {
            launchFragmentPassItem(PassItemState.ADD_MODE)
        }
    }

    private fun setupSwipeListener(rvPassList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = passListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deletePassItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvPassList)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setupRecyclerView() {
        with(binding.rvPassList) {
            passListAdapter = PassListAdapter()
            adapter = passListAdapter

            passListAdapter.onPassItemClickListener = {
                checkBiometrick(it)
            }

        }
        setupSwipeListener(binding.rvPassList)
    }

    private fun launchFragmentPassItem(state: PassItemState, passItemId: Long? = null) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_container, PassFragment.newInstance(state, passItemId))
            ?.addToBackStack("PassFragment")
            ?.commit()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkBiometrick(it: PassItem) {
        val authenticationCalback: BiometricPrompt.AuthenticationCallback =
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("В доступе отказано")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Доступ разрешен")
                    launchFragmentPassItem(PassItemState.WATCH_MODE, it.id)
                }
            }
        val biometricPrompt = activity?.let { it1 ->
            BiometricPrompt.Builder(activity)
                .setTitle("Manager passwords")
                .setSubtitle("Проверка")
                .setDescription("Отсканируйте свой отпечаток пальца")
                .setNegativeButton("Отмена", it1.mainExecutor,
                    DialogInterface.OnClickListener { dialog, whitch ->
                        notifyUser("Аутентификация отменена")
                    }
                ).build()
        }
        activity?.mainExecutor?.let { it1 ->
            biometricPrompt?.authenticate(
                getCancellationSignal(),
                it1, authenticationCalback
            )
        }

    }

    private fun notifyUser(massage: String) {
        val toast = Toast.makeText(activity, massage, Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Аутентификация было отменена пользователем")
        }
        return cancellationSignal as CancellationSignal
    }

    companion object {
        fun newInstance() = ListPassFragment()
    }
}