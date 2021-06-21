package com.lhj.assignment.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.lhj.assignment.Data.DataClass

class DBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    val FAV_TABLE: String = "favorite"

    companion object {
        const val DESC = "DESC"
        const val ASC = "ASC"
    }


    override fun onCreate(db: SQLiteDatabase) {
        var sql_create_table: String = "CREATE TABLE if not exists " + FAV_TABLE + " (" +
                "id integer primary key," +
                "name text, thumbnail text, rate real, regTime text);";

        db.execSQL(sql_create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        var sql_drop_table: String = "DROP TABLE if exists " + FAV_TABLE

        db.execSQL(sql_drop_table)
        onCreate(db)
    }

    fun insertData(mData: DataClass.FavoriteData) {

        Log.e("asdfgg", "DB 삽입 : " + mData.name)

        val database = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put("id", mData.id)
        contentValues.put("name", mData.name)
        contentValues.put("thumbnail", mData.thumbnail)
        contentValues.put("rate", mData.rate)
        contentValues.put("regTime", mData.regTime)

        database.insert(FAV_TABLE, null, contentValues)
    }

    fun selectData(): List<DataClass.FavoriteData> {
        val resultData: MutableList<DataClass.FavoriteData> = mutableListOf()
        lateinit var data: DataClass.FavoriteData
        val database = this.readableDatabase
        var result: Cursor = database.query(FAV_TABLE, null, null, null, null, null, null)
        while (result.moveToNext()) {

            data = DataClass.FavoriteData(
                result.getInt(result.getColumnIndex("id")),
                result.getString(result.getColumnIndex("name")),
                result.getDouble(result.getColumnIndex("rate")),
                result.getString(result.getColumnIndex("thumbnail")),
                result.getString(result.getColumnIndex("regTime"))
            )

            resultData.add(result.position, data)
        }

        return resultData
    }

    fun checkData(id : Int): List<DataClass.FavoriteData> {
        val resultData: MutableList<DataClass.FavoriteData> = mutableListOf()
        lateinit var data: DataClass.FavoriteData
        val database = this.readableDatabase
        var result: Cursor = database.rawQuery("SELECT * FROM " + FAV_TABLE + " WHERE id == " + id,null)

        while (result.moveToNext()) {
            data = DataClass.FavoriteData(
                result.getInt(result.getColumnIndex("id")),
                result.getString(result.getColumnIndex("name")),
                result.getDouble(result.getColumnIndex("rate")),
                result.getString(result.getColumnIndex("thumbnail")),
                result.getString(result.getColumnIndex("regTime"))
            )
            resultData.add(result.position, data)
        }

        return resultData
    }

    fun selectData(orderBy: String, orderType: String): List<DataClass.FavoriteData> {
        val resultData: MutableList<DataClass.FavoriteData> = mutableListOf()
        lateinit var data: DataClass.FavoriteData
        val database = this.readableDatabase
        var result: Cursor =
            database.query(FAV_TABLE, null, null, null, null, null, orderBy + " " + orderType)
        while (result.moveToNext()) {

            data = DataClass.FavoriteData(
                result.getInt(result.getColumnIndex("id")),
                result.getString(result.getColumnIndex("name")),
                result.getDouble(result.getColumnIndex("rate")),
                result.getString(result.getColumnIndex("thumbnail")),
                result.getString(result.getColumnIndex("regTime"))
            )

            resultData.add(result.position, data)
        }

        return resultData
    }

    fun updateFavData(id: Int, isFav: String, regTime: String) {
        val database = this.writableDatabase
        database.execSQL("UPDATE " + FAV_TABLE + " SET regTime = " + "'" + regTime + "'" + " WHERE id == " + id)
    }

    fun deleteFavData(id: Int) {
        Log.e("asdfgg", "삭제??")
        val database = this.writableDatabase
        database.execSQL("DELETE FROM $FAV_TABLE WHERE id == $id")
    }

}