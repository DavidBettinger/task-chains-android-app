package bettinger.david.taskchainapp.view.activities

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bettinger.david.taskchainapp.R

open class BaseActivity: AppCompatActivity() {

    // A global variable for Progress Dialog
    private var mProgressDialog: Dialog? = null


    protected fun showCustomProgressDialog() {
        mProgressDialog = Dialog(this)
        mProgressDialog!!.setContentView(R.layout.dialog_custom_progress)
        mProgressDialog!!.show()
    }

    protected fun hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    protected fun showMessage(message: String, context: Context){
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}