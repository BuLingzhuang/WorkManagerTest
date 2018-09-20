package com.intsig.scanner.workmanagertest.worker

import androidx.work.Data
import androidx.work.Worker

/**
 * Author：lingzhuang_bu
 * Date：2018/9/14
 * Description：
 */
class DivisionWorker :Worker(){

    companion object {
        const val KEY_FIRST = "F"
        const val KEY_SECOND = "S"
        const val KEY_RESULT = "RESULT_D"
    }

    override fun doWork(): Result {
        val first = inputData.getInt(KEY_FIRST, 0)
        val second = inputData.getInt(KEY_SECOND, 1)
        val result = first.toFloat() / second
        val data = Data.Builder().putFloat(KEY_RESULT, result).build()
        outputData = data
        return Result.SUCCESS
    }
}