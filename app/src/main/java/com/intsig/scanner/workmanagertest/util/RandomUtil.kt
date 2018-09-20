package com.intsig.scanner.workmanagertest.util

import java.util.*

/**
 * Author：lingzhuang_bu
 * Date：2018/9/17
 * Description：
 */
class RandomUtil {
    companion object {
        private val random = Random()
        fun getRandom(bound: Int): Int {
            return random.nextInt(bound)
        }
    }
}