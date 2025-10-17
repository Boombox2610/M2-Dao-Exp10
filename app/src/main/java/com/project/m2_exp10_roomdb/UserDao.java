package com.project.m2_exp10_roomdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(UserData userObject);

    @Query("SELECT * FROM UserData")
    LiveData<List<UserData>> getAllUsers();

    @Query("DELETE FROM UserData WHERE srNo = :srno")
    void delete(int srno);
}
