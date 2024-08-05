import androidx.compose.ui.window.ComposeUIViewController
import chaintech.networkapp.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
