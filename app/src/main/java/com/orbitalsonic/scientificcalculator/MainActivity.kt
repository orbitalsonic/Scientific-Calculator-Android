package com.orbitalsonic.scientificcalculator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.orbitalsonic.scientificcalculator.ScreenUtils.getScreenHeight
import com.orbitalsonic.scientificcalculator.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.text.DecimalFormat
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var isScientificCalculator = false
    private var isSecondEnable = true
    private var isDegreeEnable = true
    private var scriptEngine: ScriptEngine? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        scriptEngine = ScriptEngineManager().getEngineByName("rhino")

        setScreen(0.40)

    }


    fun onClickMethod(view: View) {
        when (view.id) {

            // Numbers
            R.id.btnDot -> {
                if (!binding.tvInputCalculation.text.toString().contains(".")) {
                    addTextCalculate(".")
                }
            }
            R.id.btnZero -> addTextCalculate("0")
            R.id.btnOne -> addTextCalculate("1")
            R.id.btnTwo -> addTextCalculate("2")
            R.id.btnThree -> addTextCalculate("3")
            R.id.btnFour -> addTextCalculate("4")
            R.id.btnFive -> addTextCalculate("5")
            R.id.btnSix -> addTextCalculate("6")
            R.id.btnSeven -> addTextCalculate("7")
            R.id.btnEight -> addTextCalculate("8")
            R.id.btnNine -> addTextCalculate("9")

            // Scientific
            R.id.btnSecond -> {
                changingSecond()
            }
            R.id.btnDegree -> {
                changingDegree()
            }
            R.id.btnSin -> {
                if (isSecondEnable) {
                    addTextCalculate("sin(")
                } else {
                    addTextCalculate("arcsin(")
                }

            }
            R.id.btnCos -> {
                if (isSecondEnable) {
                    addTextCalculate("cos(")
                } else {
                    addTextCalculate("arccos(")
                }

            }
            R.id.btnTan -> {
                if (isSecondEnable) {
                    addTextCalculate("tan(")
                } else {
                    addTextCalculate("arctan(")
                }

            }

            R.id.btnPower -> {
                addTextCalculate("^(")
            }
            R.id.btnLog -> {
                addTextCalculate("lg(")
            }
            R.id.btnNaturalLog -> {
                addTextCalculate("ln(")
            }
            R.id.btnSquareRoot -> {
                addTextCalculate("\u221a(")
            }
            R.id.btnMode -> {
                addTextCalculate("abs(")
            }
            R.id.btnExponent1, R.id.btnExponent2 -> {
                addTextCalculate("e")
            }
            R.id.btnMultiplicativeInverse -> {
                addTextCalculate("^(-1)")
            }
            R.id.btnPi -> {
                addTextCalculate("\u03c0")
            }
            R.id.btnParenthesisStart -> {
                addTextCalculate("(")
            }
            R.id.btnParenthesisClose -> {
                addTextCalculate(")")
            }

            // Operations

            R.id.btnAllClear -> clearTextAll()
            R.id.btnBackClear -> cleatTextLast()
            R.id.btnChangeCalculator1, R.id.btnChangeCalculator2 -> {
                if (isScientificCalculator) {
                    isScientificCalculator = false
                    setScreen(0.40)
                    setVisibilityScientific()

                } else {
                    isScientificCalculator = true
                    setScreen(0.30)
                    setVisibilityScientific()

                }
            }

            R.id.btnEqual -> equalClicked()
            R.id.btnPercentage -> {
                if (binding.tvInputCalculation.text.toString().isNotEmpty())
                    calculate(binding.tvInputCalculation.text.toString() + "%")
            }
            R.id.btnDivision -> {
                addOperands("÷")
            }
            R.id.btnMultiplication -> {
                addOperands("x")
            }
            R.id.btnSubtraction -> {
                addOperands("-")
            }
            R.id.btnAddition -> {
                addOperands("+")
            }

        }
    }

    private fun setScreen(percentage: Double) {
        binding.topLayout.requestLayout()
        binding.topLayout.layoutParams.height = (getScreenHeight() * percentage).toInt()
    }

    @SuppressLint("SetTextI18n")
    private fun addOperands(operands: String) {
        val mText = binding.tvInputCalculation.text.toString()
        if (mText.isEmpty()) {
            addTextCalculate("0${operands}")
        } else {
            if (isOperands(mText.last().toString())) {
                binding.tvInputCalculation.text = "${mText.dropLast(1)}$operands"
            } else {
                addTextCalculate(operands)
            }

        }


    }

    private fun isOperands(operands: String): Boolean {
        return operands == "+" || operands == "-" || operands == "x" || operands == "÷"
    }

    @SuppressLint("SetTextI18n")
    private fun addTextCalculate(mData: String) {
        val mText = binding.tvInputCalculation.text.toString()
        binding.tvInputCalculation.text = "$mText$mData"

    }

    private fun clearTextAll() {
        binding.tvInputCalculation.text = ""
        binding.tvEqualCalculation.text = ""
    }

    private fun cleatTextLast() {
        val mText = binding.tvInputCalculation.text.toString()
        if (mText.isNotEmpty()) {
            binding.tvInputCalculation.text = mText.dropLast(1)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun equalClicked() {
        if (binding.tvInputCalculation.text.toString().isNotEmpty()) {
            calculate(binding.tvInputCalculation.text.toString())
        }

    }

    private fun setVisibilityScientific() {
        if (isScientificCalculator) {
            binding.btnSecond.visibility = View.VISIBLE
            binding.btnDegree.visibility = View.VISIBLE
            binding.btnSin.visibility = View.VISIBLE
            binding.btnCos.visibility = View.VISIBLE
            binding.btnTan.visibility = View.VISIBLE
            binding.btnPower.visibility = View.VISIBLE
            binding.btnLog.visibility = View.VISIBLE
            binding.btnNaturalLog.visibility = View.VISIBLE
            binding.btnParenthesisStart.visibility = View.VISIBLE
            binding.btnParenthesisClose.visibility = View.VISIBLE
            binding.btnSquareRoot.visibility = View.VISIBLE
            binding.btnMode.visibility = View.VISIBLE
            binding.btnMultiplicativeInverse.visibility = View.VISIBLE
            binding.btnPi.visibility = View.VISIBLE
            binding.btnChangeCalculatorLayout.visibility = View.VISIBLE
            binding.btnChangeCalculator1.visibility = View.VISIBLE
            binding.btnChangeCalculator2.visibility = View.GONE
            binding.btnExponent1.visibility = View.GONE
            binding.btnExponent2.visibility = View.VISIBLE
        } else {
            binding.btnSecond.visibility = View.GONE
            binding.btnDegree.visibility = View.GONE
            binding.btnSin.visibility = View.GONE
            binding.btnCos.visibility = View.GONE
            binding.btnTan.visibility = View.GONE
            binding.btnPower.visibility = View.GONE
            binding.btnLog.visibility = View.GONE
            binding.btnNaturalLog.visibility = View.GONE
            binding.btnParenthesisStart.visibility = View.GONE
            binding.btnParenthesisClose.visibility = View.GONE
            binding.btnSquareRoot.visibility = View.GONE
            binding.btnMode.visibility = View.GONE
            binding.btnMultiplicativeInverse.visibility = View.GONE
            binding.btnPi.visibility = View.GONE
            binding.btnChangeCalculatorLayout.visibility = View.GONE
            binding.btnChangeCalculator1.visibility = View.GONE
            binding.btnChangeCalculator2.visibility = View.VISIBLE
            binding.btnExponent1.visibility = View.VISIBLE
            binding.btnExponent2.visibility = View.GONE
        }
    }


    @SuppressLint("SetTextI18n")
    private fun calculate(input: String) {

        val indexesList: List<Int>
        var tempData = ""
        val originalList = "1*($input)"

        var temp: String
        var result: String
        try {
            temp = originalList

            indexesList = originalList.indexesOf("^", false)


            for (index in indexesList.indices) {
                for (i in indexesList[index] - 1 downTo 0) {
                    if (!isDigit(originalList[i])) {
                        Log.i("information", "Start index: $i")
                        Log.i("information", "End index: ${indexesList[index]}")
                        Log.i("information", originalList.substring(i + 1, indexesList[index] + 2))

                        tempData = originalList.substring(i + 1, indexesList[index])
                        temp = temp.replace(
                            "${tempData}\\^\\(".toRegex(),
                            "Math.pow(${originalList.substring(i + 1, indexesList[index])},"
                        )

                        break
                    }
                }

            }

            result = scriptEngine?.eval(
                temp.replace("%".toRegex(), "/100")
                    .replace("x".toRegex(), "*")
                    .replace("÷".toRegex(), "/")
                    .replace("sin\\(".toRegex(), "Math.sin(")
                    .replace("cos\\(".toRegex(), "")
                    .replace("tan\\(".toRegex(), "Math.Math.cos(tan(")
                    .replace("arcsin\\(".toRegex(), "Math.sin(")
                    .replace("arccos\\(".toRegex(), "Math.cos(")
                    .replace("arctan\\(".toRegex(), "Math.tan(")
                    .replace("abs\\(".toRegex(), "Math.abs(")
                    .replace("lg\\(".toRegex(), "Math.log10(")
                    .replace("ln\\(".toRegex(), "Math.log(")
                    .replace("\\u221a\\(".toRegex(), "Math.sqrt(")
                    .replace("\\u03c0".toRegex(), "Math.PI")
                    .replace("e".toRegex(), "Math.E")
            ).toString()


            Log.i("information", "Result: $result")
            val decimal = BigDecimal(result)
            result = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString()
        } catch (e: Exception) {
            e.printStackTrace()
            binding.tvEqualCalculation.text = "= Wrong Format"
            Log.i("information", e.toString())
            return
        }
        if (result == "Infinity") {
            binding.tvEqualCalculation.text = "= Can't divide by zero"

        } else if (result.contains(".")) {
            result = result.replace("\\.?0*$".toRegex(), "")
            if (result.length > 18) {
                result = handlingLengthyResult(result)
                binding.tvEqualCalculation.text = "= $result"
            } else {
                binding.tvEqualCalculation.text = "= $result"
            }

        }
    }

    private fun handlingLengthyResult(number: String): String {
        val d = BigDecimal(number)
        val df = DecimalFormat("0.###########E0")
        return df.format(d)
    }

    private fun changingSecond() {
        if (isSecondEnable) {
            isSecondEnable = false
            binding.btnSin.text = resources.getText(R.string.arcsin)
            binding.btnCos.text = resources.getText(R.string.arccos)
            binding.btnTan.text = resources.getText(R.string.arctan)
            binding.btnDegree.isEnabled = false

        } else {
            isSecondEnable = true
            binding.btnSin.text = resources.getText(R.string.sin)
            binding.btnCos.text = resources.getText(R.string.cos)
            binding.btnTan.text = resources.getText(R.string.tan)
            binding.btnDegree.isEnabled = true

        }
    }

    @SuppressLint("SetTextI18n")
    private fun changingDegree() {
        if (isDegreeEnable) {
            isDegreeEnable = false
            binding.btnSecond.isEnabled = false
            binding.btnDegree.text = "rad"

        } else {
            isDegreeEnable = true
            binding.btnDegree.text = "deg"
            binding.btnSecond.isEnabled = true
        }
    }

    private fun String?.indexesOf(substr: String, ignoreCase: Boolean = false): List<Int> {
        return this?.let {
            val indexes = mutableListOf<Int>()
            var startIndex = 0
            while (startIndex in 0 until length) {
                val index = this.indexOf(substr, startIndex, ignoreCase)
                startIndex = if (index != -1) {
                    indexes.add(index)
                    index + substr.length
                } else {
                    index
                }
            }
            return indexes
        } ?: emptyList()
    }

    private fun isDigit(ch: Char): Boolean {
        return Character.isDigit(ch)
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
