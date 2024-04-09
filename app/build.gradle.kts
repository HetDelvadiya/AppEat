plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.awcindia.myapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.awcindia.myapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {



    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.facebook.android:facebook-android-sdk:16.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.firebase.database.ktx)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
    implementation("com.google.firebase:firebase-storage")
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.facebook.android:facebook-login:latest.release")
    implementation ("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation ("com.facebook.android:facebook-login:16.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-auth")
    implementation("androidx.core:core-ktx:1.12.0")


    implementation(libs.firebase.auth)
    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.facebook.android:facebook-android-sdk:latest.release")

    // glide and imageslider
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.car.ui.lib)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    // Default Dependency
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}