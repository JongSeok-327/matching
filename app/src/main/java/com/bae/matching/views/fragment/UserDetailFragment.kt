package com.bae.matching.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bae.matching.databinding.FragmentUserDetailBinding
import com.bae.matching.utils.Dlog

class UserDetailFragment : Fragment()
{
    private var _binding: FragmentUserDetailBinding? = null
    private val binding
        get() = _binding!!
    private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val userId = args.userId
            Dlog.e("UserDetailFragment id : $userId")

            toolbar.apply {
                setNavigationOnClickListener {
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}