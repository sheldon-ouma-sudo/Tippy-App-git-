package com.example.tippy

import android.animation.ArgbEvaluator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     seekBarTip.progress = INITIAL_TIP_PERCENT
        tvpercentage.text = "$INITIAL_TIP_PERCENT%"
        updateTipDescription(INITIAL_TIP_PERCENT)
    seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        Log.i(TAG, "onProgressChanged $progress")
            tvpercentage.text = "$progress%"
            updateTipDescription(progress)


            computeTipAndTotal()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}

    })


        etBase.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
           Log.i(TAG,"afterTextChanged $s")
            computeTipAndTotal()

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })



    }

    private fun updateTipDescription(tipPercent: Int) {
      val tipDescription : String

       when(tipPercent){
           in 0..9 -> tipDescription = "Poor"
           in 10..14 -> tipDescription = "Acceptable"
           in 15..19 -> tipDescription = "Good"
           in 20..24 -> tipDescription = "Great"
           else -> tipDescription ="Amazing"

       }
        tvTipdescription.text = tipDescription
        val color = ArgbEvaluator().evaluate(tipPercent.toFloat() / seekBarTip.max,
            ContextCompat.getColor(this, R.color.worstTip),
            ContextCompat.getColor(this, R.color.bestTip)
        ) as Int
         tvTipdescription.setTextColor(color)
    }

    private fun computeTipAndTotal() {
        //Get the value of the  base and tip percentage
        if(etBase.text.isEmpty()){
            tvtipAmount.text = ""
            tvtotalAmount.text = ""
            return
        }
        val  baseAmount =etBase.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        val tipAmount = baseAmount * tipPercent/100
        val totalAmount = tipAmount + baseAmount
        tvtipAmount.text = "%.2f".format(tipAmount)
        tvtotalAmount.text = "%.2f".format(totalAmount)
    }
}
