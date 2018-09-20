package com.intsig.scanner.workmanagertest.dao

import android.arch.persistence.room.*
import com.intsig.scanner.workmanagertest.entity.Histories

/**
 * Author：lingzhuang_bu
 * Date：2018/9/19
 * Description：
 */
@Dao
interface HistoriesDao {
    @Query("select * from Histories")
    fun getAll(): List<Histories>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(vararg dataList: Histories)

    @Update
    fun update(vararg dataList: Histories)

    @Delete
    fun delete(vararg dataList: Histories)
}