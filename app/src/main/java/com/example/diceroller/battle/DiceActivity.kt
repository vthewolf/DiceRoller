package com.example.diceroller.battle

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.diceroller.R
import com.example.diceroller.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupListeners()

        viewModel.startBattle(3, 2) // Ejemplo: 3 dados atacante, 2 dados defensor
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.attackerRolls.collect { rolls ->
                displayDiceImages(rolls, isRed = true)
            }
        }

        lifecycleScope.launch {
            viewModel.defenderRolls.collect { rolls ->
                displayDiceImages(rolls, isRed = false)
            }
        }

        lifecycleScope.launch {
            viewModel.battleResult.collect { (attackerLosses, defenderLosses) ->
                displayResults(attackerLosses, defenderLosses)
            }
        }
    }

    private fun setupListeners() {
        binding.button.setOnClickListener {
            viewModel.startBattle(3, 2) // Repite batalla con 3 dados atacante, 2 dados defensor
        }
    }

    private fun displayDiceImages(rolls: List<Int>, isRed: Boolean) {
        val diceImages = rolls.map { getDiceDrawable(it, isRed) }
        if (isRed) {
            with(binding){
                redDice1.setImageResource(diceImages.getOrNull(0) ?: 0)
                redDice2.setImageResource(diceImages.getOrNull(1) ?: 0)
                redDice3.setImageResource(diceImages.getOrNull(2) ?: 0)
            }
        } else {
            with(binding){
                blueDice1.setImageResource(diceImages.getOrNull(0) ?: 0)
                blueDice2.setImageResource(diceImages.getOrNull(1) ?: 0)
            }
        }
    }

    private fun displayResults(attackerLosses: Int, defenderLosses: Int) {
        with(binding) {
            resultsTextView.visibility = View.VISIBLE
            resultsNumber.visibility = View.VISIBLE
            attackerResult.text = attackerLosses.toString()
            defenderResult.text = defenderLosses.toString()
        }
    }

    private fun getDiceDrawable(roll: Int, isRed: Boolean): Int {
        return if (isRed) {
            when (roll) {
                1 -> R.drawable.red_1
                2 -> R.drawable.red_2
                3 -> R.drawable.red_3
                4 -> R.drawable.red_4
                5 -> R.drawable.red_5
                else -> R.drawable.red_6
            }
        } else {
            when (roll) {
                1 -> R.drawable.blue_1
                2 -> R.drawable.blue_2
                3 -> R.drawable.blue_3
                4 -> R.drawable.blue_4
                5 -> R.drawable.blue_5
                else -> R.drawable.blue_6
            }
        }
    }
}
