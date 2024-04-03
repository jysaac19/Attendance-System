plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.attendanceapp2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.attendanceapp2"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Icons
    implementation("androidx.compose.material:material-icons-extended:1.6.4")
    implementation("com.google.firebase:firebase-inappmessaging-ktx:20.4.1")
    // Navigation
//    val nav_version = "2.7.4" implemment $nav_version
    implementation("androidx.navigation:navigation-compose:2.7.7")
    // Splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
    //==========================================
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
    implementation("androidx.compose.ui:ui-graphics-android:1.6.4")
    implementation("com.google.android.engage:engage-core:1.4.0")
    implementation("com.google.android.gms:play-services-mlkit-subject-segmentation:16.0.0-beta1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //external implementations
    implementation ("androidx.compose.foundation:foundation:1.6.4")
    implementation ("androidx.compose.foundation:foundation-layout:1.6.4")
    implementation ("androidx.compose.ui:ui-tooling:1.6.4")
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.4")

    //Material 3 Core
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    //Calendar
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")
    //Clock
    implementation ("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")
    //Swipe
    implementation("me.saket.swipe:swipe:1.2.0")

    val room_version = "2.6.1"
    //Room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.core:core-ktx:1.12.0")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation("androidx.room:room-common:$room_version")
    implementation("androidx.room:room-runtime:$room_version")

    //Data Converter
    implementation ("com.google.code.gson:gson:2.10.1")

    //live clock
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //service
    implementation ("androidx.work:work-runtime:2.9.0")

    //send email
    implementation ("com.sun.mail:android-mail:1.6.5")
    implementation ("com.sun.mail:android-activation:1.6.5")

    //dropdown
    implementation ("me.saket.cascade:cascade-compose:2.0.0-beta1")

    //CameraX
    implementation("androidx.camera:camera-camera2:1.3.2")
    implementation("androidx.camera:camera-lifecycle:1.3.2")
    implementation("androidx.camera:camera-view:1.3.2")

    val camerax_version = "1.3.0-alpha04"
    implementation ("com.google.mlkit:barcode-scanning:17.2.0")

    //Zxing
    implementation("com.google.zxing:core:3.4.0")
    implementation ("com.journeyapps:zxing-android-embedded:4.2.0")
}