package com.example.dummyjsonapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.example.dummyjsonapp.db.AppDatabase

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val cartDao = AppDatabase.getInstance(application).cartDao()
    val cartItems = cartDao.getAllItems().asLiveData()
}
