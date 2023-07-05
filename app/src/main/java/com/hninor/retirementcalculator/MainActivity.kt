package com.hninor.retirementcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCenter.start(
            application, "d231f4a0-f044-4e56-9aba-bc2978a0211c",
            Analytics::class.java, Crashes::class.java
        )
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val interestEditText = findViewById<EditText>(R.id.interestEditText)
        val ageEditText = findViewById<EditText>(R.id.ageEditText)
        val retirementEditText = findViewById<EditText>(R.id.retirementEditText)
        val monthlySavingsEditText = findViewById<EditText>(R.id.monthlySavingsEditText)
        val currentEditText = findViewById<EditText>(R.id.currentEditText)
        calculateButton.setOnClickListener {
            //throw Exception("Something wrong here")
            try {
                val interestRate = interestEditText.text.toString().toFloat()
                val age = ageEditText.text.toString().toInt()
                val retirementAge = retirementEditText.text.toString().toInt()
                val montlySaving = monthlySavingsEditText.text.toString().toFloat()
                val current = currentEditText.text.toString().toFloat()

                val properties = HashMap<String, String>()
                properties.put("interestRate", interestRate.toString())
                properties.put("age", age.toString())
                properties.put("retirementAge", retirementAge.toString())
                properties.put("montlySaving", montlySaving.toString())
                properties.put("current", current.toString())
                if (interestRate <= 0) {
                    Analytics.trackEvent("wrong_interest_rate", properties)
                }

                if (retirementAge < age) {
                    Analytics.trackEvent("wrong_age", properties)
                }
            } catch (e: Exception) {
                Analytics.trackEvent(e.message)
            }
        }
    }
}