package com.attendanceapp2.screens.mainscreens.scanner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun TransparentCamLayout() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawWithContent {

                val canvasWidth = size.width
                val canvasHeight = size.height
                val width = canvasWidth * 0.6f
                val height = width * 2 / 2f

                drawContent()

                drawWithLayer {

                    // Destination
                    // This is transparent color
                    drawRect(Color(0x99000000))

                    // Source
                    // This is where we extract this rect from transparent
                    drawRect(
                        topLeft = Offset((canvasWidth - width) / 2, canvasHeight * .3f),
                        size = Size(width, height),
                        color = Color.Transparent,
                        blendMode = BlendMode.SrcIn
                    )
                }

                drawRect(
                    topLeft = Offset((canvasWidth - width) / 2, canvasHeight * .3f),
                    size = Size(width, height),
                    color = Color.White,
                    style = Stroke(2.dp.toPx())
                )
            }
    ) {

    }
}

/**
 * Draw with layer to use [BlendMode]s
 */
private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}