package com.project.m2_exp10_roomdb;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserData {
    @PrimaryKey(autoGenerate = true)
    public int srNo;
    public String name;
    public String dateOfBirth;

    @NonNull
    @Override
    public String toString() {
        return "userData{" +
                "srNo=" + srNo +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
