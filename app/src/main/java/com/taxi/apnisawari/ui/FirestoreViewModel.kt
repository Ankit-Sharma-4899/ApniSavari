package com.taxi.apnisawari.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taxi.apnisawari.ResultState
import com.taxi.apnisawari.firestoredb.FirestoreModelResponse
import com.taxi.apnisawari.repository.FirestoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirestoreViewModel  @Inject constructor(
    private val repo: FirestoreRepository
): ViewModel() {
    init {
        getItems()
    }

    private val _res:MutableState<FirestoreState> = mutableStateOf(FirestoreState())
    val res: State<FirestoreState> = _res
    private fun getItems() = viewModelScope.launch {
        repo.getItems().collect{
            when(it){
                is ResultState.Success->{
                    _res.value = FirestoreState(
                        data = it.data
                    )
                }
                is ResultState.Failure->{
                    _res.value= FirestoreState(
                        error = it.msg.toString()
                    )

                }
                ResultState.Loading->{
                    _res.value= FirestoreState(
                        isLoading = true
                    )
                }
            }
        }
    }
}

data class FirestoreState(
    val data:List<FirestoreModelResponse> = emptyList(),
    val error:String = "",
    val isLoading:Boolean = false
)