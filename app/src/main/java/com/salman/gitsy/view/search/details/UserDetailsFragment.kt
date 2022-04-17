package com.salman.gitsy.view.search.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.salman.gitsy.databinding.FragmentUserDetailsBinding
import com.salman.gitsy.domain.remote.Envelope
import com.salman.gitsy.view.search.SearchViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

class UserDetailsFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var mBinding: FragmentUserDetailsBinding
    private val viewModel: SearchViewModel by activityViewModels { direct.instance() }

    // getting username param from listing fragment
    private val args: UserDetailsFragmentArgs by navArgs()
    private val username: String by lazy { args.username }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentUserDetailsBinding.inflate(
            LayoutInflater.from(requireActivity()),
            container,
            false
        )
        mBinding.run {

            viewModel.user.observe(viewLifecycleOwner) {
                when (it) {
                    is Envelope.Loading -> {
                        progress.visibility = View.VISIBLE
                    }

                    is Envelope.Error -> {
                        progress.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    is Envelope.Success -> {
                        progress.visibility = View.GONE
                    }
                }
            }

            viewModel.loadUserInfoByUsername(username)

        }
        return mBinding.root
    }

}