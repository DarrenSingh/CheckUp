package com.Group6.checkup.Utils.Dao;

import android.content.Context;

import androidx.annotation.Nullable;

import com.Group6.checkup.Database.DatabaseHelper;

import java.util.List;

public abstract class Dao<T> {

    protected final DatabaseHelper db;

    public Dao(@Nullable Context context) {
        db = DatabaseHelper.getInstance(context);
    }

    public abstract boolean exists(String... searchId);

    public abstract T find(String... searchId);

    public abstract List<T> findAll();

    public abstract boolean insert(T object);

    public abstract boolean update(T object);

    public abstract boolean delete(String... searchId);

}
