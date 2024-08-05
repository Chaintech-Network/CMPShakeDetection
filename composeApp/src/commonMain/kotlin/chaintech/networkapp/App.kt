package chaintech.networkapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chaintech.network.cmpshakedetection.rememberShakeDetector
import chaintech.networkapp.theme.AppTheme
import chaintech.networkapp.ui.Item
import chaintech.networkapp.ui.SpinWheelComponent
import chaintech.networkapp.ui.SpinWheelItem
import chaintech.networkapp.ui.rememberSpinWheelState
import chaintech.networkapp.util.toColor
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import shakedetection.composeapp.generated.resources.Res
import shakedetection.composeapp.generated.resources.icn_background
import shakedetection.composeapp.generated.resources.icn_center
import shakedetection.composeapp.generated.resources.icn_coin
import shakedetection.composeapp.generated.resources.icn_gift
import shakedetection.composeapp.generated.resources.icn_jackpot
import shakedetection.composeapp.generated.resources.icn_sad
import shakedetection.composeapp.generated.resources.icn_tick
import shakedetection.composeapp.generated.resources.icn_ticket
import shakedetection.composeapp.generated.resources.icn_wheel_bg
import kotlin.random.Random


val spinItems = listOf(
    Item(title = "50 Coins", image = Res.drawable.icn_coin),
    Item(title = "Gifts", image = Res.drawable.icn_gift),
    Item(title = "500 Coins", image = Res.drawable.icn_coin),
    Item(title = "Try Again", image = Res.drawable.icn_sad),
    Item(title = "100 Coins", image = Res.drawable.icn_coin),
    Item(title = "Ticket", image = Res.drawable.icn_ticket),
)

@Composable
internal fun App() = AppTheme {

    val colors1 = remember {
        listOf(
            "F07021",
            "F5C91A"
        ).map { it.toColor() }
    }

    val colors2 = remember {
        listOf(
            "833735",
            "580343"
        ).map { it.toColor() }
    }

    val items = remember {
        List(spinItems.size) { index ->
            val colors = if (index % 2 == 0) colors1 else colors2

            SpinWheelItem(
                colors = colors
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(55.dp),
                        painter = painterResource(spinItems[index].image),
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                    )
                    Text(
                        text = spinItems[index].title,
                        style = TextStyle(color = Color(0xFFFFFFFF), fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.offset(y = -8.dp)
                    )
                }

            }

        }
    }

    val spinState = rememberSpinWheelState(
        items = items,
        backgroundImage = Res.drawable.icn_wheel_bg,
        centerImage = Res.drawable.icn_center,
        indicatorImage = Res.drawable.icn_tick,
        onSpinningFinished = null,
    )
    var isSpinning = false
    val shakeDetector = rememberShakeDetector()
    // Start detecting shakes
    LaunchedEffect(Unit) {
        shakeDetector.start()
    }
    shakeDetector.onShake {
        if(!isSpinning) {
            isSpinning = true
            spinState.launchInfinite()

            MainScope().launch {
                delay(timeMillis = Random.nextLong(5000, 9000))
                spinState.stoppingWheel(Random.nextInt(0, 6))
//                        delay(5000)
                isSpinning = false
            }
        }
    }

    // Stop detecting shakes when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            shakeDetector.stop()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(Res.drawable.icn_background),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(50.dp))

            Image(
                modifier = Modifier.width(303.dp).height(144.dp),
                painter = painterResource(Res.drawable.icn_jackpot),
                contentScale = ContentScale.Crop,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.size(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .aspectRatio(1f)
            ) {
                SpinWheelComponent(spinState)
            }
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}