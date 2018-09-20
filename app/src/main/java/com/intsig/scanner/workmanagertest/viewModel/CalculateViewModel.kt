package com.intsig.scanner.workmanagertest.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.intsig.scanner.workmanagertest.entity.Histories

/**
 * Author：lingzhuang_bu
 * Date：2018/9/19
 * Description：
 */
class CalculateViewModel : ViewModel() {
    val mCurrentContent: MutableLiveData<String> = MutableLiveData()
    val mHistories: MutableLiveData<String> = MutableLiveData()
}