package com.example.multimoduleapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import java.util.Locale


class CustomAdapter(context: Context, private val countries: List<String>) :
    ArrayAdapter<String>(context, R.layout.select_country_item, countries.toMutableList()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.select_country_item, parent, false)

        val countryNameTextView: TextView = view.findViewById(R.id.countryName);
        countryNameTextView.setText(getItem(position));

        return view;
    }

    override fun getFilter(): Filter {
        return countryFilter
    }

    private val countryFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            val suggestions: MutableList<String> = ArrayList<String>()
            if (constraint.isNullOrEmpty()) {
                suggestions.addAll(countries)
            } else {
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in countries) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        suggestions.add(item)
                    }
                }
            }
            results.values = suggestions
            results.count = suggestions.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            clear()
            (results.values as? List<String>)?.let { addAll(it) }
            notifyDataSetChanged()
        }
    }
}
