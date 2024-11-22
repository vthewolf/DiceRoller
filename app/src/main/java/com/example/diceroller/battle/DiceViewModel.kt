package com.example.diceroller.battle

import androidx.lifecycle.ViewModel
import com.example.diceroller.domain.model.Dice
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    private val _attackerRolls = MutableStateFlow<List<Int>>(emptyList())
    val attackerRolls: StateFlow<List<Int>> get() = _attackerRolls

    private val _defenderRolls = MutableStateFlow<List<Int>>(emptyList())
    val defenderRolls: StateFlow<List<Int>> get() = _defenderRolls

    private val _battleResult = MutableStateFlow(Pair(0, 0)) // attackerLosses, defenderLosses
    val battleResult: StateFlow<Pair<Int, Int>> get() = _battleResult

    fun startBattle(attackerDiceCount: Int, defenderDiceCount: Int) {
        val attackerRolls = rollDice(attackerDiceCount)
        val defenderRolls = rollDice(defenderDiceCount)

        _attackerRolls.value = attackerRolls
        _defenderRolls.value = defenderRolls

        val result = calculateLosses(attackerRolls, defenderRolls)
        _battleResult.update { result }
    }

    private fun rollDice(count: Int): List<Int> {
        return List(count) { Dice(6).roll() }.sortedDescending()
    }

    private fun calculateLosses(attackerRolls: List<Int>, defenderRolls: List<Int>): Pair<Int, Int> {
        var attackerResult = 0
        var defenderResult = 0

        for (i in 0 until minOf(attackerRolls.size, defenderRolls.size)) {
            if (attackerRolls[i] > defenderRolls[i]) {
                attackerResult++
            } else {
                defenderResult++
            }
        }

        return Pair(attackerResult, defenderResult)
    }
}
