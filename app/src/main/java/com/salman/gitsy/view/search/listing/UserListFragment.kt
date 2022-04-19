package com.salman.gitsy.view.search.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.salman.gitsy.databinding.FragmentUserListBinding
import com.salman.gitsy.domain.database.entity.UserEntity
import com.salman.gitsy.domain.remote.Envelope
import com.salman.gitsy.utility.DebouncingTextListener
import com.salman.gitsy.utility.ItemActionListener
import com.salman.gitsy.view.search.SearchAdapter
import com.salman.gitsy.view.search.SearchViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

class UserListFragment : Fragment(), KodeinAware, ItemActionListener<UserEntity> {

    override val kodein by closestKodein()
    private lateinit var mBinding: FragmentUserListBinding
    private val viewModel: SearchViewModel by activityViewModels { direct.instance() }
    private val userAdapter = SearchAdapter()
    private val textWatcher = DebouncingTextListener {
        if (!it.isNullOrEmpty()) {
            viewModel.query(it.trim())
        } else {
            userAdapter.submitList(null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserListBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAdapter.apply {
            mBinding.users.adapter = this
            setItemActionListener(this@UserListFragment)
        }


        viewModel.users.observe(viewLifecycleOwner) {
            when (it) {
                is Envelope.Loading -> {
                    mBinding.progress.visibility = View.VISIBLE
                }

                is Envelope.Error -> {
                    mBinding.progress.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is Envelope.Success -> {
                    mBinding.progress.visibility = View.INVISIBLE
                    userAdapter.submitList(it.data)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.searchInput.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        mBinding.searchInput.removeTextChangedListener(textWatcher)

    }

    override fun onItemClicked(view: View, model: UserEntity) {
        val direction =
            UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(model.userName)
        findNavController().navigate(direction)
    }


}