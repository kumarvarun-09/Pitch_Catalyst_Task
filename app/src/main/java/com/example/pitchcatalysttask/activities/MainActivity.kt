package com.example.pitchcatalysttask.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pitchcatalysttask.R
import com.example.pitchcatalysttask.adapters.ItemRecyclerViewAdapter
import com.example.pitchcatalysttask.dataModels.ItemModel
import com.example.pitchcatalysttask.database.getAllItems
import com.example.pitchcatalysttask.database.saveItemToDatabase
import com.example.pitchcatalysttask.databinding.ActivityMainBinding
import com.example.pitchcatalysttask.databinding.CustomDialogToAddItemBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var adapter: ItemRecyclerViewAdapter
    private lateinit var itemList: ArrayList<ItemModel>
    private val itemModel by lazy { ItemModel(null, null, null) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        refreshScreen()
        binding.addItemBtn.setOnClickListener {
            showCustomDialogToAddItem()
        }

    }

    private fun showCustomDialogToAddItem() {
        val customDialog = Dialog(this@MainActivity)
        val dialogBinding = CustomDialogToAddItemBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        customDialog.setCanceledOnTouchOutside(false)
        dialogBinding.btnCancel.setOnClickListener {
            customDialog.dismiss()
        }
        dialogBinding.btnSave.setOnClickListener {
            itemModel.title = dialogBinding.titleEdt.editText!!.text.toString().trim()
            itemModel.body = dialogBinding.bodyDescEdt.editText!!.text.toString().trim()
            if (validateData(itemModel)) {
                saveItemToDatabase(itemModel)
                {
                    refreshScreen()
                    customDialog.dismiss()
                }
            }
        }
        customDialog.show()
    }

    private fun validateData(itemModel: ItemModel): Boolean {
        return (itemModel.title!!.isNotEmpty() && itemModel.body!!.isNotEmpty())
    }

    fun refreshScreen() {
        getAllItems {
            itemList = it!!
            adapter = ItemRecyclerViewAdapter(this@MainActivity, itemList)
            binding.itemRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            binding.itemRecyclerView.adapter = adapter
        }
    }
}