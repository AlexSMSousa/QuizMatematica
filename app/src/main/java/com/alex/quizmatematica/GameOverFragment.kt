package com.alex.quizmatematica


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.alex.quizmatematica.databinding.FragmentGameOverBinding

/**
 * A simple [Fragment] subclass.
 */
class GameOverFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val binding: FragmentGameOverBinding =
           DataBindingUtil.inflate(inflater, R.layout.fragment_game_over, container, false)

        binding.tryAgainButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_gameOverFragment_to_gameFragment)
        }

        return binding.root

    }


}
