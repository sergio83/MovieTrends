package com.billboard.movies.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.billboard.movies.R
import java.util.ArrayList
import java.util.HashMap

class SpinnerDropDownAdapter(val context: Context): BaseAdapter(), SpinnerAdapter {

    private var mConvertView: View? = null
    private var mList: List<String> = listOf()
    private var mSelectedIndex: Int? = null
    private var mSelections: HashMap<Int, Boolean>? = null


    constructor(ctx: Context, list: List<String>, selection: Int? = null): this(ctx){
        this.mList = ArrayList(list)
        this.mSelectedIndex = selection
    }

    constructor(ctx: Context, list: ArrayList<String>, selections: List<Int> = listOf()): this(ctx) {
        this.mList = ArrayList(list)

        mSelections = HashMap()

        for(i in 0..mList.size){
            mSelections!![i] = false
        }

        selections?.map{
            mSelections!![it] = true
        }

    }

    private fun multipleSelectionsEnabled(): Boolean {
        return mSelections != null
    }

    fun switchSelectedIndex(index: Int) {

        if (multipleSelectionsEnabled()) {
            val value = mSelections!![index] ?: false
            mSelections!![index] = !value
        } else {
            mSelectedIndex = index
        }
    }

    fun getSelectedIndex(): Int? {
        return mSelectedIndex
    }

    fun getSelectedIndexes(): List<Int> {

        if(mSelections != null){
            return mSelections!!.filterValues { s -> s }.let { it.keys.toList() }
        }else{
            return listOf()
        }
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(pos: Int): String {
        return mList[pos]
    }

    override fun getItemId(arg0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        if (convertView == null)
            this.mConvertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, null)
        else
            this.mConvertView = convertView

        var holder: Holder? = this.mConvertView!!.tag as Holder?

        if (holder == null) {
            holder = Holder()
            holder.bind(mConvertView!!)
            this.mConvertView!!.tag = holder
        }

        val item = mList[position]
        this.fillHolderUIValues(item, holder, position)
        return this.mConvertView!!
    }

    override fun getDropDownView(position: Int, convertView: View, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    protected fun fillHolderUIValues(str: String?, holder: Holder, position: Int) {
        holder.title!!.text = str ?: ""

        if (!multipleSelectionsEnabled()) {
            holder.image!!.visibility = if (position == mSelectedIndex) View.VISIBLE else View.INVISIBLE
        } else {
            holder.image!!.visibility = if (mSelections!![position] == true) View.VISIBLE else View.INVISIBLE
        }
    }

    class Holder {
        internal var title: TextView? = null
        internal var image: ImageView? = null

        fun bind(view: View){
            title = view.findViewById<View>(R.id.textView) as TextView
            image = view.findViewById<View>(R.id.checkImage) as ImageView
        }
    }
}