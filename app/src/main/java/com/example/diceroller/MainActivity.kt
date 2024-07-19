package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.diceroller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            rollDice()
        }

        // Do a dice roll when the app starts
        for (i in 0..4){
            rollDice()
            hideFirstRollText()
        }
    }

    private fun hideFirstRollText() {
        binding.resultsTextView.visibility = View.INVISIBLE
        binding.resultsNumber.visibility = View.INVISIBLE
    }

    private fun rollDice() {
        val redDicesList = mutableListOf<Int>()
        val blueDicesList = mutableListOf<Int>()

        val dice = Dice(6)
        val dice2 = Dice(6)
        val dice3 = Dice(6)
        val dice4 = Dice(6)
        val dice5 = Dice(6)

        val diceRoll = dice.roll()
        val diceRoll2 = dice2.roll()
        val diceRoll3 = dice3.roll()
        val diceRoll4 = dice4.roll()
        val diceRoll5 = dice5.roll()

        redDicesList.add(diceRoll)
        redDicesList.add(diceRoll2)
        redDicesList.add(diceRoll3)
        blueDicesList.add(diceRoll4)
        blueDicesList.add(diceRoll5)

        // Changes the image according to the number obtained
        with(binding){
            redDice1.setImageResource(drawRedRes(diceRoll))
            redDice2.setImageResource(drawRedRes(diceRoll2))
            redDice3.setImageResource(drawRedRes(diceRoll3))
            blueDice1.setImageResource(drawBlueRes(diceRoll4))
            blueDice2.setImageResource(drawBlueRes(diceRoll5))

            redDice1.contentDescription = diceRoll.toString()
            redDice2.contentDescription = diceRoll2.toString()
            redDice3.contentDescription = diceRoll3.toString()
            blueDice1.contentDescription = diceRoll4.toString()
            blueDice2.contentDescription = diceRoll5.toString()
        }
        drawResults(redDicesList, blueDicesList)
    }

    private fun drawRedRes(roll: Int): Int {
        val drawableResource = when (roll) {
            1 -> R.drawable.red_1
            2 -> R.drawable.red_2
            3 -> R.drawable.red_3
            4 -> R.drawable.red_4
            5 -> R.drawable.red_5
            else -> R.drawable.red_6
        }
        return drawableResource
    }

    private fun drawBlueRes(roll: Int): Int {
        val drawableResource = when (roll) {
            1 -> R.drawable.blue_1
            2 -> R.drawable.blue_2
            3 -> R.drawable.blue_3
            4 -> R.drawable.blue_4
            5 -> R.drawable.blue_5
            else -> R.drawable.blue_6
        }
        return drawableResource
    }

    private fun drawResults(redDicesList: List<Int>, blueDicesList: List<Int>) {
        var redCount = 0
        var blueCount = 0
        val sortedRedList = redDicesList.sortedDescending()
        val sortedBlueList = blueDicesList.sortedDescending()

        val largestRed1 = sortedRedList.getOrNull(0) ?: Int.MIN_VALUE
        val largestRed2 = sortedRedList.getOrNull(1) ?: Int.MIN_VALUE
        val largestBlue1 = sortedBlueList.getOrNull(0) ?: Int.MIN_VALUE
        val largestBlue2 = sortedBlueList.getOrNull(1) ?: Int.MIN_VALUE

        if (largestBlue1 >= largestRed1){
            redCount--
        } else blueCount--

        if (largestBlue2 >= largestRed2){
            redCount--
        } else blueCount--

        with (binding) {
            resultsTextView.visibility = View.VISIBLE
            resultsNumber.visibility = View.VISIBLE
            defenderResult.text = blueCount.toString()
            attackerResult.text = redCount.toString()
        }
    }
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}