package com.example.pitchcatalysttask.database

import android.util.Log
import com.example.pitchcatalysttask.constants.ITEM
import com.example.pitchcatalysttask.constants.SUCCESS
import com.example.pitchcatalysttask.dataModels.ItemModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import java.util.UUID


fun saveItemToDatabase(itemModel: ItemModel, callback: (String?) -> Unit) {
    itemModel.id = UUID.randomUUID().toString()
    Firebase.firestore.collection(ITEM).document(itemModel.id!!)
        .set(itemModel)
        .addOnSuccessListener {
            callback(SUCCESS)
        }
}

fun getAllItems(callback: (ArrayList<ItemModel>?) -> Unit) {
    val itemsList = ArrayList<ItemModel>()
    Firebase.firestore.collection(ITEM).get()
        .addOnSuccessListener {
            for (i in it.documents) {
                itemsList.add(i.toObject<ItemModel>()!!)
            }
            callback(itemsList)
        }
}

fun deleteItemById(id: String, callback: (String?) -> Unit) {
    Firebase.firestore.collection(ITEM).document(id)
        .delete()
        .addOnSuccessListener {
            callback(SUCCESS)
        }
        .addOnFailureListener { e -> Log.w("Deleting $id", "Error deleting document", e) }
}