package com.lhj.assignment.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
                "name text, thumbnail text, rate real, imagePath text, subject text, price integer , regTime text);";

        db.execSQL(sql_create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        var sql_drop_table: String = "DROP TABLE if exists " + FAV_TABLE

        db.execSQL(sql_drop_table)
        onCreate(db)
    }

    //즐겨찾기 DB 삽입
    fun insertData(mData: DataClass.MainData) {

        val database = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put("id", mData.id)
        contentValues.put("name", mData.name)
        contentValues.put("thumbnail", mData.thumbnail)
        contentValues.put("rate", mData.rate)
        contentValues.put("imagePath", mData.description.imagePath)
        contentValues.put("subject", mData.description.subject)
        contentValues.put("price", mData.description.price)
        contentValues.put("regTime", mData.regTime)

        database.insert(FAV_TABLE, null, contentValues)
    }

    //즐겨찾기 데이터 조회
    fun selectData(): List<DataClass.MainData> {
        val resultData: MutableList<DataClass.MainData> = mutableListOf()
        lateinit var data: DataClass.MainData
        val database = this.readableDatabase
        var result: Cursor = database.query(FAV_TABLE, null, null, null, null, null, null)
        while (result.moveToNext()) {

            data = DataClass.MainData(
                result.getInt(result.getColumnIndex("id")),
                result.getString(result.getColumnIndex("name")),
                result.getDouble(result.getColumnIndex("rate")),
                result.getString(result.getColumnIndex("thumbnail")),
                DataClass.Description(result.getString(result.getColumnIndex("imagePath")), result.getString(result.getColumnIndex("subject")), result.getInt(result.getColumnIndex("price"))),
                result.getString(result.getColumnIndex("regTime"))
            )

            resultData.add(result.position, data)
        }

        return resultData
    }

    //즐겨찾기 데이터 확인
    fun checkData(id: Int): List<DataClass.MainData> {
        val resultData: MutableList<DataClass.MainData> = mutableListOf()
        lateinit var data: DataClass.MainData
        val database = this.readableDatabase
        var result: Cursor =
            database.rawQuery("SELECT * FROM " + FAV_TABLE + " WHERE id == " + id, null)

        while (result.moveToNext()) {
            data = DataClass.MainData(
                result.getInt(result.getColumnIndex("id")),
                result.getString(result.getColumnIndex("name")),
                result.getDouble(result.getColumnIndex("rate")),
                result.getString(result.getColumnIndex("thumbnail")),
                DataClass.Description(result.getString(result.getColumnIndex("imagePath")), result.getString(result.getColumnIndex("subject")), result.getInt(result.getColumnIndex("price"))),
                result.getString(result.getColumnIndex("regTime"))
            )
            resultData.add(result.position, data)
        }

        return resultData
    }

    //즐겨찾기 데이터 정렬
    fun selectData(orderBy: String, orderType: String): List<DataClass.MainData> {
        val resultData: MutableList<DataClass.MainData> = mutableListOf()
        lateinit var data: DataClass.MainData
        val database = this.readableDatabase
        var result: Cursor =
            database.query(FAV_TABLE, null, null, null, null, null, orderBy + " " + orderType)
        while (result.moveToNext()) {

            data = DataClass.MainData(
                result.getInt(result.getColumnIndex("id")),
                result.getString(result.getColumnIndex("name")),
                result.getDouble(result.getColumnIndex("rate")),
                result.getString(result.getColumnIndex("thumbnail")),
                DataClass.Description(result.getString(result.getColumnIndex("imagePath")), result.getString(result.getColumnIndex("subject")), result.getInt(result.getColumnIndex("price"))),
                result.getString(result.getColumnIndex("regTime"))
            )

            resultData.add(result.position, data)
        }

        return resultData
    }

    //즐겨찾기 데이터 삭제
    fun deleteFavData(id: Int) {
        val database = this.writableDatabase
        database.execSQL("DELETE FROM $FAV_TABLE WHERE id == $id")
    }

}