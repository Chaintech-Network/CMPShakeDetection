package chaintech.networkapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

@Stable
data class SpinWheelItem(
    val colors: List<Color>,
    val content: @Composable () -> Unit,
)

data class Item(
    val title: String,
    val image:DrawableResource
)


internal fun List<SpinWheelItem>.getDegreesPerItem(): Float =  360f / this.size.toFloat()