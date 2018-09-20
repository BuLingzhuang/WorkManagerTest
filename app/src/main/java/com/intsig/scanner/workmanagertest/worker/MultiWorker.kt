package com.intsig.scanner.workmanagertest.worker

import androidx.work.Data
import androidx.work.Worker

/**
 * Author：lingzhuang_bu
 * Date：2018/9/14
 * Description：
 */
class MultiWorker : Worker() {

    companion object {
        const val KEY_NUM = "MultiWorker_NUM"
        const val KEY_MULTI = "MultiWorker_MULTI"
        const val KEY_FIRST = "MultiWorker_First"
    }

    override fun doWork(): Result {
        val num = inputData.getInt(KEY_NUM, 0)
        val multi = inputData.getInt(KEY_MULTI, 0)
        val first = inputData.getBoolean(KEY_FIRST, true)
        val result = num * multi
        val key = if (first) {
            SubtractWorker.KEY_FIRST
        } else {
            SubtractWorker.KEY_SECOND
        }
        val build = Data.Builder().putInt(key, result).build()
        outputData = build
        return Result.SUCCESS
    }
}