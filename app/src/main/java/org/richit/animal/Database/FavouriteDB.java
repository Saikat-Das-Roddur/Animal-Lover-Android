package org.richit.animal.Database;

import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;

import org.richit.animal.Models.AnimalModel;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class FavouriteDB {

    public static EasyDB easyDB;

    public static void init(Context context) {
        easyDB = EasyDB.init(context, "FavDb")
                .setTableName("DEMO TABLE")  // You can ignore this line if you want
                .addColumn(new Column("name", "text", "unique"))
                .addColumn(new Column("object", "text", "not null"))
                .doneTableColumn();
    }

    public static boolean addData(AnimalModel animal) {
        return easyDB.addData(1, animal.getName())
                .addData(2, new Gson().toJson(animal))
                .doneDataAdding();
    }

    public static ArrayList<AnimalModel> getData() {
        ArrayList<AnimalModel> animalModels = new ArrayList<>();
        Cursor res = easyDB.getAllData();
        while (res.moveToNext()) {
            animalModels.add(new Gson().fromJson(res.getString(2), AnimalModel.class));
        }
        return animalModels;

    }

    public static boolean deleteData(String name) {
        return easyDB.deleteRow(1, name);
    }

    public static boolean isFavourite(String name) {
        Cursor res = easyDB.searchInColumn(1, name, 1); // Here we passed limit = 1. Thus it will return only one row data with the matched column value
        return res != null;
    }

}
