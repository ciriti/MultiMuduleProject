package com.example.posttemplate.posts.ui

import com.example.posttemplate.posts.domain.model.Post

sealed class HomeState {
    data object Loading : HomeState()
    data class Error(val message: String) : HomeState()
    data class Success(val posts: List<Post>) : HomeState()
}

sealed class HomeIntent {
    object LoadPosts : HomeIntent()
    data class SelectPost(val postId: Int) : HomeIntent()
}

sealed class HomeEffect {
    data class ShowError(val message: String) : HomeEffect()
    data class NavigateToPostDetails(val postId: Int) : HomeEffect()
}
