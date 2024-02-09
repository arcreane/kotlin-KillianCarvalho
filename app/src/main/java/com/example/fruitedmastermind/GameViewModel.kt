package com.example.fruitedmastermind

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // list of all fruits
    private val _allFruits = listOf(
        Fruit("banana", false, true, R.drawable.banana),
        Fruit("grape", true, false, R.drawable.grape),
        Fruit("kiwi", false, true, R.drawable.kiwi),
        Fruit("lemon", true, true, R.drawable.lemon),
        Fruit("orange", true, true, R.drawable.orange),
        Fruit("prune", true, false, R.drawable.prune),
        Fruit("raspberry", false, false, R.drawable.raspberry),
        Fruit("strawberry", false, false, R.drawable.strawberry),
    )
    val allFruits: List<Fruit> = _allFruits

    // combination of fruits to guess
    private val _combinationToGuess = MutableLiveData<List<Fruit>>()
    val combinationToGuess: LiveData<List<Fruit>> = _combinationToGuess

    // user's guesses
    private val _currentGuess = MutableLiveData<List<Fruit>>()
    val currentGuess: LiveData<List<Fruit>> = _currentGuess

    // number of trials left
    private val _remainingAttempts = MutableLiveData<Int>()
    val remainingAttempts = _remainingAttempts

    // current score
    private val _score = MutableLiveData<Int>()
    val score = _score

    // combination history
    private val _guessHistory = MutableLiveData<List<List<Fruit>>>(emptyList())
    val guessHistory = _guessHistory

    // combination result history
    private val _resultHistory = MutableLiveData<List<List<Char>>>(emptyList())
    val resultHistory = _resultHistory

    private val _win = MutableLiveData<Boolean>()
    val win = _win

    fun start_game(){
        _combinationToGuess.value = set_combination(_allFruits)
        _remainingAttempts.value = 10
        _score.value = 0
    }

    fun make_guess(guess: List<Fruit>){
        // check the answer of the user
        var answer_check = check_combination(guess)

        // keep the history
        _guessHistory.value = _guessHistory.value?.plus(listOf(guess))
        _resultHistory.value = _resultHistory.value?.plus(listOf(answer_check))

        // left one attempts to the user
        _remainingAttempts.value = (_remainingAttempts.value ?: 0) -1

        // if all the value is '1' the game is end and we ask to play again
        if (answer_check.all {it == '1'}){
            _score.value = _remainingAttempts.value
            _win.value = true
        }
    }

    // give hint link to the seeds variable
    fun giveFirstHint(): List<Boolean> {
        val combination = _combinationToGuess.value ?: emptyList()
        _remainingAttempts.value = _remainingAttempts.value!! - 2
        return combination.map { fruit ->
            if (fruit.hasSeeds) {
                true
            } else {
                false
            }
        }
    }

    // give hint link to the peelable variable
    fun giveSecondHint(): List<Boolean> {
        val combination = _combinationToGuess.value ?: emptyList()
        _remainingAttempts.value = _remainingAttempts.value!! - 3

        return combination.map { fruit ->
            if (fruit.isPeelable) {
                true
            } else {
                false
            }
        }
    }

    private fun set_combination(fruits: List<Fruit>): List<Fruit> {
        val combination = fruits.shuffled().take(4)

        return combination
    }
    fun check_combination(userCombination: List<Fruit>): MutableList<Char>
    {
        val resultProposition = mutableListOf<Char>()
        val correctCombination = _combinationToGuess.value ?: emptyList()

        // loop with index and fruit to check information more rapidly
        for ((index, fruit) in userCombination.withIndex())
        {
            if (fruit !in correctCombination)
                resultProposition.add('X')
            else
            {
                if (correctCombination[index] == fruit)
                    resultProposition.add('1')
                else
                    resultProposition.add('0')
            }
        }
        return resultProposition
    }

    fun resetGame() {
        _combinationToGuess.value = emptyList()
        _currentGuess.value = emptyList()
        _remainingAttempts.value = 10
        _score.value = 0
        _guessHistory.value = emptyList()
        _resultHistory.value = emptyList()
    }
}