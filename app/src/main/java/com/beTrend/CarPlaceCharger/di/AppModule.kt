package com.beTrend.CarPlaceCharger.di

import com.beTrend.CarPlaceCharger.domain.repository.AuthRepository
import com.beTrend.CarPlaceCharger.domain.repository.AuthRepositoryImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(
        auth = Firebase.auth
    )
}