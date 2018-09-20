package com.intsig.scanner.workmanagertest.worker

import androidx.work.Data
import androidx.work.Worker

/**
 * Author：lingzhuang_bu
 * Date：2018/9/13
 * Description：
 */
class AddWorker : Worker() {
    companion object {
        const val KEY_FIRST = "AddWorker_F"
        const val KEY_SECOND = "AddWorker_S"
        const val KEY_THIRD = "AddWorker_T"
        const val KEY_1 = "AddWorker_1"
    }

    override fun doWork(): Result {
        val first = inputData.getInt(KEY_FIRST, 0)
        val second = inputData.getInt(KEY_SECOND, 0)
        val third = inputData.getInt(KEY_THIRD, 0)
        val isFirstWorker = inputData.getBoolean(KEY_1, true)
        val result = first + second + third
        val builder = Data.Builder()
        if (isFirstWorker) {
            builder.putInt(MultiWorker.KEY_MULTI, 2)
        } else {
            builder.putInt(MultiWorker.KEY_MULTI, 3)
        }
        val build = builder.putInt(MultiWorker.KEY_NUM, result).putBoolean(MultiWorker.KEY_FIRST, isFirstWorker).build()
        outputData = build
        return Result.SUCCESS
    }
}