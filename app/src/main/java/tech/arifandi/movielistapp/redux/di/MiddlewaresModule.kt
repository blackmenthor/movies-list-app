package tech.arifandi.movielistapp.redux.di

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import org.rekotlin.Action
import tech.arifandi.movielistapp.controller.MoviesController
import tech.arifandi.movielistapp.models.MovieDetailItem
import tech.arifandi.movielistapp.redux.actions.GenreActions
import tech.arifandi.movielistapp.redux.actions.GenreDetailActions
import tech.arifandi.movielistapp.redux.actions.MovieDetailActions
import tech.arifandi.movielistapp.utils.Constants
import javax.inject.Singleton

@Module
internal class MiddlewaresModule {

    @Provides
    @Singleton
    fun providesMiddlewareFactory(
        moviesController: MoviesController
    ): MiddlewareFactory {
        return MiddlewareFactory()
            .register(genresMiddleware(moviesController))
            .register(movieDetailMiddleware(moviesController))
    }

    companion object {
        private fun genresMiddleware(
            moviesController: MoviesController
        ): MiddlewareFunction<Action> {
            return { state, action, dispatch, next ->

                when (action) {
                    GenreActions.StartFetching -> {
                        moviesController
                            .getAllGenre()
                            .subscribe(
                                {
                                    dispatch(GenreActions.GotResults(it))
                                },
                                {
                                    dispatch(GenreActions.FetchFailed(it))
                                }
                            )
                    }
                    is GenreDetailActions.StartFetching -> {
                        moviesController
                            .getMoviesByGenre(action.payload)
                            .subscribe(
                                {
                                    val loadMore = it.page < it.totalPages
                                    dispatch(GenreDetailActions.GotResults(it.movies, loadMore = loadMore))
                                },
                                {
                                    dispatch(GenreDetailActions.FetchFailed(it))
                                }
                            )
                    }
                    GenreDetailActions.LoadNextPage -> {
                        val genreDetailState = state!!.genreDetailState
                        val page = genreDetailState.currentPage.plus(1)
                        val genreId = genreDetailState.currentGenreId ?: 0 // should never happened
                        moviesController
                            .getMoviesByGenre(genreId, page)
                            .subscribe(
                                {
                                    val loadMore = it.page < it.totalPages
                                    dispatch(GenreDetailActions.GotResults(it.movies, loadMore = loadMore))
                                },
                                {
                                    dispatch(GenreDetailActions.LoadNextPageError)
                                }
                            )
                    }
                }

                next(action)
            }
        }

        private fun movieDetailMiddleware(
            moviesController: MoviesController
        ): MiddlewareFunction<Action> {
            return { state, action, dispatch, next ->

                when (action) {
                    is MovieDetailActions.StartFetching -> {
                        moviesController
                            .getMovieDetail(action.payload)
                            .flatMap { movieDetail ->
                                moviesController
                                    .getMovieReviews(action.payload)
                                    .flatMap { movieReviews ->
                                        return@flatMap Single.just(
                                            MovieDetailItem(
                                                movieDetail = movieDetail,
                                                reviews = movieReviews.reviews,
                                                loadMore = movieReviews.page < movieReviews.totalPages
                                            )
                                        )
                                    }
                            }
                            .flatMap { movieDetailItem ->
                                moviesController
                                    .getMovieVideos(action.payload)
                                    .flatMap { movieVideos ->
                                        val filteredVideos = movieVideos
                                            .filter { it.site == Constants.Default.YOUTUBE_SITE }
                                        return@flatMap Single.just(
                                            movieDetailItem.copy(
                                                videos = filteredVideos
                                            )
                                        )
                                    }
                            }
                            .subscribe(
                                {
                                    dispatch(MovieDetailActions.GotFirstResult(it))
                                },
                                {
                                    dispatch(MovieDetailActions.FetchFailed(it))
                                }
                            )
                    }
                    MovieDetailActions.LoadNextReviewPage -> {
                        val movieDetailState = state!!.movieDetailState
                        val page = movieDetailState.currentReviewPage.plus(1)
                        val genreId = movieDetailState.movie?.id ?: 0 // should never happened
                        moviesController
                            .getMoviesByGenre(genreId, page)
                            .subscribe(
                                {
                                    val loadMore = it.page < it.totalPages
                                    dispatch(GenreDetailActions.GotResults(it.movies, loadMore = loadMore))
                                },
                                {
                                    dispatch(GenreDetailActions.LoadNextPageError)
                                }
                            )
                    }
                }

                next(action)
            }
        }

    }
}