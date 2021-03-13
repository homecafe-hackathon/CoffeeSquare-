package makeus6.hackathon.homecafe.src.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.BaseFragment
import makeus6.hackathon.homecafe.databinding.FragmentHomeBinding
import makeus6.hackathon.homecafe.src.main.home.models.HomeResponse

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),HomeFragmentView{

    var mBackWait:Long = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feed.layoutManager=
          LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.searchBtn.setOnClickListener {
            binding.appName.visibility=View.GONE
            binding.searchEdit.visibility=View.VISIBLE
            binding.searchEditBtn.visibility=View.VISIBLE
            binding.searchBtn.visibility=View.GONE
        }

        binding.searchEditBtn.setOnClickListener {
            showLoadingDialog(requireContext())
            HomeService(this).searchFeed(binding.searchEdit.text.toString(),0,20)
            binding.searchEdit.setText("")
            binding.searchEditBtn.visibility=View.GONE
            binding.searchEdit.visibility=View.GONE
            binding.appName.visibility=View.VISIBLE
            binding.searchBtn.visibility=View.VISIBLE
        }


    }
    override fun onStart() {
        super.onStart()

        showLoadingDialog(requireContext())
        HomeService(this).getFeed(0,20)
    }

    override fun onGetFeedSuccess(response: HomeResponse) {
        dismissLoadingDialog()
        Log.d("확인",response.data.toString())
        binding.feed.adapter=HomeAdapter(requireContext(),response.data)
    }

    override fun onGetFeedFailure(message: String) {
        dismissLoadingDialog()
        Log.d("확인","실패"+message)

    }

    override fun onSearchFeedSuccess(response: HomeResponse) {
        dismissLoadingDialog()


        Log.d("확인",response.data.toString())
        binding.feed.adapter=HomeAdapter(requireContext(),response.data)
    }

    override fun onSearchFeedFailure(message: String) {
        dismissLoadingDialog()
        Log.d("확인","실패"+message)
    }


}