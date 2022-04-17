package com.salman.gitsy.view.search.listing

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.salman.gitsy.databinding.FragmentUserListBinding
import com.salman.gitsy.domain.database.entity.UserEntity
import com.salman.gitsy.domain.remote.Envelope
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
        mBinding.run {

            userAdapter.apply {
                users.adapter = this
                setItemActionListener(this@UserListFragment)
            }

            userAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    if (positionStart == 0) {
                        users.smoothScrollToPosition(0)
                    }
                }
            })

            searchInput.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val newText = s.toString().trim()
                    if (newText.isNotEmpty()) {
                        viewModel.query(newText)
                    } else {
                        userAdapter.submitList(null)
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }
            })

            viewModel.users.observe(viewLifecycleOwner) {
                when (it) {

                    is Envelope.Loading -> {
                        progress.visibility = View.VISIBLE
                    }

                    is Envelope.Error -> {
                        progress.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    is Envelope.Success -> {
                        progress.visibility = View.INVISIBLE
                        userAdapter.submitList(it.data)
                    }
                }
            }


        }
        return mBinding.root
    }


    override fun onItemClicked(view: View, model: UserEntity) {
        val direction =
            UserListFragmentDirections.actionUserListFragmentToUserDetailsFragment(model.username)
        findNavController().navigate(direction)
    }


}