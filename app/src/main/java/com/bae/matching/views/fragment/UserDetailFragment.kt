package com.bae.matching.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import com.bae.matching.R
import com.bae.matching.databinding.FragmentUserDetailBinding
import com.bae.matching.utils.Dlog
import com.bae.matching.viewmodels.UserDetailViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserDetailFragment : Fragment()
{
    private var _binding: FragmentUserDetailBinding? = null
    private val binding
        get() = _binding!!
    private val args: UserDetailFragmentArgs by navArgs()
    private val userDetailViewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = args.userId
        Dlog.i("UserDetailFragment id : $userId")
        binding.apply {
            val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.navigation)
            bottomNavigationView?.visibility = View.GONE
            toolbar.setNavigationOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }

        loadData(userId)
        observeViewModel()
    }

    private fun loadData(userId: Int) {
        // User Detail
        userDetailViewModel.getUserDetailData(userId)
    }

    private fun observeViewModel() {
        userDetailViewModel.apply {
            userInfo.map {user ->
                CoroutineScope(Dispatchers.Main).launch {
                    // Toolbar Title
                    if (user != null){
                        binding.toolbar.title = "${user.age}歳 ${user.address}"
                    }

                    // Photo
                    context?.let {
                        val imageLoader = ImageLoader(it)
                        val request = ImageRequest.Builder(it)
                            .data(user?.photo)
                            .build()

                        val drawable = imageLoader.execute(request).drawable
                        binding.imgUserPhoto.setImageDrawable(drawable)
                    }

                    // Tweet
                    binding.textUserTweet.text = user?.tweet

                    // Nickname
                    binding.textUserNickName.text = user?.nickname

                    // Age
                    user?.age?.let {
                        binding.textAge.text = it.toString()
                    }

                    // Address
                    binding.textAddress.text = user?.address
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}