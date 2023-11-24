package nl.jovmit.androiddevs.feature.login

import com.google.common.truth.Truth.*
import org.junit.jupiter.api.Test

class LoginScreenStateTest {

    @Test
    fun defaultScreenState() {
        val viewModel = LoginViewModel()

        assertThat(viewModel.screenState.value)
            .isEqualTo(LoginScreenState())
    }

}