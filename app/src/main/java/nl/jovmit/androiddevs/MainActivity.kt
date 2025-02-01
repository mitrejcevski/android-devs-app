package nl.jovmit.androiddevs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.airbnb.android.showkase.annotation.ShowkaseRoot
import com.airbnb.android.showkase.models.Showkase
import dagger.hilt.android.AndroidEntryPoint
import nl.jovmit.androiddevs.core.view.theme.AppTheme

@ShowkaseRoot
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            startActivity(Showkase.getBrowserIntent(this))
            finish()
//            AppTheme {
//                MainApp()
//            }
        }
    }
}