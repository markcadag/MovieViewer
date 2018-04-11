package com.markcadag.movieviewer.ui.base

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.markcadag.movieviewer.R
import org.jetbrains.anko.alert

/**
 * Created by markcadag on 4/10/18.
 * Parent class of activity that contains help functions
 */
open class BaseActivity : AppCompatActivity() {

    private val errorAlertDialog: AlertDialog by lazy {
        alert(resources.getString(R.string.an_error_has_occured),
                resources.getString(R.string.something_went_wrong)).build()
    }

    private val dialog : AlertDialog by lazy {
        // runs on first access of messageView
        alert("Please wait a bit…", "Fetching data").build()
    }


    /**
     * Show a progress dialog
     */
    fun showProgressDialog() {
        dialog.let {
            if (!it.isShowing) it.show()
        }
    }

    /**
     * Dismiss progress dialog
     */
    fun dismissProgressDialog() {
        dialog.let {
            if (it.isShowing) it.hide()
        }
    }

    /**
     * Show error dialog
     */
    fun showErrorDialog() {
        errorAlertDialog.let {
            if (!it.isShowing) it.show()
        }
    }

    fun hideErrorDialog() {
        errorAlertDialog.let {
            if (it.isShowing) it.hide()
        }
    }
}