package bettinger.david.taskchainapp.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import bettinger.david.taskchainapp.R
import bettinger.david.taskchainapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class LoginActivity : BaseActivity() {


    private val viewModel: LoginViewModel by viewModels()

    //TODO implement login
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        btn_login.setOnClickListener {
            login()
        }

        viewModel.messageLiveData.observe(this, {
            showMessage(resources.getString(it), this)
        })

        viewModel.userLiveData.observe(this, {
            startActivity(Intent(this, TaskChainsActivity::class.java))
        })

    }

    private fun login() {
        viewModel.isLoading.observe(this, {
            it?.let { loading ->
                if (loading) showCustomProgressDialog() else hideProgressDialog()
            }
        })

        viewModel.loginUserWithUserName(et_user_name.text.toString())
    }

}