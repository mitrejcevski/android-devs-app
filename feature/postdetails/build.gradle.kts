plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "nl.jovmit.androiddevs.feature.postdetails"
    compileSdk = libs.versions.compileSdkVersion.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdkVersion.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        val javaVersion = libs.versions.javaVersion.get()
        sourceCompatibility = JavaVersion.toVersion(javaVersion)
        targetCompatibility = JavaVersion.toVersion(javaVersion)
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaVersion.get()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }

    testOptions.unitTests {
        isReturnDefaultValues = true
        all { tests ->
            tests.useJUnitPlatform()
            tests.testLogging {
                events("passed", "failed", "skipped")
            }
        }
    }
}

dependencies {
    implementation(projects.core.view)
    implementation(libs.bundles.hilt)

    kapt(libs.hilt.compiler)

    testImplementation(libs.bundles.unit.testing)

    testRuntimeOnly(libs.junit.jupiter.engine)
}