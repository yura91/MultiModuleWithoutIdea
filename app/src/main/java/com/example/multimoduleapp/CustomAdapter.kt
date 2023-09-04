package com.example.multimoduleapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class CustomAdapter(list: List<String>) : BaseAdapter() {
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
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.select_country_item, null)
        val logo = view.findViewById<ImageView>(R.id.logoView)
        logo.setImageResource(R.drawable.icon_russia)
        val countryName = view.findViewById<TextView>(R.id.countryName)
        countryName.text = countries[position]
        return view
    }
}