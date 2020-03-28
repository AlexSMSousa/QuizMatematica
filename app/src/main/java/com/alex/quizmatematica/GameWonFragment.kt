package com.alex.quizmatematica


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.alex.quizmatematica.databinding.FragmentGameWonBinding

/**
 * A simple [Fragment] subclass.
 */
class GameWonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentGameWonBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game_won, container, false)

        val args = arguments?.let { GameWonFragmentArgs.fromBundle(it) }
        args?.let {

            Toast.makeText(
                context,
                "Acertou ${it.numCorrect} de ${it.numQuestions}",
                Toast.LENGTH_LONG)
                .show()
        }

        binding.nextMatchButton.setOnClickListener {
            Navigation.findNavController(it).navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        return binding.root
    }


}
