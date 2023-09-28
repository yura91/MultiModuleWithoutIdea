package net.pst.cash.presentation


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import net.pst.cash.R
import net.pst.cash.domain.model.CountryModel
import java.util.Locale


class CountriesAdapter(context: Context, private val countries: List<CountryModel>) :
    ArrayAdapter<CountryModel>(context, R.layout.select_country_item, countries.toMutableList()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.select_country_item, parent, false)
            viewHolder = ViewHolder()
            viewHolder.flagImageView = view.findViewById(R.id.logoView)
            viewHolder.countryTextView = view.findViewById(R.id.countryName)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val countryItem = getItem(position)
        viewHolder.countryTextView.text = countryItem?.title

        return view!!;
    }

    override fun getFilter(): Filter {
        return countryFilter
    }

    private val countryFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            val suggestions: MutableList<CountryModel> = ArrayList()
            if (constraint.isEmpty()) {
                suggestions.addAll(countries)
            } else {
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in countries) {
                    if (item.title.lowercase().contains(filterPattern)) {
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
            (results.values as? List<CountryModel>)?.let { addAll(it) }
            notifyDataSetChanged()
        }
    }

    private class ViewHolder {
        lateinit var flagImageView: ImageView
        lateinit var countryTextView: TextView
    }
}

