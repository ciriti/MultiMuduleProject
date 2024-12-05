package io.github.ciriti.profile.ui

import androidx.lifecycle.viewModelScope
import io.github.ciriti.profile.domain.service.UserService
import io.github.ciriti.ui.components.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val service: UserService
) : BaseViewModel<ProfileState, ProfileEffect, ProfileIntent>() {

    private val _state = MutableStateFlow(ProfileState())
    override val state: StateFlow<ProfileState> = _state

    private val _effect = MutableSharedFlow<ProfileEffect>()
    override val effect: SharedFlow<ProfileEffect> = _effect

    override fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadUser(intent.userId)
        }
    }

    private fun loadUser(userId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            service.getUserById(userId).fold(
                { throwable ->
                    _state.value = _state.value.copy(isLoading = false, errorMessage = throwable.message)
                    _effect.emit(ProfileEffect.ShowError(throwable.message ?: "Unknown error"))
                },
                { user ->
                    _state.value = _state.value.copy(isLoading = false, user = user, errorMessage = null)
                }
            )
        }
    }
}
