package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener { rollDice() }

        // Do a dice roll when the app starts
        for (i in 0..4){
            rollDice()
        }
    }

    /**
     * Roll the dice and update the screen with the result
     */
    private fun rollDice() {
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

        val diceImage: ImageView = findViewById(R.id.redDice1)
        val diceImage2: ImageView = findViewById(R.id.redDice2)
        val diceImage3: ImageView = findViewById(R.id.redDice3)
        val diceImage4: ImageView = findViewById(R.id.blueDice1)
        val diceImage5: ImageView = findViewById(R.id.blueDice2)

        // Changes the image according to the number obtained
        diceImage.setImageResource(drawRedRes(diceRoll))
        diceImage2.setImageResource(drawRedRes(diceRoll2))
        diceImage3.setImageResource(drawRedRes(diceRoll3))
        diceImage4.setImageResource(drawBlueRes(diceRoll4))
        diceImage5.setImageResource(drawBlueRes(diceRoll5))

        diceImage.contentDescription = diceRoll.toString()
        diceImage2.contentDescription = diceRoll2.toString()
        diceImage3.contentDescription = diceRoll3.toString()
        diceImage4.contentDescription = diceRoll4.toString()
        diceImage5.contentDescription = diceRoll5.toString()
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
}

class Dice(private val numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}