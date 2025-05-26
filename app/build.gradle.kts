plugins {
     alias(libs.plugins.android.application)
     alias(libs.plugins.kotlin.android)
     alias(libs.plugins.kotlin.compose)

     id("com.google.dagger.hilt.android")
     alias(libs.plugins.ksp)
     alias(libs.plugins.kotlin.serialization)
}

android {
     namespace = "com.example.mystatisticsapp"
     compileSdk = 35

     defaultConfig {
          applicationId = "com.example.mystatisticsapp"
          minSdk = 26
          targetSdk = 35
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
     compileOptions {
          sourceCompatibility = JavaVersion.VERSION_11
          targetCompatibility = JavaVersion.VERSION_11
     }
     kotlinOptions {
          jvmTarget = "11"
     }
     buildFeatures {
          compose = true
     }
}

dependencies {
     implementation(libs.androidx.core.ktx)
     implementation(libs.androidx.lifecycle.runtime.ktx)
     implementation(libs.androidx.activity.compose)
     implementation(platform(libs.androidx.compose.bom))
     implementation(libs.androidx.ui)
     implementation(libs.androidx.ui.graphics)
     implementation(libs.androidx.ui.tooling.preview)
     implementation(libs.androidx.material3)
     testImplementation(libs.junit)
     androidTestImplementation(libs.androidx.junit)
     androidTestImplementation(libs.androidx.espresso.core)
     androidTestImplementation(platform(libs.androidx.compose.bom))
     androidTestImplementation(libs.androidx.ui.test.junit4)
     debugImplementation(libs.androidx.ui.tooling)
     debugImplementation(libs.androidx.ui.test.manifest)

     implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")

     implementation("androidx.navigation:navigation-compose:2.8.4")

     implementation("io.coil-kt.coil3:coil:3.2.0")
     implementation("io.coil-kt.coil3:coil-compose:3.2.0")
     implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")

     implementation ("com.google.code.gson:gson:2.11.0")
     implementation ("com.squareup.retrofit2:retrofit:2.11.0")
     implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
     implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
     implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

     implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
     implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

     implementation("com.google.dagger:hilt-android:2.56.1")
     implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
     ksp("com.google.dagger:hilt-android-compiler:2.56.1")
}