package com.intsig.scanner.workmanagertest.worker

import androidx.work.Data
import androidx.work.Worker

/**
 * Author：lingzhuang_bu
 * Date：2018/9/13
 * Description：
 */
class SubtractWorker : Worker() {
    companion object {
        const val KEY_FIRST = "SubtractWorker_F"
        const val KEY_SECOND = "SubtractWorker_S"
        const val KEY_RESULT = "SubtractWorker_R"
    }

    override fun doWork(): Result {
        val first = inputData.getIntArray(KEY_FIRST)
        val second = inputData.getIntArray(KEY_SECOND)
        if (first != null && second != null) {
            val result = first[0] - second[0]
            val build = Data.Builder().putInt(KEY_RESULT, result).build()
            outputData = build
        }
        return Result.SUCCESS
    }
}