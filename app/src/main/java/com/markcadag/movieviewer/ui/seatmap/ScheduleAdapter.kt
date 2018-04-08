package com.markcadag.movieviewer.ui.seatmap

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.markcadag.movieviewer.R
import com.markcadag.movieviewer.model.Schedule

/**
 * Created by markcadag on 4/8/18.
 */
class ScheduleAdapter(context: Activity?, resourceId: Int, textViewId: Int) : ArrayAdapter<Schedule>(context, resourceId, textViewId) {

    private val inflater: LayoutInflater? = context?.layoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertedView = convertView

        if (convertedView == null) {
            convertedView = inflater?.inflate(R.layout.custom_dropdown_item, parent, false)
        }

        val txtTitle = convertedView?.findViewById(R.id.txt_title) as TextView

        val time = getItem(position)
        txtTitle.text = time.label

        return convertedView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertedView = convertView

        if (convertView == null) {
            convertedView = inflater?.inflate(R.layout.custom_dropdown_item, parent, false)
        }

        val txtTitle = convertedView?.findViewById(R.id.txt_title) as TextView

        val date = getItem(position)
        txtTitle.text = date.label

        return convertedView
    }
}