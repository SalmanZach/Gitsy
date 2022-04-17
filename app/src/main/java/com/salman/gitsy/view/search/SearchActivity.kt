package com.salman.gitsy.view.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.salman.gitsy.R
import com.salman.gitsy.databinding.ActivitySearchBinding
import com.salman.gitsy.domain.remote.Envelope
import com.salman.gitsy.domain.remote.beans.UserBean
import com.salman.gitsy.utility.DebouncingTextListener
import com.salman.gitsy.utility.ItemActionListener
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

class SearchActivity : AppCompatActivity(), KodeinAware, ItemActionListener<UserBean> {

    private lateinit var binding: ActivitySearchBinding
    private val userAdapter = SearchAdapter()

    override val kodein by closestKodein()
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this, direct.instance())[SearchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)


        binding.run {

            userAdapter.apply {
                users.adapter = this
                setItemActionListener(this@SearchActivity)
            }

            searchInput.addTextChangedListener(DebouncingTextListener {
                if (!it.isNullOrEmpty()) {
                    viewModel.query(it.trim())
                } else {
                    userAdapter.submitList(null)
                }
            })
        }

        viewModel.users.observe(this) {
            when (it) {

                is Envelope.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }

                is Envelope.Error -> {
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }

                is Envelope.Success -> {
                    binding.progress.visibility = View.INVISIBLE
                    userAdapter.submitList(it.data)
                }
            }
        }
    }


    override fun onItemClicked(view: View, model: UserBean) {

    }
}