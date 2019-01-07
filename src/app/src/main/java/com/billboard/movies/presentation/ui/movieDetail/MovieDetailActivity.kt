package com.billboard.movies.presentation.ui.movieDetail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.billboard.movies.BuildConfig
import com.billboard.movies.R
import com.billboard.movies.data.remote.api.utils.Status
import com.billboard.movies.domain.model.*
import com.billboard.movies.presentation.ui.base.BaseActivity
import com.billboard.movies.presentation.utils.extensions.parse
import com.google.android.material.chip.Chip
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.content_movie_detail_view.*
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject


class MovieDetailActivity : BaseActivity() {

    companion object {

        private const val MOVIE_ID_PARAM = "id"

        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_ID_PARAM, movieId)
            return intent
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mMovieDetailViewModel: MovieDetailViewModel
    private var mPlayer: YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupToolbar(toolbar, R.string.app_name)
        addBackButton(toolbar)

        AndroidInjection.inject(this)
        mMovieDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel::class.java)

        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorTransparent))

        val movieId = intent.getIntExtra(MOVIE_ID_PARAM, -1)
        if (movieId >= 0) {
            mMovieDetailViewModel.getMovie(movieId).observe(this, Observer { movie ->
                movie?.let {
                    showMovie(it)
                }
            })

            mMovieDetailViewModel.getMovieDetail(movieId).observe(this, Observer { movieResource ->

                when (movieResource.status) {
                    Status.LOADING -> {

                    }

                    Status.SUCCESS -> {
                        movieResource.data?.let {
                            showMovieDetails(it)
                        }
                    }

                    Status.ERROR -> {

                    }
                }

            })

            mMovieDetailViewModel.getMovieCast(movieId).observe(this, Observer { castResource ->
                when (castResource.status) {
                    Status.LOADING -> {

                    }

                    Status.SUCCESS -> {
                        runOnUiThread {
                            castResource.data?.let {
                                showCast(it)
                            }
                        }
                    }

                    Status.ERROR -> {

                    }
                }

            })

            mMovieDetailViewModel.getVideos(movieId).observe(this, Observer { videosResource ->
                when (videosResource.status) {
                    Status.LOADING -> {

                    }

                    Status.SUCCESS -> {
                        runOnUiThread {
                            videosResource.data?.let {
                                showVideos(it)
                            }
                        }
                    }

                    Status.ERROR -> {

                    }
                }

            })

            mMovieDetailViewModel.getGenres(movieId).observe(this, Observer { genres ->
                showGenres(genres)
            })

        } else {
            finish()
        }

        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        window.statusBarColor = Color.TRANSPARENT


    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    private fun showMovie(movie: Movie) {
        movie.title?.let {
            supportActionBar?.apply {
                collapsingToolbar.title = it
            }
        }
        titleTextView.text = movie.title
        subtitleTextView.text = movie.releaseDate?.parse("yyyy-MM-dd", "MMMM dd, yyyy")?.capitalize()
        summaryTextView.text = movie.overview
        popularityView.percentage = (movie.voteAverage * 10).toInt()
        bannerImageView.setImageURI(movie.bannerUri())
        posterImageView.setImageURI(movie.posterUri())

        languageTextView.text = "-"
        movie.originalLanguage?.let {
            mMovieDetailViewModel.getLanguage(it).observe(this, Observer { language ->
                languageTextView.text = language?.name ?: "-"
            })
        }

    }

    private fun showGenres(genres: List<Genre>) {
        genres.map {
            val chip = Chip(genresChipGroup.context)
            chip.text = it.name
            genresChipGroup.addView(chip)
        }
    }

    private fun showMovieDetails(detail: MovieDetail) {

        if (detail.budget > 0) {
            budgeTextView.text = "$" + NumberFormat.getNumberInstance(Locale.US).format(detail.budget)
        } else {
            budgeTextView.text = "-"
        }

        if (detail.runtime > 0) {
            val hours = detail.runtime / 60 //since both are ints, you get an int
            val minutes = detail.runtime % 60
            durationTextView.text = "" + hours + "h " + minutes + "m"
        } else {
            durationTextView.text = "-"
        }

        statusTextView.text = detail.status
    }

    private fun showCast(cast: List<Actor>) {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        castRecyclerView.layoutManager = linearLayoutManager
        val adapter = CastAdapter(cast)
        castRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun showVideos(videoEntities: List<Video>) {

        val youTubePlayerFragment =
            supportFragmentManager.findFragmentById(R.id.youtubeView) as YouTubePlayerSupportFragment?

        if (videoEntities.isEmpty()) {
            videoContainer.visibility = View.GONE
        }

        youTubePlayerFragment?.initialize(BuildConfig.YOUTUBE_API_KEY, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(
                provider: YouTubePlayer.Provider, player: YouTubePlayer,
                wasRestored: Boolean
            ) {
                if (!wasRestored) {
                    val video = videoEntities.first()
                    mPlayer = player
                    mPlayer?.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                    mPlayer?.cueVideo(video.key)
                }
            }

            override fun onInitializationFailure(arg0: YouTubePlayer.Provider, arg1: YouTubeInitializationResult) {

            }
        })
    }


}
