package com.dicoding.warnaku_app.wellcome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.warnaku_app.R
import com.dicoding.warnaku_app.view.login.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ThirdFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third, container, false)

        val fabNext: FloatingActionButton = view.findViewById(R.id.fab_next)
        fabNext.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()  // Pastikan WellcomeActivity juga ditutup
        }

        return view
    }
}