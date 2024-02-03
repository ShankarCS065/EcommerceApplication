package com.krashkrosh748199.shoption.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.Address
import com.krashkrosh748199.shoption.ui.adapters.AddressListAdapter
import com.krashkrosh748199.shoption.utils.Constants
import com.krashkrosh748199.shoption.utils.MSPTextView
import com.krashkrosh748199.shoption.utils.SwipeToDeleteCallback
import com.krashkrosh748199.shoption.utils.SwipeToEditCallback

class AddressListActivity : BaseActivity() {
    private var mSelectAddress:Boolean =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_list)
        setupActionBar()

        findViewById<TextView>(R.id.tv_add_address).setOnClickListener {
            val intent = Intent(this@AddressListActivity, AddEditAddressActivity::class.java)
            startActivityForResult(intent, Constants.ADD_ADDRESS_REQUEST_CODE)
        }
        getAddressList()
        if(intent.hasExtra(Constants.EXTRA_ADDRESS_DETAILS)){
            mSelectAddress = intent.getBooleanExtra(Constants.EXTRA_ADDRESS_DETAILS,false)
        }
        //tv_title <- tv_title_Address_list
        if(mSelectAddress){
            findViewById<TextView>(R.id.tv_title).text=resources.getString(R.string.title_select_address)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode== Activity.RESULT_OK){
            getAddressList()
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(findViewById(R.id.toolbar_address_list_activity))

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_address_list_activity).setNavigationOnClickListener { onBackPressed() }
    }


    private fun getAddressList() {

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        FireStoreClass().getAddressesList(this@AddressListActivity)
    }

    fun successAddressListFromFirestore(addressList: ArrayList<Address>) {

        // Hide the progress dialog
        hideProgressDialog()

        if (addressList.size > 0) {

            findViewById<RecyclerView>(R.id.rv_address_list).visibility = View.VISIBLE
            findViewById<TextView>(R.id.tv_no_address_found).visibility = View.GONE

            findViewById<RecyclerView>(R.id.rv_address_list).layoutManager = LinearLayoutManager(this@AddressListActivity)
            findViewById<RecyclerView>(R.id.rv_address_list).setHasFixedSize(true)

            val addressAdapter = AddressListAdapter(this@AddressListActivity,addressList,mSelectAddress )
            findViewById<RecyclerView>(R.id.rv_address_list).adapter = addressAdapter

            if(!mSelectAddress){
                val editSwipeHandler = object : SwipeToEditCallback(this) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        val adapter = findViewById<RecyclerView>(R.id.rv_address_list).adapter as AddressListAdapter
                        adapter.notifyEditItem(
                            this@AddressListActivity,
                            viewHolder.adapterPosition
                        )

                    }
                }

                val editItemTouchHelper=ItemTouchHelper(editSwipeHandler)
                editItemTouchHelper.attachToRecyclerView(findViewById(R.id.rv_address_list))


                val deleteSwipeHandler = object : SwipeToDeleteCallback(this) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        showProgressDialog(resources.getString(R.string.please_wait))
                        addressList[viewHolder.adapterPosition].id?.let {
                            FireStoreClass().deleteAddress(
                                this@AddressListActivity,
                                it
                            )
                        }
                    }
                }
                val deleteItemTouchHelper = ItemTouchHelper(deleteSwipeHandler)
                deleteItemTouchHelper.attachToRecyclerView(findViewById(R.id.rv_address_list))
            }



        } else {
            findViewById<RecyclerView>(R.id.rv_address_list).visibility = View.GONE
            findViewById<TextView>(R.id.tv_no_address_found).visibility = View.VISIBLE
        }

    }
    fun deleteAddressSuccess() {

        // Hide progress dialog.
        hideProgressDialog()

        Toast.makeText(
            this@AddressListActivity,
            resources.getString(R.string.err_your_address_deleted_successfully),
            Toast.LENGTH_SHORT
        ).show()

        getAddressList()
    }

}