package com.salman.gitsy.domain

import android.app.Application
import com.salman.gitsy.domain.database.GitsyDatabase
import com.salman.gitsy.domain.remote.GitsyApis
import com.salman.gitsy.domain.repo.GitsyRepository
import com.salman.gitsy.domain.repo.GitsyRepositoryImp
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

class GitsyApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@GitsyApp))

        // app service
        bind<GitsyApis>() with singleton { com.salman.gitsy.domain.remote.ServiceClient() }

        // initializing database
        bind() from singleton { GitsyDatabase(instance()) }
        bind() from singleton { instance<GitsyDatabase>().userDao() }

        // app repository initializing with userDao
        bind<GitsyRepository>() with singleton {
            GitsyRepositoryImp(instance(), instance())
        }

        // app view model factory
        bind() from singleton {
            ViewModelFactory(instance())
        }

    }

}