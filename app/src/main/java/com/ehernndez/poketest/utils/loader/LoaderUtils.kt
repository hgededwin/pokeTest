package com.ehernndez.poketest.utils.loader

import android.content.Context

open class LoaderUtils {
    companion object {
        private var loader: Loader? = null

        fun showLoader(context: Context) {
            try {
                hideLoader()
                loader = Loader(context)
                loader.let { customLoader ->
                    customLoader?.setCanceledOnTouchOutside(true)
                    customLoader?.show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun hideLoader() {
            if (loader != null && loader?.isShowing!!) {
                loader = try {
                    loader?.dismiss()
                    null
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }
    }
}