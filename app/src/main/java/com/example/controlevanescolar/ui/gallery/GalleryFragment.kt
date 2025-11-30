package com.example.controlevanescolar.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.controlevanescolar.R
import com.example.controlevanescolar.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButtonAlunos.setOnClickListener {
            findNavController().navigate(R.id.action_nav_gallery_to_alunosFragment)
        }

        binding.imageButtonResponsaveis.setOnClickListener {
            findNavController().navigate(R.id.action_nav_gallery_to_responsaveisFragment)
        }

        binding.imageButtonTurma.setOnClickListener {
            findNavController().navigate(R.id.action_nav_gallery_to_turmaFragment)
        }

        binding.imageButtonEscola.setOnClickListener {
            findNavController().navigate(R.id.action_nav_gallery_to_escolaFragment)
        }

        binding.imageButtonCondutores.setOnClickListener {
            findNavController().navigate(R.id.action_nav_gallery_to_condutoresFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}