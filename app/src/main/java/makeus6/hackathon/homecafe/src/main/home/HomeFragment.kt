package makeus6.hackathon.homecafe.src.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.src.main.home.HomeFragmentView
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.config.BaseFragment
import makeus6.hackathon.homecafe.databinding.FragmentHomeBinding
import makeus6.hackathon.homecafe.src.main.MainActivity
import makeus6.hackathon.homecafe.src.main.home.models.HomeResponse

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home),HomeFragmentView{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feed.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    override fun onGetFeedSuccess(response: HomeResponse) {
        dismissLoadingDialog()
        Log.d("확인",response.message)
        binding.feed.adapter=HomeAdapter(requireContext(),response.data)
    }

    override fun onGetFeedFailure(message: String) {
        dismissLoadingDialog()
        Log.d("확인",message)

    }
}