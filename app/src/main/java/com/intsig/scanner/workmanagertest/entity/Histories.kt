package com.intsig.scanner.workmanagertest.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Author：lingzhuang_bu
 * Date：2018/9/19
 * Description：
 */
@Entity
class Histories(formula: String?, result: String?) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "his_id")
    var mId: Int = 0

    @ColumnInfo(name = "his_formula")
    var mFormula: String? = formula

    @ColumnInfo(name = "his_result")
    var mResult: String? = result
}