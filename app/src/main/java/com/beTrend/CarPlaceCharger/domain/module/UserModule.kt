package com.beTrend.CarPlaceCharger.domain.module

import com.beTrend.CarPlaceCharger.domain.repository.UserRepository
import com.beTrend.CarPlaceCharger.domain.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UserModule {
    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }
}
