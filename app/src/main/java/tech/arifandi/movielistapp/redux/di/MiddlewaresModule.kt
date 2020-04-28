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
import tech.arifandi.movielistapp.redux.actions.MovieReviewListActions
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
            .register(movieReviewListMiddleware(moviesController))
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
                                                // we only want to show first 3 reviews here, if any.
                                                reviews = movieReviews.reviews.take(3),
                                                // we'll show the remaining on Review Detail Page
                                                moreReviewsAvailable = movieReviews.totalResults > 3
                                            )
                                        )
                                    }
                                    .onErrorResumeNext(
                                        // we'd want to continue displaying the movie even though the review can't be fetched
                                        Single.just(
                                            MovieDetailItem(
                                                movieDetail = movieDetail
                                            )
                                        )
                                    )
                            }
                            .flatMap { movieDetailItem ->
                                moviesController
                                    .getMovieVideos(action.payload)
                                    .flatMap { movieVideos ->
                                        // we'd only want to show youtube links, not others (e.g. Vimeo)
                                        // also, filter for Trailers only, not other.
                                        val filteredVideos = movieVideos
                                            .filter { it.site == Constants.Default.YOUTUBE_SITE
                                                    && it.type == Constants.Default.TYPE_TRAILER }
                                        return@flatMap Single.just(
                                            movieDetailItem.copy(
                                                videos = filteredVideos
                                            )
                                        )
                                    }
                                    .onErrorResumeNext(
                                        // we'd want to continue displaying the movie even though the review can't be fetched
                                        Single.just(movieDetailItem)
                                    )
                            }
                            .subscribe(
                                {
                                    dispatch(MovieDetailActions.GotResult(it))
                                },
                                {
                                    dispatch(MovieDetailActions.FetchFailed(it))
                                }
                            )
                    }
                }

                next(action)
            }
        }

        private fun movieReviewListMiddleware(
            moviesController: MoviesController
        ): MiddlewareFunction<Action> {
            return { state, action, dispatch, next ->

                when (action) {
                    is MovieReviewListActions.StartFetching -> {
                        moviesController
                            .getMovieReviews(action.payload)
                            .subscribe(
                                {
                                    val loadMore = it.page < it.totalPages
                                    dispatch(MovieReviewListActions.GotResult(it.reviews, loadMore = loadMore))
                                },
                                {
                                    dispatch(MovieReviewListActions.FetchFailed(it))
                                }
                            )
                    }
                    MovieReviewListActions.LoadNextPage -> {
                        val movieReviewListState = state!!.movieReviewListState
                        val page = movieReviewListState.currentPage.plus(1)
                        val movieId = movieReviewListState.currentMovieId ?: 0 // should never happened
                        moviesController
                            .getMovieReviews(movieId, page)
                            .subscribe(
                                {
                                    val loadMore = it.page < it.totalPages
                                    dispatch(MovieReviewListActions.GotResult(it.reviews, loadMore = loadMore))
                                },
                                {
                                    dispatch(MovieReviewListActions.LoadNextPageError)
                                }
                            )
                    }
                }

                next(action)
            }
        }

    }
}