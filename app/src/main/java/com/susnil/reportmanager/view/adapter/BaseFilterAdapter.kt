package com.susnil.reportmanager.view.adapter

import android.os.Handler
import android.text.TextUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.susnil.reportmanager.service.model.RestrictionType
import java.util.*

abstract class BaseFilterAdapter<T, S : RecyclerView.ViewHolder> : RecyclerView.Adapter<S>(), Filterable {

    val originalArrayList = ArrayList<T>()
    var list = ArrayList<T>()
        private set
    private var recyclerView: RecyclerView? = null
    var restrictionType: RestrictionType? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun setItems(arrayList: ArrayList<T>) {
        this.originalArrayList.clear()
        this.originalArrayList.addAll(arrayList)
        this.list.clear()
        this.list.addAll(arrayList)
        notifyDataSetChanged()
    }

    fun getListItem(position: Int): T? {
        return if (position > list.size) {
            null
        } else list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                list.clear()

                if (TextUtils.isEmpty(charSequence)) {
                    list.addAll(originalArrayList)
                } else {
                    val filteredList = ArrayList<T>()
                    for (`object` in originalArrayList) {
                        filterObject(filteredList, `object`, charSequence.toString(), restrictionType)
                    }
                    list = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = list
                return filterResults
            }


            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {

                Handler().postDelayed({
                    if (recyclerView != null) {
                        recyclerView!!.recycledViewPool.clear()
                    }
                    notifyDataSetChanged()
                }, 100)
            }
        }
    }

    protected abstract fun filterObject(
        filteredList: ArrayList<T>,
        `object`: T,
        searchText: String,
        restrictionType: RestrictionType?
    )

}