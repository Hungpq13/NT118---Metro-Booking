plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)

}

android {
    namespace = "com.example.nt118project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nt118project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps:google-maps-services:0.18.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("androidx.fragment:fragment:1.8.0")
    implementation(libs.appcompat.v170)
    implementation(libs.constraintlayout);
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
}