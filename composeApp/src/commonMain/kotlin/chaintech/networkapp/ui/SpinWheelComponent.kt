package chaintech.networkapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import shakedetection.composeapp.generated.resources.Res
import shakedetection.composeapp.generated.resources.icn_center
import shakedetection.composeapp.generated.resources.icn_tick
import shakedetection.composeapp.generated.resources.icn_wheel_bg

@Composable
internal fun SpinWheelComponent(spinWheelState: SpinWheelState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.icn_wheel_bg),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(7f))
            BoxWithConstraints(
                modifier = Modifier
                    .weight(86f)
                    .aspectRatio(1f)
            ) {
                val imageSize = this.maxHeight.times(0.14f)
                SpinWheel(modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationZ = spinWheelState.rotation.value
                    }, items = spinWheelState.items)
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(imageSize),
                    painter = painterResource(Res.drawable.icn_center),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.weight(7f))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .offset(y = -20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .weight(14f)
                    .aspectRatio(1f),
                painter = painterResource(Res.drawable.icn_tick),
                contentDescription = null
            )

            Spacer(modifier = Modifier.weight(62f))
        }
    }
}

