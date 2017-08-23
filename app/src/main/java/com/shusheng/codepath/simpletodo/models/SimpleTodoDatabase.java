package com.shusheng.codepath.simpletodo.models;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = SimpleTodoDatabase.NAME, version = SimpleTodoDatabase.VERSION)
public class SimpleTodoDatabase {

    public static final String NAME = "SimpleTodoDatabase";
    public static final int VERSION = 1;
}
