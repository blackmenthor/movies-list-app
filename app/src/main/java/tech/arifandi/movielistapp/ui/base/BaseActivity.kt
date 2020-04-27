package tech.arifandi.movielistapp.ui.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity

internal abstract class BaseActivity: AppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        inject()
    }

    abstract fun inject()
}