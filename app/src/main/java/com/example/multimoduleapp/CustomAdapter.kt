package com.example.multimoduleapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class CustomAdapter(list: List<String>) : BaseAdapter() {

    private class ViewHolder {
        lateinit var flagImageView: ImageView
        lateinit var countryTextView: TextView
    }

    private var countries: List<String> = list
    override fun getCount(): Int {
        return countries.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var viewHolder = ViewHolder()

        if (view == null) {
            view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.select_country_item, parent, false)
            viewHolder.flagImageView = view.findViewById(R.id.logoView)
            viewHolder.countryTextView = view.findViewById(R.id.countryName)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        val country = countries[position]
        viewHolder.countryTextView.text = country

        return view!!
    }

}
