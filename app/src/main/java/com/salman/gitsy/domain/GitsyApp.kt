package com.salman.gitsy.domain

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

/**
 * Created by Salman Saifi on 17/04/22.
 * Contact at zach.salmansaifi@gmail.com.
 */

class GitsyApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@GitsyApp))

    }

}