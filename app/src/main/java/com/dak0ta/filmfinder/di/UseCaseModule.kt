package com.dak0ta.filmfinder.di

import com.dak0ta.domain.usecase.GetFilmDetailsUseCase
import com.dak0ta.domain.usecase.GetFilmImageUrlsUseCase
import com.dak0ta.domain.usecase.GetFilmsUseCase
import com.dak0ta.domain.usecase.GetReviewsUseCase
import com.dak0ta.domain.usecase.GetTotalPagesUseCase
import com.dak0ta.domain.usecase.impl.GetFilmDetailsUseCaseImpl
import com.dak0ta.domain.usecase.impl.GetFilmImageUrlsUseCaseImpl
import com.dak0ta.domain.usecase.impl.GetFilmsUseCaseImpl
import com.dak0ta.domain.usecase.impl.GetReviewsUseCaseImpl
import com.dak0ta.domain.usecase.impl.GetTotalPagesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindGetFilmDetailsUseCase(
        impl: GetFilmDetailsUseCaseImpl
    ): GetFilmDetailsUseCase

    @Binds
    @Singleton
    abstract fun bindGetFilmImageUrlsUseCase(
        impl: GetFilmImageUrlsUseCaseImpl
    ): GetFilmImageUrlsUseCase

    @Binds
    @Singleton
    abstract fun bindGetFilmsUseCase(
        impl: GetFilmsUseCaseImpl
    ): GetFilmsUseCase

    @Binds
    @Singleton
    abstract fun bindGetReviewsUseCase(
        impl: GetReviewsUseCaseImpl
    ): GetReviewsUseCase

    @Binds
    @Singleton
    abstract fun bindGetTotalPagesUseCase(
        impl: GetTotalPagesUseCaseImpl
    ): GetTotalPagesUseCase

}
