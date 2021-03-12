package makeus6.hackathon.homecafe.src.login

import makeus6.hackathon.homecafe.src.login.models.LoginResponse
import makeus6.hackathon.homecafe.src.login.models.SetProfileResponse

interface LoginView {
    fun onLoginSuccess(response: LoginResponse)

    fun onLoginFailure(message: String)

    fun onSetProfileSuccess(response: SetProfileResponse)

    fun onSetProfileFailure(message: String)
}