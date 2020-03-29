package com.alex.quizmatematica


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.alex.quizmatematica.databinding.FragmentHowToPlayBinding

/**
 * A simple [Fragment] subclass.
 */
class HowToPlayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_how_to_play, container, false)
    }


}
