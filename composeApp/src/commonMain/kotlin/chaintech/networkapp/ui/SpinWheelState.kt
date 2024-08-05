package chaintech.networkapp.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import chaintech.networkapp.util.getDegreeFromSection
import chaintech.networkapp.util.getDegreeFromSectionWithRandom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Stable
data class SpinWheelState(
    internal val items: List<SpinWheelItem>,
    internal val backgroundImage: DrawableResource,
    internal val centerImage: DrawableResource,
    internal val indicatorImage: DrawableResource,
    private val initSpinWheelSection: Int?,
    private val onSpinningFinished: (() -> Unit)?,
    private val stopDuration: Duration,
    private val stopNbTurn: Float,
    private val rotationPerSecond: Float,
    private val scope: CoroutineScope,
) {
    internal val rotation = Animatable(0f)

    init {
        initSpinWheelSection?.let {
            goto(it)
        } ?: launchInfinite()
    }

    fun stoppingWheel(sectionToStop: Int) {
        if (sectionToStop !in items.indices) {
            return
        }


        scope.launch {
            val destinationDegree = getDegreeFromSectionWithRandom(items, sectionToStop)

            rotation.animateTo(
                targetValue = rotation.value + (stopNbTurn * 360f) + destinationDegree + (360f - (rotation.value % 360f)),
                animationSpec = tween(
                    durationMillis = stopDuration.inWholeMilliseconds.toInt(),
                    easing = EaseOutQuad
                )
            )
        }

    }

    fun goto(section: Int) {
        scope.launch {
            if (section !in items.indices) {

                return@launch
            }
            val positionDegree = getDegreeFromSection(items, section)
            rotation.snapTo(positionDegree)
        }
    }

    fun launchInfinite() {
        scope.launch {
            // Infinite repeatable rotation when is playing
            rotation.animateTo(
                targetValue = rotation.value + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (rotationPerSecond * 1000f).toInt(),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }
}

@Composable
fun rememberSpinWheelState(
    items: List<SpinWheelItem>,
    backgroundImage: DrawableResource,
    centerImage: DrawableResource,
    indicatorImage: DrawableResource,
    onSpinningFinished: (() -> Unit)?,
    initSpinWheelSection: Int? = 0, //if null then infinite
    stopDuration: Duration = 5.seconds,
    stopNbTurn: Float = 3f,
    rotationPerSecond: Float = 0.8f,
    scope: CoroutineScope = rememberCoroutineScope(),
): SpinWheelState {
    return remember {
        SpinWheelState(
            items = items,
            backgroundImage = backgroundImage,
            centerImage = centerImage,
            indicatorImage = indicatorImage,
            initSpinWheelSection = initSpinWheelSection,
            stopDuration = stopDuration,
            stopNbTurn = stopNbTurn,
            rotationPerSecond = rotationPerSecond,
            scope = scope,
            onSpinningFinished = onSpinningFinished,
        )
    }
}