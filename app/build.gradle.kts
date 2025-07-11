plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.graph)
}

android {
    namespace = "com.example.cashpulse"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cashpulse"
        minSdk = 26
        targetSdk = 36
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

    // Configure packaging to avoid conflicts
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation(projects.core.navigation)
    implementation(projects.core.network)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.ui)

    implementation(projects.feature.account.ui)
    implementation(projects.feature.account.data)
    implementation(projects.feature.account.domain)

    implementation(projects.feature.category.ui)
    implementation(projects.feature.category.data)
    implementation(projects.feature.category.domain)

    implementation(projects.feature.expenses.ui)
    implementation(projects.feature.expenses.data)
    implementation(projects.feature.expenses.domain)

    implementation(projects.feature.incomes.ui)
    implementation(projects.feature.incomes.data)
    implementation(projects.feature.incomes.domain)

    implementation(projects.feature.settings.ui)

    implementation(libs.dagger.dagger)
    kapt(libs.dagger.compiler)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Test dependencies
    testImplementation(libs.junit)

    // Android test dependencies
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug dependencies
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}