package tech.arifandi.movielistapp.ui.splash

import android.os.Bundle
import tech.arifandi.movielistapp.R
import tech.arifandi.movielistapp.App
import tech.arifandi.movielistapp.ui.base.BaseActivity
import tech.arifandi.movielistapp.ui.home.HomeActivity
import tech.arifandi.movielistapp.ui.splash.di.SplashGraph
import tech.arifandi.movielistapp.ui.splash.di.SplashModule
import javax.inject.Inject

internal class SplashActivity : BaseActivity(), SplashView {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        presenter.startCounter()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.destroy()
    }

    override fun inject() {

        SplashGraph()
            .sampleAppComponent((application as App).graph)
            .homeModule(SplashModule(this))
            .inject(this)
    }

    override fun onTimerFinish() {
        startActivity(HomeActivity.newIntent(this))
        finish()
    }

}