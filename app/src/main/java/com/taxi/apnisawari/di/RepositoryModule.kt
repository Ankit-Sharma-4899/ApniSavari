package com.taxi.apnisawari.di


import com.taxi.apnisawari.repository.FirestoreRepository
import com.taxi.apnisawari.repository.FirestoredbRepositoryimpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesFirestoreRepository(
        repo:FirestoredbRepositoryimpl
    ): FirestoreRepository



}
