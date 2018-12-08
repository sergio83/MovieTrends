package com.billboard.movies.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.billboard.movies.BuildConfig
import com.billboard.movies.R
import com.billboard.movies.model.db.entity.Language
import com.billboard.movies.model.db.entity.Movie
import com.billboard.movies.model.rest.base.Status
import com.billboard.movies.ui.adapter.MoviesAdapter
import com.billboard.movies.utils.NetworkUtils
import com.billboard.movies.utils.SpinnerDropDownAdapter
import com.billboard.movies.viewmodel.MoviesViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_billboard.*
import java.util.*
import javax.inject.Inject
import androidx.core.util.Pair as UtilPair

class BillboardActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mMoviesViewModel: MoviesViewModel
    private var mViewAdapter = MoviesAdapter(mutableListOf()) { movie, view ->
        showMovieDetail(movie, view)
    }
    lateinit var linearLayoutManager: LinearLayoutManager
    private var adapter: SpinnerDropDownAdapter? = null
    var isLoading = false
    var languages: List<Language> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_billboard)
        setupToolbar(toolbar, R.string.app_name)
        AndroidInjection.inject(this)

        mMoviesViewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = mViewAdapter

        this.swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        swipeContainer.setOnRefreshListener {
            updateContent()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()

                if (visibleItemCount + pastVisibleItems >= totalItemCount && !isLoading) {
                    getNextPage()
                    isLoading = true
                }
            }
        })

        checkApiKeys()
//      updateConfig(mMoviesViewModel.getCurrentLanguage())
        updateContent(false)

        //TODO: 1 pull to refresh --> DONE
        //TODO: 2 title and navigation movible --> DONE
        //TODO: 3 check internet --> DONE
        //TODO: 4 empty list --> DONE
        //TODO: 5 loading --> DONE
        //TODO: 6 paginado --> DONE

        //TODO: 7 transiciones --> DONE
        //TODO: 8 pantalla detalle maquetar --> DONE
        //TODO: 9 pantalla detalle viewmodel (zip para ejecutar al mismo tiempo varias tareas) --> DONE
        //TODO: 13 configurar lenguage: --> DONE
        //TODO: Revisar arquitectura sobretodo los viewmodel

        //Extra
        //TODO: 10 implementar un worker manager para bajar genres
        //TODO: 11 Implementar las pruebas

        //TODO: 12 Borrar warnings --> DONE

        //TODO: 14 descargar configuracion

        //TODO: 15 ver Transformations.switchMap y live datsa --> DONE

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.home_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
//        R.id.action_settings -> {
//            // User chose the "Settings" item, show the app settings UI...
//            showChangeLanguageAlert()
//            true
//        }
//
//        else -> {
//            // If we got here, the user's action was not recognized.
//            // Invoke the superclass to handle it.
//            super.onOptionsItemSelected(item)
//        }
//    }

    fun checkApiKeys(){

        if(BuildConfig.API_KEY.isEmpty() || BuildConfig.YOUTUBE_API_KEY.isEmpty()){
            val builder = AlertDialog.Builder(this)
            with(builder)
            {
                setTitle(getString(R.string.error))
                setMessage(getString(R.string.error_keys))
                setPositiveButton("OK"){ _, _ -> finish()}
                show()
            }
        }
    }

    private fun updateConfig(lang: Language, shouldRecreate:Boolean = false){
        val conf = resources.configuration
        conf.setLocale(Locale(lang.format6391))
        resources.updateConfiguration(conf, resources.displayMetrics)
        if(shouldRecreate)
            recreate()
    }

    private fun showChangeLanguageAlert() {
        val list = mutableListOf<String>()
        languages = mMoviesViewModel.getLanguages()
        languages.forEach {
            it.resourceId?.let {
                list.add(getString(it))
            }
        }

        val index = languages.indexOf(mMoviesViewModel.getCurrentLanguage())
        adapter = SpinnerDropDownAdapter(this, list, selection = index)

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.setting_language)).setAdapter(adapter) { dialogInterface, i ->
            mMoviesViewModel.saveLanguage(languages[i])
            updateConfig(languages[i], true)
        }
        builder.create().show()
    }

    fun onUpdateContent(v: View) {
        updateContent()
    }

    private fun updateContent(force: Boolean = true) {

        if (!NetworkUtils.isNetworkConnected(this) && mViewAdapter.size() == 0) {
            showNoInternetView()
            return
        }

        mMoviesViewModel.getMovies().observe(this, Observer { moviesResource ->

            when (moviesResource.status) {
                Status.LOADING -> {
                    showLoadingView()
                }

                Status.SUCCESS -> {

                    mViewAdapter.refreshList(moviesResource?.data ?: listOf())
                    if (mViewAdapter.size() == 0) {
                        showNoContentView()
                    } else {
                        showContentView()
                    }

                }

                Status.ERROR -> {
                    if (mViewAdapter.size() == 0) {
                        showNoContentView()
                    }
                }
            }
        })

    }

    private fun getNextPage() {
        mMoviesViewModel.getNextMoviePage().observe(this, Observer { moviesResource ->

            when (moviesResource.status) {
                Status.LOADING -> {
                }

                Status.SUCCESS -> {
                    isLoading = false
                    mViewAdapter.refreshList(moviesResource?.data ?: listOf())
                }

                Status.ERROR -> {
                    isLoading = false
                }
            }
        })
    }

    private fun showMovieDetail(movie: Movie, view: View) {

        val navigationBar = findViewById<View>(android.R.id.navigationBarBackground)
        val statusBar = findViewById<View>(android.R.id.statusBarBackground)

        val p1 = UtilPair.create(view, resources.getString(R.string.banner_transition_name))
        //Important: to avoid traslucent status bar and navigation
        val p2 = UtilPair.create(statusBar!!, statusBar.transitionName!!)
        val p3 = UtilPair.create(navigationBar!!, navigationBar.transitionName!!)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, p1, p2, p3
        )

        val intent = MovieDetailActivity.newIntent(this, movie.id)
        startActivity(intent, options.toBundle())
    }

    private fun showNoContentView() {
        swipeContainer.isRefreshing = false
        swipeContainer.visibility = View.GONE
        loadingView.visibility = View.GONE
        noContentView.visibility = View.VISIBLE
        noInternetView.visibility = View.GONE
    }

    private fun showNoInternetView() {
        swipeContainer.isRefreshing = false
        swipeContainer.visibility = View.GONE
        loadingView.visibility = View.GONE
        noContentView.visibility = View.GONE
        noInternetView.visibility = View.VISIBLE
    }

    private fun showLoadingView() {
        swipeContainer.isRefreshing = true
        swipeContainer.visibility = View.GONE
        loadingView.visibility = View.VISIBLE
        noContentView.visibility = View.GONE
        noInternetView.visibility = View.GONE
    }

    private fun showContentView() {
        swipeContainer.isRefreshing = false
        swipeContainer.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        noContentView.visibility = View.GONE
        noInternetView.visibility = View.GONE
    }

}



