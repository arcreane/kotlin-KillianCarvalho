package com.example.fruitedmastermind

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // list of all fruits
    private val _all_fruits = listOf(
        Fruit("banana", false, true, R.drawable.banana),
        Fruit("grape", true, false, R.drawable.grape),
        Fruit("kiwi", false, true, R.drawable.kiwi),
        Fruit("lemon", true, true, R.drawable.lemon),
        Fruit("orange", true, true, R.drawable.orange),
        Fruit("prune", true, false, R.drawable.prune),
        Fruit("raspberry", false, false, R.drawable.raspberry),
        Fruit("strawberry", false, false, R.drawable.strawberry),
    )

    // combination of fruits to guess
    private val _combination_to_guess = MutableLiveData<List<Fruit>>()
    val combination_to_guess: LiveData<List<Fruit>> = _combination_to_guess

    // user's guesses
    private val _current_guess = MutableLiveData<List<Fruit>>()
    val current_guess: LiveData<List<Fruit>> = _current_guess

    // number of trials left
    private val _remaining_attempts = MutableLiveData<Int>()
    val remaining_attempts = _remaining_attempts

    // current score
    private val _score = MutableLiveData<Int>()
    val score = _score

    fun start_game(){
        _combination_to_guess.value = set_combination(_all_fruits)
        _remaining_attempts.value = 0
        _score.value = 0
    }

    fun make_guess(guess: List<Fruit>){
        // check the answer of the user
        var answer_check = check_combination(guess)

        // left one attempts to the user
        _remaining_attempts.value = (_remaining_attempts.value ?: 0) -1

        // if all the value is '1' the game is end and we ask to play again
        if (answer_check.all {it == '1'})
            start_game()
    }

    private fun set_combination(fruits: List<Fruit>): List<Fruit> {
        val combination = fruits.shuffled().take(4)

        return combination
    }
    fun check_combination(user_combination: List<Fruit>): MutableList<Char>
    {
        val result_proposition = mutableListOf<Char>()
        val correct_combination = _combination_to_guess.value ?: emptyList()

        // loop with index and fruit to check information more rapidly
        for ((index, fruit) in user_combination.withIndex())
        {
            if (fruit !in correct_combination)
                result_proposition.add('X')
            else
            {
                if (correct_combination[index] == fruit)
                    result_proposition.add('1')
                else
                    result_proposition.add('0')
            }
        }
        return result_proposition
    }
}