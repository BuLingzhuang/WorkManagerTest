package com.intsig.scanner.workmanagertest.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.intsig.scanner.workmanagertest.dao.HistoriesDao
import com.intsig.scanner.workmanagertest.entity.Histories

/**
 * Author：lingzhuang_bu
 * Date：2018/9/19
 * Description：
 */
@Database(entities = [(Histories::class)], version = 1)
abstract class HistoriesDatabase : RoomDatabase() {
     abstract fun getHistoriesDao(): HistoriesDao
}