package makeus6.hackathon.homecafe.src.main.home


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.src.main.home.HomeFragmentView
import com.softsquared.template.kotlin.src.main.home.HomeService
import com.softsquared.template.kotlin.src.main.home.models.PostSignUpRequest
import com.softsquared.template.kotlin.src.main.home.models.SignUpResponse
import com.softsquared.template.kotlin.src.main.home.models.UserResponse
import makeus6.hackathon.homecafe.R
import makeus6.hackathon.homecafe.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.feed.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


    }

}