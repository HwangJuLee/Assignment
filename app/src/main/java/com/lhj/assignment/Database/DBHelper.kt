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

    val MAIN_TABLE: String = "main"

    override fun onCreate(db: SQLiteDatabase) {
        var sql_create_table: String = "CREATE TABLE if not exists " + MAIN_TABLE + " (" +
                "id integer primary key," +
                "name text, thumbnail text, imagePath text, subject text, price integer, rate real, favorites text);";


        db.execSQL(sql_create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        var sql_drop_table: String = "DROP TABLE if exists " + MAIN_TABLE

        db.execSQL(sql_drop_table)
        onCreate(db)
    }

    fun insertData(mData: DataClass.MainData) {

        Log.e("asdfgg", "DB 삽입 : " + mData.name)

        val database = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put("id", mData.id)
        contentValues.put("name", mData.name)
        contentValues.put("thumbnail", mData.thumbnail)
        contentValues.put("imagePath", mData.description.imagePath)
        contentValues.put("subject", mData.description.subject)
        contentValues.put("price", mData.description.price)
        contentValues.put("rate", mData.rate)
        contentValues.put("favorites", "N")

        database.insert(MAIN_TABLE, null, contentValues)
    }

    fun selectData(): List<DataClass.MainData> {
        val resultData: MutableList<DataClass.MainData> = mutableListOf()
        lateinit var data: DataClass.MainData
        val database = this.readableDatabase
        var result: Cursor = database.query(MAIN_TABLE, null, null, null, null, null, null)
        while (result.moveToNext()) {

            data = DataClass.MainData(
                result.getInt(result.getColumnIndex("id")),
                result.getString(result.getColumnIndex("name")),
                result.getDouble(result.getColumnIndex("rate")),
                result.getString(result.getColumnIndex("thumbnail")),
                DataClass.Description(
                    result.getString(result.getColumnIndex("imagePath")),
                    result.getString(result.getColumnIndex("subject")),
                    result.getInt(result.getColumnIndex("price"))
                ),
                result.getString(result.getColumnIndex("favorites"))
            )

            resultData.add(result.position, data)
        }

        return resultData
    }

}