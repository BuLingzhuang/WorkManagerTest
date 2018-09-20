package com.intsig.scanner.workmanagertest.worker

import android.arch.persistence.room.Room
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import com.intsig.scanner.workmanagertest.database.HistoriesDatabase
import com.intsig.scanner.workmanagertest.entity.Histories

/**
 * Author：lingzhuang_bu
 * Date：2018/9/19
 * Description：
 */
class SaveWorker : Worker() {
    companion object {
        const val KEY_FORMULA = "SaveWorker_Formula"
        const val KEY_RESULT = "SaveWorker_Result"
        const val KEY_NEW_LIST = "SaveWorker_NewList"
        val TAG: String = SaveWorker::class.java.name
    }

    override fun doWork(): Result {
        val formula = inputData.getString(KEY_FORMULA)
        val result = inputData.getString(KEY_RESULT)
        return try {
            val db = Room.databaseBuilder(applicationContext, HistoriesDatabase::class.java,
                    "HistoriesDatabase").build()
            val historiesDao = db.getHistoriesDao()
            if (!formula.isNullOrEmpty() && !result.isNullOrEmpty()) {
                historiesDao.insertData(Histories(formula, result))
            }
            val all = historiesDao.getAll()
            val sb = StringBuilder()
            if (all.isNotEmpty()) {
                sb.append("历史纪录:").append("\n")
                all.forEach { sb.append(it.mId).append(". ").append(it.mFormula).append(it.mResult).append("\n") }
            }
            val build = Data.Builder().putString(KEY_NEW_LIST, sb.toString()).build()
            outputData = build
            Result.SUCCESS
        } catch (e: Exception) {
            Log.e(TAG, e.message)
            Result.FAILURE
        }
    }
}