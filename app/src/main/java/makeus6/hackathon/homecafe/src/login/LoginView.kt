package makeus6.hackathon.homecafe.src.login

import makeus6.hackathon.homecafe.src.login.models.LoginResponse

interface LoginView {
    fun onLoginSuccess(response: LoginResponse)

    fun onLoginFailure(message: String)
}