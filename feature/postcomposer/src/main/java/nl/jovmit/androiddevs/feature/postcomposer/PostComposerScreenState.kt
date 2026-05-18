package nl.jovmit.androiddevs.feature.postcomposer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostComposerScreenState(
    val title: String = "",
    val body: String = "",
    val isLoading: Boolean = false,
    val isTitleBlank: Boolean = false,
    val isBodyBlank: Boolean = false,
    val isPosted: Boolean = false,
    val isOffline: Boolean = false,
    val isUnavailable: Boolean = false,
    val isNotSignedIn: Boolean = false
): Parcelable
