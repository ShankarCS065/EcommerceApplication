package com.krashkrosh748199.shoption.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.SoldProduct
import com.krashkrosh748199.shoption.ui.adapters.SoldProductsListAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SoldProductsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sold_products, container, false)
    }

    override fun onResume() {
        super.onResume()

        getSoldProductsList()
    }

    private fun getSoldProductsList() {
        showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().getSoldProductsList(this@SoldProductsFragment)
    }

    fun successSoldProductsList(soldProductsList: ArrayList<SoldProduct>) {

        hideProgressDialog()

        val soldProductItem = view?.findViewById<RecyclerView>(R.id.rv_sold_product_items)
        val noSoldProductFound = view?.findViewById<TextView>(R.id.tv_no_sold_products_found)



        if (soldProductsList.size > 0) {
            soldProductItem?.visibility = View.VISIBLE
            noSoldProductFound?.visibility = View.GONE

            soldProductItem?.layoutManager = LinearLayoutManager(activity)
            soldProductItem?.setHasFixedSize(true)

            val soldProductsListAdapter =
                SoldProductsListAdapter(requireActivity(), soldProductsList)
            soldProductItem?.adapter = soldProductsListAdapter
        } else {
            soldProductItem?.visibility = View.GONE
            noSoldProductFound?.visibility = View.VISIBLE
        }

    }

}
