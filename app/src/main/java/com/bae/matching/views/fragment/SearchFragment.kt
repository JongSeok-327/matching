package com.bae.matching.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bae.matching.R
import com.bae.matching.app.MatchingApplication
import com.bae.matching.databinding.FragmentSearchBinding
import com.bae.matching.utils.Dlog
import com.bae.matching.utils.ListLoadState
import com.bae.matching.viewmodels.SearchViewModel
import com.bae.matching.viewmodels.SearchViewModelFactory
import com.bae.matching.views.UserListItemDecoration
import com.bae.matching.views.adapter.UserListRecyclerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

class SearchFragment : Fragment()
{
    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(
            requireActivity().application as MatchingApplication,
            (requireActivity().application as MatchingApplication).matchingRepository
        )
    }

    private lateinit var userListAdapter: UserListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.navigation)
            bottomNavigationView?.visibility = View.VISIBLE
            // Pull To Refresh
            swipeRefresh.apply {
                setOnRefreshListener {
                    refresh()
                }
            }

            userListAdapter = UserListRecyclerAdapter(object : UserListRecyclerAdapter.ItemCallback{
                override fun onClickedItem(userId: Int) {
                    Dlog.d("On Item Clicked : $userId")
                    val action = TopFragmentDirections.actionSearchFragmentToUserDetailFragment(userId)
                    findNavController().navigate(action)
                }
            })

            recycler.apply {
                adapter = userListAdapter
                layoutManager = GridLayoutManager(context,2)
                setHasFixedSize(true)
                addItemDecoration(UserListItemDecoration())
            }
        }

        searchViewModel.getUserData()
        observeViewModel()
    }
    private fun refresh() {
        binding.swipeRefresh.isRefreshing = false
        searchViewModel.refresh()
    }

    private fun observeViewModel() {
        searchViewModel.apply {
            userList.map { user ->
                Dlog.d("user List observe : ${user?.map { it.nickname }}")
                userListAdapter.submitList(user)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            listStateLoading.map {
                Dlog.d("List Loading State : $it")
                if (it == ListLoadState.LOADING || it == ListLoadState.PREPARE) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}