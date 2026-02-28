package com.example.dummyjsonapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void upsert(CartItemEntity item);

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    CartItemEntity getByProductId(int productId);

    @Query("SELECT * FROM cart_items ORDER BY addedAt DESC")
    Flow<List<CartItemEntity>> getAllItems();
}
