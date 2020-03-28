package com.alex.quizmatematica


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.alex.quizmatematica.databinding.FragmentGameWonBinding

/**
 * A simple [Fragment] subclass.
 */
class GameWonFragment : Fragment() {

    private var args: GameWonFragmentArgs? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentGameWonBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_game_won, container, false)

        this.args = arguments?.let { GameWonFragmentArgs.fromBundle(it) }
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

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)

        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.itemId){
                R.id.share -> shareSuccess()
            }

        return super.onOptionsItemSelected(item)
    }

    private fun getShareIntent() : Intent {
        val sharedIntent = Intent(Intent.ACTION_SEND)
        sharedIntent.apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_success_text, args?.numCorrect, args?.numQuestions)
                )
        }

        return sharedIntent
    }

    private fun shareSuccess(){
        startActivity(getShareIntent())
    }


}
