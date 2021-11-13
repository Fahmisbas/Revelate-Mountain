package com.revelatestudio.revelatemountain.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.revelatestudio.revelatemountain.databinding.FragmentDashboardBinding
import com.revelatestudio.revelatemountain.ui.adapter.WallpaperListAdapter
import com.revelatestudio.revelatemountain.util.ViewType.LATEST
import com.revelatestudio.revelatemountain.util.ViewType.POPULAR
import dagger.hilt.android.AndroidEntryPoint
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.recyclerview.widget.ConcatAdapter





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


        latestWallpaperAdapter { adapter ->
            viewModel.latestMountainPictures.observe(viewLifecycleOwner, {
                adapter.setViewType(LATEST)
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            })

        }


      popularWallpaperAdapter { adapter ->
          viewModel.pouplarMountainPictures.observe(viewLifecycleOwner, { it ->
              adapter.setViewType(POPULAR)
              adapter.submitData(viewLifecycleOwner.lifecycle, it)
          })
      }
    }




    private fun latestWallpaperAdapter(adapterObj: (WallpaperListAdapter) -> Unit) {
        val adapter = WallpaperListAdapter()
        binding.rvLatest.adapter = adapter
        binding.rvLatest.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapterObj.invoke(adapter)
    }



    private fun popularWallpaperAdapter(adapterObj: (WallpaperListAdapter) -> Unit) {
        val adapter = WallpaperListAdapter()
        binding.rvDashboard.adapter = adapter
        binding.rvDashboard.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapterObj.invoke(adapter)
    }

    companion object {

        fun newInstance() = DashboardFragment()
    }
}