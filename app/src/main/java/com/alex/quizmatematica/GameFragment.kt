package com.alex.quizmatematica

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.alex.quizmatematica.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        // Bind com o layout e o viewModel
        binding.game = viewModel

        binding.lifecycleOwner = this

        viewModel.questionIndex.observe(viewLifecycleOwner, Observer {questionIndex ->
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.title_android_quiz, questionIndex + 1, viewModel.numQuestions.value)

        })

        viewModel.endGame.observe(viewLifecycleOwner, Observer { endGame ->
            if(endGame){
                view?.let { v ->
                    if(viewModel.winner.value == true){
                        Navigation.findNavController(v)
                            .navigate(GameFragmentDirections
                                .actionGameFragmentToGameWonFragment(viewModel.numQuestions.value?:0, viewModel.questionIndex.value?:0))

                    }else {
                        Navigation.findNavController(v)
                            .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
                    }
                }

            }
        })

        binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { view: View ->
            viewModel.getResult(binding.questionRadioGroup.checkedRadioButtonId)
            if(viewModel.endGame.value != true){
                binding.invalidateAll()
            }

        }
        return binding.root
    }

}
