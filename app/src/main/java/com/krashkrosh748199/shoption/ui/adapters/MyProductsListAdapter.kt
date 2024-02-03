package com.krashkrosh748199.shoption.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.krashkrosh748199.shoption.R
import com.krashkrosh748199.shoption.models.Product
import com.krashkrosh748199.shoption.ui.activities.ProductDetailsActivity
import com.krashkrosh748199.shoption.ui.fragments.ProductsFragment
import com.krashkrosh748199.shoption.utils.Constants
import com.krashkrosh748199.shoption.utils.GlideLoader

open class MyProductsListAdapter(
    private val context: Context,
    private var list: ArrayList<Product>,
    private val fragment:ProductsFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            model.image?.let { GlideLoader(context).loadProductPicture(it,holder.itemView.findViewById(R.id.iv_item_image)) }
            holder.itemView.findViewById<TextView>(R.id.tv_item_name).text = model.title
            holder.itemView.findViewById<TextView>(R.id.tv_item_price).text = "Rs${model.price}"

            holder.itemView.findViewById<ImageButton>(R.id.ib_delete_product).setOnClickListener {

                model.product_id?.let { it1 -> fragment.deleteProduct(it1) }
            }
            holder.itemView.setOnClickListener{
                val intent= Intent(context,ProductDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_PRODUCT_ID,model.product_id)
                intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID,model.user_id)
                context.startActivity(intent)

            }

        }
    }
    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

}