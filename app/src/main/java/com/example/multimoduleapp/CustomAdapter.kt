package com.example.multimoduleapp


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import java.util.Locale


class CustomAdapter(context: Context, private val countries: MutableList<String>) :
    ArrayAdapter<String>(context, R.layout.select_country_item, countries) {

    val initialCounries = countries.toList()

    override fun getCount(): Int {
        return countries.size
    }

    override fun getItem(position: Int): String? {
        return countries.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.select_country_item, parent, false)

        val countryNameTextView: TextView = view.findViewById(R.id.countryName);
//        ImageView countryFlagImageView = view.findViewById(R.id.country_flag);

        countryNameTextView.setText(countries.get(position));
//        countryFlagImageView.setImageResource(countryFlags.get(position));

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
                suggestions.addAll(initialCounries)
            } else {
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in initialCounries) {
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
