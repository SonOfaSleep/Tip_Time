package com.sonofasleep.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sonofasleep.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener { calculateTip() }

        binding.buttonPersonMinus.setOnClickListener { removePerson() }
        binding.buttonPersonPlus.setOnClickListener { addPerson() }
    }

    private fun removePerson() {
        val number = binding.numberOfPersonsEditText.text.toString().toIntOrNull() ?: 2
        if (number == 1 || number == 0) return

        binding.numberOfPersonsEditText.setText((number - 1).toString())
    }

    private fun addPerson() {
        val number = binding.numberOfPersonsEditText.text.toString().toIntOrNull() ?: 0

        binding.numberOfPersonsEditText.setText((number + 1).toString())
    }

    private fun calculateTip() {
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_fifteen_percent -> 0.15
            R.id.option_ten_percent -> 0.10
            else -> 0.07
        }


        val stringInEditPerson = binding.numberOfPersonsEditText.text.toString()
        val numberOfPersons = stringInEditPerson.toIntOrNull()
        when (numberOfPersons) {
            null -> {
                binding.tipResult.text = getString(R.string.pers_number_warning)
                return
            }
            0 -> {
                binding.tipResult.text = getString(R.string.pers_number_zero_warning)
                return
            }
        }

        var tip = tipPercentage * cost
        var tipPerPerson = tip / numberOfPersons!!
        var totalPerPerson = cost / numberOfPersons + tipPerPerson

        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
            totalPerPerson = kotlin.math.ceil(totalPerPerson)
            tipPerPerson = kotlin.math.ceil(tipPerPerson)
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        val formattedTotalPerPerson = NumberFormat.getCurrencyInstance().format(totalPerPerson)
        val formattedTipPerPerson = NumberFormat.getCurrencyInstance().format(tipPerPerson)

        binding.tipResult.text =
            getString(R.string.per_person, formattedTipPerPerson, formattedTotalPerPerson)
    }
}