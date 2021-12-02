package com.revelatestudio.revelatemountain.ui.dashboard

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.revelatestudio.revelatemountain.databinding.FragmentDashboardBinding
import com.revelatestudio.revelatemountain.ui.adapter.WallpaperListAdapter
import com.revelatestudio.revelatemountain.util.ViewType.LATEST
import com.revelatestudio.revelatemountain.util.ViewType.POPULAR
import com.revelatestudio.revelatemountain.util.gone
import com.revelatestudio.revelatemountain.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeLoading.setOnChildScrollUpCallback { _, _ ->
            binding.rvDashboard.canScrollVertically(-1)

        }

        binding.swipeLoading.isRefreshing = true

        observeLatestMountainPictures()
        observePopularWallpaper()



        with(binding.swipeLoading) {
            setOnRefreshListener {
                observeLatestMountainPictures()
                observePopularWallpaper()
                isRefreshing = false
            }
        }
    }


    private fun observePopularWallpaper() {
        popularWallpaperAdapter { adapter ->
            viewModel.getPopularMountainPicturesPager().observe(viewLifecycleOwner, {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            })
            lifecycleScope.launch {
                adapter.loadStateFlow.map {
                    it.refresh
                }.distinctUntilChanged()
                    .collect {
                        if (it is LoadState.NotLoading) {
                            binding.titlePopular.visible()

                            binding.swipeLoading.isRefreshing = adapter.itemCount <= 0
                        }
                    }
            }
        }
    }

    private fun observeLatestMountainPictures() {
        latestWallpaperAdapter { adapter ->
            viewModel.getLatestMountainPictures().observe(viewLifecycleOwner, {
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            })

            lifecycleScope.launch {
                adapter.loadStateFlow.map {
                    it.refresh
                }.distinctUntilChanged()
                    .collect {
                        if (it is LoadState.NotLoading) {
                            binding.titleLatest.visible()
                            binding.swipeLoading.isRefreshing = adapter.itemCount <= 0
                        }
                    }
            }
        }

    }

    private fun latestWallpaperAdapter(adapterObj: (WallpaperListAdapter) -> Unit) {
        WallpaperListAdapter(viewType = LATEST, onItemClickListener = {

        }).let {
            binding.rvLatest.adapter = it
            binding.rvLatest.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapterObj.invoke(it)
        }

    }


    private fun popularWallpaperAdapter(adapterObj: (WallpaperListAdapter) -> Unit) {
        WallpaperListAdapter(viewType = POPULAR, onItemClickListener = {

        }).let {
            binding.rvDashboard.adapter = it
            binding.rvDashboard.layoutManager =
                StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapterObj.invoke(it)
        }

    }
}