package chaintech.networkapp.ui

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.min
import chaintech.networkapp.util.toBrush

@Composable
internal fun SpinWheel(
    modifier: Modifier = Modifier,
    items: List<SpinWheelItem>,
) {
    BoxWithConstraints(modifier = modifier) {

        val degreesPerItems = items.getDegreesPerItem()
        val size = min(this.maxHeight, this.maxWidth)
        val brushEnd = with(LocalDensity.current) { size.toPx() / 2f }

        items.forEachIndexed { index, item ->
            SpinWheelSlice(
                modifier = Modifier.rotate(degrees = degreesPerItems * index),
                size = size,
                brush = item.colors.toBrush(brushEnd),
                degree = degreesPerItems,
                content = item.content
            )
        }
    }
}
