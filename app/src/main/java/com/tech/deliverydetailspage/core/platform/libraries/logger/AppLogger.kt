package com.tech.deliverydetailspage.core.platform.libraries.logger

import com.github.ajalt.timberkt.Timber
import com.tech.deliverydetailspage.BuildConfig

class AppLogger {
    companion object {
        fun plantTree() {
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }
        }

        fun debug(message: String) {
            Timber.d { message }
        }

        fun error(message: String) {
            Timber.e { message }
        }

        fun info(message: String) {
            Timber.i { message }
        }

        fun warning(message: String) {
            Timber.w { message }
        }
    }
}
