package com.attendanceapp2.user.studentapp.screens.mainscreens

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.attendanceapp2.appviewmodel.AppViewModelProvider
import com.attendanceapp2.universal.data.ScannedQRCodeHolder
import com.attendanceapp2.user.studentapp.viewmodel.AttendanceResult
import com.attendanceapp2.user.studentapp.viewmodel.QRCodeAnalyzer
import com.attendanceapp2.user.studentapp.viewmodel.ScannerViewModel
import kotlinx.coroutines.DelicateCoroutinesApi

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun StudentScanner (
    viewModel: ScannerViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    var attendanceResult by remember { mutableStateOf<String?>(null) }
    var isSuccess by remember { mutableStateOf(false) }
    // Collect the scanned QR code state
    val scannedQRCode by ScannedQRCodeHolder.scannedQRCodeHolder.collectAsState()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(android.Manifest.permission.CAMERA)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            if (hasCameraPermission) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithContent {
                            val canvasWidth = size.width
                            val canvasHeight = size.height
                            val cornerRadius = 16.dp.toPx()
                            val width = canvasWidth * 0.6f
                            val height = width * 2 / 2f

                            drawContent()

                            drawWithLayer {

                                // Destination
                                // This is transparent color
                                drawRect(Color.Black.copy(alpha = 0.9f))

                                // Source
                                // This is where we extract this rect from transparent
                                drawRoundRect(
                                    topLeft = Offset((canvasWidth - width) / 2, canvasHeight * .3f),
                                    size = Size(width, height),
                                    cornerRadius = CornerRadius(cornerRadius),
                                    color = Color.Transparent,
                                    blendMode = BlendMode.SrcIn
                                )
                            }

                            drawRoundRect(
                                topLeft = Offset((canvasWidth - width) / 2, canvasHeight * .3f),
                                size = Size(width, height),
                                cornerRadius = CornerRadius(cornerRadius),
                                color = Color.White,
                                style = Stroke(2.dp.toPx())
                            )
                        }
                ) {
                    AndroidView(
                        factory = {
                            val previewView = PreviewView(context)
                            val preview = Preview.Builder().build()
                            val selector = CameraSelector.Builder()
                                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                .build()
                            preview.setSurfaceProvider(previewView.surfaceProvider)

                            // Set the target resolution to a smaller square size
                            val targetResolution = android.util.Size(200, 200)
                            @Suppress("DEPRECATION")
                            val imageAnalysis = ImageAnalysis.Builder()
                                .setTargetResolution(targetResolution)
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build()
                            imageAnalysis.setAnalyzer(
                                ContextCompat.getMainExecutor(context),
                                QRCodeAnalyzer(viewModel) { qrCode ->
                                    // Handle the scanned QR code here
                                }
                            )

                            try {
                                cameraProviderFuture.get().bindToLifecycle(
                                    lifecycleOwner,
                                    selector,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            previewView
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 200.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        scannedQRCode?.let { qrCode ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = qrCode.subjectCode,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(250.dp)
                )
                Text(
                    text = qrCode.subjectName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(250.dp)
                )

                //For debug purposes
                Text(
                    text = "Sub ID: ${qrCode.subjectId}, QR Date: ${qrCode.date}, QR Time: ${qrCode.time}" ,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(250.dp)
                )

                // Display attendance result here
                attendanceResult?.let {
                    val color = if (isSuccess) Color.Green else Color.Red
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = color,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.width(250.dp)
                    )
                }
            }
        }
    }

    // Call validateAndInsertAttendance function here and update attendance result
    LaunchedEffect(scannedQRCode) {
        scannedQRCode?.let {
            val result = viewModel.validateAndInsertAttendance()
            isSuccess = when(result) {
                is AttendanceResult.Success -> true
                is AttendanceResult.Error -> false
            }
            attendanceResult = when(result) {
                is AttendanceResult.Success -> result.message
                is AttendanceResult.Error -> result.errorMessage
            }
        }
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