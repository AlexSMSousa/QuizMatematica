package com.alex.quizmatematica

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alex.quizmatematica.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    data class Question(
        val text: String,
        val answers: List<String>)

    //Lista de questões e respostas - A primeira resposta sempre é a certa
    private val questions: MutableList<Question> = mutableListOf(
        Question(text = "Quanto é 1 X 7?",
            answers = listOf("7", "8", "9", "0")),
        Question(text = "Quanto é 2 X 3",
            answers = listOf("6", "3", "11", "9")),
        Question(text = "Quanto é 5 X 4",
            answers = listOf("20", "35", "25", "15")),
        Question(text = "Quanto é 4 X 3",
            answers = listOf("12", "13", "11", "14")),
        Question(text = "Quanto é 7 X 8",
            answers = listOf("56", "55", "57", "59"))

    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>
    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false)

        // Embaralha as questões e coloca um indece na primeira
        randomizeQuestions()

        // Bind com o layout
        binding.game = this

        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            val checkedId = binding.questionRadioGroup.checkedRadioButtonId
            // Não faça nada se o id for -1
            if (-1 != checkedId) {
                var answerIndex = 0
                when (checkedId) {
                    R.id.secondAnswerRadioButton -> answerIndex = 1
                    R.id.thirdAnswerRadioButton -> answerIndex = 2
                    R.id.fourthAnswerRadioButton -> answerIndex = 3
                }
                //A primeira resposta original sempre é a resposta correta,
                //Verifica se é a primeira resposta original
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    //Verifica se chegou ao fim
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                      //  binding.invalidateAll()
                    } else {
                        //Venceu - Navigate para o gameWonFragment
                    }
                } else {
                    // Perdeu - Navigate para o gameOverFragment.
                }
            }
        }
        return binding.root
    }

    // randomiza as questões e coloca o indice como primeira
    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }


    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        //cria uma copia da lista de respostas
        answers = currentQuestion.answers.toMutableList()
        // randomiza
        answers.shuffle()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_quiz, questionIndex + 1, numQuestions)
    }
}
