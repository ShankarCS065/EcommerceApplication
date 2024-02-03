package com.krashkrosh748199.shoption.ui.fragments


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.Product
import com.krashkrosh748199.shoption.ui.activities.AddProductActivity
import com.krashkrosh748199.shoption.ui.adapters.MyProductsListAdapter

class ProductsFragment : BaseFragment() {

    //private lateinit var homeViewModel: HomeViewModel
// private lateinit var mRootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //  homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_products, container, false)

        return root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId

       if(id==R.id.action_add_product)
          {
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true

        }
        return super.onOptionsItemSelected(item)

    }
    override fun onResume() {
        super.onResume()

        getProductListFromFireStore()
    }

    private fun getProductListFromFireStore() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of FireStore class.
        FireStoreClass().getProductsList(this@ProductsFragment)
    }

    fun successProductsListFromFireStore(productsList: ArrayList<Product>) {
        hideProgressDialog()

        val recyclerView = view?.findViewById<RecyclerView>(R.id.rv_my_product_items)
        val noProductsTextView = view?.findViewById<TextView>(R.id.tv_no_products_found)

        if (productsList.size > 0) {
            recyclerView?.visibility = View.VISIBLE
            noProductsTextView?.visibility = View.GONE

            recyclerView?.layoutManager = LinearLayoutManager(requireContext())
            recyclerView?.setHasFixedSize(true)

            val adapterProducts = MyProductsListAdapter(requireActivity(), productsList,this@ProductsFragment)
            recyclerView?.adapter = adapterProducts
        } else {
            recyclerView?.visibility = View.GONE
            noProductsTextView?.visibility = View.VISIBLE
        }
    }

    fun deleteProduct(productID: String) {

        showAlertDialogToDeleteProduct(productID)
    }

    fun productDeleteSuccess() {

        // Hide the progress dialog
        hideProgressDialog()

        Toast.makeText(
            requireActivity(),
            resources.getString(R.string.product_delete_success_message),
            Toast.LENGTH_SHORT
        ).show()

        // Get the latest products list from cloud firestore.
        getProductListFromFireStore()
    }

    private fun showAlertDialogToDeleteProduct(productID: String) {

        val builder = AlertDialog.Builder(requireActivity())
        //set title for alert dialog
        builder.setTitle(resources.getString(R.string.delete_dialog_title))
        //set message for alert dialog
        builder.setMessage(resources.getString(R.string.delete_dialog_message))
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, _ ->

            showProgressDialog(resources.getString(R.string.please_wait))

            // Call the function of FireStore class.
            FireStoreClass().deleteProduct(this@ProductsFragment, productID)

            dialogInterface.dismiss()
        }

        //performing negative action
        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _ ->

            dialogInterface.dismiss()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }



}