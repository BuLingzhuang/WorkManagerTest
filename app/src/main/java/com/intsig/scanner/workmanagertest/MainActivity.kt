package com.intsig.scanner.workmanagertest

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.intsig.scanner.workmanagertest.util.RandomUtil
import com.intsig.scanner.workmanagertest.viewModel.CalculateViewModel
import com.intsig.scanner.workmanagertest.worker.AddWorker
import com.intsig.scanner.workmanagertest.worker.MultiWorker
import com.intsig.scanner.workmanagertest.worker.SaveWorker
import com.intsig.scanner.workmanagertest.worker.SubtractWorker
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * (a+b+c)*d-(e+f+g)*h=
     * [(a+b+c)*d-(e+f+g)*h]/2=
     */

    private var fNum: Int = 0
    private var sNum: Int = 0
    private var tNum: Int = 0

    private var fNum1: Int = 0
    private var sNum1: Int = 0
    private var tNum1: Int = 0

    private lateinit var mViewModel: CalculateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(CalculateViewModel::class.java)
        val contentObserver = Observer<String> {
            tv_content.text = it?.let { it } ?: "Error"
        }
        mViewModel.mCurrentContent.observe(this, contentObserver)
        val historiesObserver = Observer<String> {
            tv_histories.text = it?.let {
                it
            } ?: "Error"
        }
        mViewModel.mHistories.observe(this, historiesObserver)
        btn_random.setOnClickListener { random() }
        btn_calculate.setOnClickListener { doWork() }
        random()
        refreshHistories()
    }

    private fun refreshHistories() {
        save2DB(null, null)
    }

    private fun random() {
        fNum = RandomUtil.getRandom(100)
        sNum = RandomUtil.getRandom(100)
        tNum = RandomUtil.getRandom(100)
        fNum1 = RandomUtil.getRandom(100)
        sNum1 = RandomUtil.getRandom(100)
        tNum1 = RandomUtil.getRandom(100)
        mViewModel.mCurrentContent.value = getFormula()
    }

    private fun getFormula(): String {
        return "($fNum+$sNum+$tNum)*2-($fNum1+$sNum1+$tNum1)*3="
    }

    private fun doWork() {
        val combo0 = combo(fNum, sNum, tNum, true)
        val combo1 = combo(fNum1, sNum1, tNum1, false)
        val subtractRequest = OneTimeWorkRequest.Builder(SubtractWorker::class.java).setInputMerger(ArrayCreatingInputMerger::class.java).build()
        val calculateContinuation = WorkContinuation.combine(combo0, combo1).then(subtractRequest)

        val statusById = WorkManager.getInstance().getStatusById(subtractRequest.id)
        statusById.observe(this, Observer {
            if (it != null && it.state.isFinished) {
                val result = when (it.state) {
                    State.SUCCEEDED -> {
                        val result = it.outputData.getInt(SubtractWorker.KEY_RESULT, -1).toString()
                        val formula = getFormula()
                        save2DB(formula, result)
                        formula + result
                    }
                    State.CANCELLED -> "Cancelled"
                    else -> "Error"
                }
                mViewModel.mCurrentContent.value = result
            }
        })
        calculateContinuation.enqueue()
    }

    private fun save2DB(formula: String?, result: String?) {
        val data = Data.Builder().putString(SaveWorker.KEY_FORMULA, formula).putString(SaveWorker.KEY_RESULT, result).build()
        val request = OneTimeWorkRequest.Builder(SaveWorker::class.java).setInputData(data).build()
        val statusById = WorkManager.getInstance().getStatusById(request.id)
        statusById.observe(this, Observer {
            if (it != null && it.state.isFinished) {
                mViewModel.mHistories.value = when (it.state) {
                    State.SUCCEEDED -> it.outputData.getString(SaveWorker.KEY_NEW_LIST).toString()
                    State.CANCELLED -> "Cancelled"
                    else -> "Error"
                }
            }
        })
        WorkManager.getInstance().enqueue(request)
    }

    private fun combo(fNum: Int, sNum: Int, tNum: Int, isFirst: Boolean): WorkContinuation {
        val addFirstData = Data.Builder().putInt(AddWorker.KEY_FIRST, fNum).putInt(AddWorker.KEY_SECOND, sNum)
                .putInt(AddWorker.KEY_THIRD, tNum).putBoolean(AddWorker.KEY_1, isFirst).build()
        val addFirstRequest = OneTimeWorkRequest.Builder(AddWorker::class.java)
                .setInputData(addFirstData)
                .build()
        val doubleRequest = OneTimeWorkRequest.Builder(MultiWorker::class.java).build()
        return WorkManager.getInstance().beginWith(addFirstRequest).then(doubleRequest)
    }
}
