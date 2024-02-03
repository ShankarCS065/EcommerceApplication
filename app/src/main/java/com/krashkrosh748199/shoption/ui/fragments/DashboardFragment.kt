package com.krashkrosh748199.shoption.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.Product
import com.krashkrosh748199.shoption.ui.activities.CartListActivity
import com.krashkrosh748199.shoption.ui.activities.SettingsActivity
import com.krashkrosh748199.shoption.ui.adapters.DashboardItemsListAdapter

class DashboardFragment : BaseFragment() {

    // private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //  dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId

        when(id){
            R.id.action_settings->{
                startActivity(Intent(activity,SettingsActivity::class.java))
                return true
            }
            R.id.action_cart->{
                startActivity(Intent(activity,CartListActivity::class.java))
                return true
            }

        }
        return super.onOptionsItemSelected(item)

    }

    override fun onResume() {
        super.onResume()
        getDashboardItemsList()
    }
    private fun getDashboardItemsList() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().getDashboardItemsList(this@DashboardFragment)
    }

    fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {
        hideProgressDialog()


        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_dashboard_items)
        val noProductsTextView = view?.findViewById<TextView>(R.id.tv_no_dashboard_items_found)

        if (dashboardItemsList.size > 0) {
            recyclerView?.visibility = View.VISIBLE
            noProductsTextView?.visibility = View.GONE

            recyclerView?.layoutManager = GridLayoutManager(activity,2)
            recyclerView?.setHasFixedSize(true)

            val adapterProducts = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
            recyclerView?.adapter = adapterProducts


        } else {
            recyclerView?.visibility = View.GONE
            noProductsTextView?.visibility = View.VISIBLE
        }
    }


}