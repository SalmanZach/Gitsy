package com.salman.gitsy.view.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.salman.gitsy.R
import com.salman.gitsy.databinding.ActivitySearchBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

class SearchActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivitySearchBinding

    override val kodein by closestKodein()
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this, direct.instance())[SearchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

    }
}