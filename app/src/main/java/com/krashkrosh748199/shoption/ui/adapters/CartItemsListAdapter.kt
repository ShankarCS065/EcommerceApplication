package com.krashkrosh748199.shoption.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.firestore.FireStoreClass
import com.krashkrosh748199.shoption.models.CartItem
import com.krashkrosh748199.shoption.ui.activities.CartListActivity
import com.krashkrosh748199.shoption.utils.Constants
import com.krashkrosh748199.shoption.utils.GlideLoader

class CartItemsListAdapter (
    private val context: Context,
    private var list: ArrayList<CartItem>,
    private val updateCartItems:Boolean
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_cart_layout,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            model.image?.let {
                GlideLoader(context).loadProductPicture(
                    it,
                    holder.itemView.findViewById(R.id.iv_cart_item_image)
                )
            }

            holder.itemView.findViewById<TextView>(R.id.tv_cart_item_title).text = model.title
            holder.itemView.findViewById<TextView>(R.id.tv_cart_item_price).text =
                "Rs ${model.price}"
            holder.itemView.findViewById<TextView>(R.id.tv_cart_quantity).text = model.cart_quantity

            if (model.cart_quantity == "0") {
                holder.itemView.findViewById<ImageButton>(R.id.ib_remove_cart_item).visibility =
                    View.GONE
                holder.itemView.findViewById<ImageButton>(R.id.ib_add_cart_item).visibility =
                    View.GONE

                if (updateCartItems){
                    holder.itemView.findViewById<ImageButton>(R.id.ib_delete_cart_item).visibility = View.VISIBLE
                }
                else{
                    holder.itemView.findViewById<ImageButton>(R.id.ib_delete_cart_item).visibility = View.GONE

                }

                holder.itemView.findViewById<TextView>(R.id.tv_cart_quantity).text =
                    context.resources.getString(R.string.lbl_out_of_stock)

                holder.itemView.findViewById<TextView>(R.id.tv_cart_quantity).setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSnackBarError
                    )
                )
            } else {
                if(updateCartItems){
                    holder.itemView.findViewById<ImageButton>(R.id.ib_remove_cart_item).visibility = View.VISIBLE
                    holder.itemView.findViewById<ImageButton>(R.id.ib_add_cart_item).visibility = View.VISIBLE
                    holder.itemView.findViewById<ImageButton>(R.id.ib_delete_cart_item).visibility = View.VISIBLE
                }
                else{
                    holder.itemView.findViewById<ImageButton>(R.id.ib_remove_cart_item).visibility = View.GONE
                    holder.itemView.findViewById<ImageButton>(R.id.ib_add_cart_item).visibility = View.GONE
                    holder.itemView.findViewById<ImageButton>(R.id.ib_delete_cart_item).visibility = View.GONE

                }

                holder.itemView.findViewById<TextView>(R.id.tv_cart_quantity).setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorSecondaryText
                    )
                )
            }

            holder.itemView.findViewById<ImageButton>(R.id.ib_remove_cart_item).setOnClickListener {
                if (model.cart_quantity == "1") {
                    model.id?.let { it1 -> FireStoreClass().removeItemFromCart(context, it1) }
                } else {

                    val cartQuantity: Int? = model.cart_quantity?.toInt()

                    val itemHashMap = HashMap<String, Any>()

                    if (cartQuantity != null) {
                        itemHashMap[Constants.CART_QUANTITY] = (cartQuantity - 1).toString()
                    }

                    // Show the progress dialog.

                    if (context is CartListActivity) {
                        context.showProgressDialog(context.resources.getString(R.string.please_wait))
                    }

                    model.id?.let { it1 ->
                        FireStoreClass().updateMyCart(
                            context,
                            it1, itemHashMap
                        )
                    }
                }

            }
            holder.itemView.findViewById<ImageButton>(R.id.ib_add_cart_item).setOnClickListener {

                val cartQuantity: Int? = model.cart_quantity?.toInt()

                if (cartQuantity != null) {
                    if (cartQuantity < model.stock_quantity?.toInt()!!) {

                        val itemHashMap = HashMap<String, Any>()

                        itemHashMap[Constants.CART_QUANTITY] = (cartQuantity + 1).toString()

                        // Show the progress dialog.
                        if (context is CartListActivity) {
                            context.showProgressDialog(context.resources.getString(R.string.please_wait))
                        }

                        model.id?.let { it1 ->
                            FireStoreClass().updateMyCart(
                                context,
                                it1, itemHashMap
                            )
                        }
                    } else {
                        if (context is CartListActivity) {
                            context.showErrorSnackBar(
                                context.resources.getString(
                                    R.string.msg_for_available_stock,
                                    model.stock_quantity
                                ),
                                true
                            )
                        }
                    }
                }

            }
            holder.itemView.findViewById<ImageButton>(R.id.ib_delete_cart_item).setOnClickListener {

                when (context) {
                    is CartListActivity -> {
                        context.showProgressDialog(context.resources.getString(R.string.please_wait))
                    }
                }

                model.id?.let { it1 -> FireStoreClass().removeItemFromCart(context, it1) }

            }
        }
    }

            override fun getItemCount(): Int {
                return list.size
            }

            private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
        }