package com.example.pitchcatalysttask.adapters

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pitchcatalysttask.dataModels.ItemModel
import com.example.pitchcatalysttask.database.deleteItemById
import com.example.pitchcatalysttask.databinding.ItemRvBinding

class ItemRecyclerViewAdapter(
    private val context: Context,
    private val itemList: ArrayList<ItemModel>
) :
    RecyclerView.Adapter<ItemRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.titleTV.text = itemList[position].title
        holder.binding.bodyTv.text = itemList[position].body
        holder.binding.deleteCheckBox.isChecked = false
        holder.binding.deleteCheckBox.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("Delete ${itemList[position].title}?")
            dialogBuilder.setCancelable(false)
            dialogBuilder.setPositiveButton("Yes") { dialog, which ->
                deleteItemById(itemList[position].id!!)
                {
                    itemList.removeAt(position)
                    notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
            dialogBuilder.setNegativeButton("No") { dialog, which ->
                holder.binding.deleteCheckBox.isChecked = false
                dialog.dismiss()
            }
            dialogBuilder.create().show()
        }
    }
}