package com.alex.quizmatematica

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.min

class GameViewModel: ViewModel() {

    data class Question(
        val text: String,
        val answers: List<String>)

    //Lista de questões e respostas - A primeira resposta sempre é a certa
    val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "1 X 7",
            answers = listOf("7", "8", "9", "0")
        ),
        Question(
            text = "2 X 3",
            answers = listOf("6", "3", "11", "9")
        ),
        Question(
            text = "5 X 4",
            answers = listOf("20", "35", "25", "15")
        ),
        Question(
            text = "4 X 3",
            answers = listOf("12", "13", "11", "14")
        ),
        Question(
            text = "7 X 8",
            answers = listOf("56", "55", "57", "59")
        ))

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>

    private val _questionIndex = MutableLiveData<Int>()
    val questionIndex: LiveData<Int>
        get() = _questionIndex

    private val _endGame = MutableLiveData<Boolean>()
    val endGame: LiveData<Boolean>
        get() = _endGame

    private val _winner = MutableLiveData<Boolean>()
    val winner: LiveData<Boolean>
        get() = _winner

  //  private val _numQuestions = min((questions.size + 1) / 2, 3)
    private val _numQuestions = MutableLiveData( min((questions.size + 1) / 2, 3))
    val numQuestions: LiveData<Int>
        get() = _numQuestions

    init {
        // Embaralha as questões e coloca um indice na primeira
        randomizeQuestions()
    }

    // randomiza as questões e coloca o indice como primeira
    fun randomizeQuestions() {
        questions.shuffle()
        _questionIndex.value = 0
        setQuestion()
    }

    private fun plusIndex(){
        this._questionIndex.value = questionIndex.value?.plus(1)
    }

    fun setQuestion() {
        currentQuestion = questions[questionIndex.value?:0]
        //cria uma copia da lista de respostas
        answers = currentQuestion.answers.toMutableList()
        // randomiza
        answers.shuffle()

    }

    private fun hasNextWord(){
        //Verifica se chegou ao fim
        if (questionIndex.value?:-1 < numQuestions.value?:-1) {
            currentQuestion = questions[questionIndex.value?: 0]
            setQuestion()

        } else {
            //Venceu - Navigate para o gameWonFragment
            this._winner.value = true
            this._endGame.value = true


        }
    }

    fun getResult(checkedId: Int) {
        // Não faça nada se o id for -1
        if (-1 != checkedId) {
            var answerIndex = 0
            when (checkedId) {
                R.id.secondAnswerRadioButton -> answerIndex = 1
                R.id.thirdAnswerRadioButton -> answerIndex = 2
                R.id.fourthAnswerRadioButton -> answerIndex = 3
            }

            //Verifica se é a primeira resposta original
            //A primeira resposta original sempre é a resposta correta,
            if (answers[answerIndex] == currentQuestion.answers[0]) {

                plusIndex()
                hasNextWord()

            } else {
                // Perdeu - Navigate para o gameOverFragment.
                this._winner.value = false
                this._endGame.value = true


            }
        }
    }

}