
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")

    id("com.google.gms.google-services")
//    id ("org.jetbrains.kotlin.kapt")

//    id ("com.android.library")

}

android {
    namespace = "com.ssoft.ews4thai"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ssoft.ews4thai"
        minSdk = 24
        targetSdk = 34
        versionCode = 11
        versionName = "1.3.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }



}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-analytics")

    implementation ("com.google.firebase:firebase-messaging:23.3.1")


    // Koin for Android DI
    implementation ("org.koin:koin-android-viewmodel:2.2.0")
    implementation ("org.koin:koin-androidx-ext:2.2.0")

    implementation ("id.zelory:compressor:3.0.1")
//    implementation 'id.zelory:compressor:3.0.1'
    implementation ("com.github.yalantis:ucrop:2.2.8")

//    implementation 'com.github.yalantis:ucrop:2.2.6-native'


    //Http
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version"


    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation("com.squareup.okhttp3:okhttp")
//    implementation("com.squareup.okhttp3:logging-interceptor")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")


    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //pernission
    implementation ("com.github.permissions-dispatcher:permissionsdispatcher:4.9.2")
    implementation(project(mapOf("path" to ":common")))
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    kapt ("com.github.permissions-dispatcher:permissionsdispatcher-processor:4.9.2")

//    implementation("com.mindorks.android:placeholderview:1.0.3")
//    kapt ("com.mindorks.android:placeholderview-compiler:1.0.3")
//    annotationProcessor ("com.mindorks.android:placeholderview-compiler:1.0.3")


//

    implementation("com.github.bumptech.glide:glide:4.11.0")
            implementation("androidx.work:work-runtime-ktx:2.7.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")


    implementation ("org.parceler:parceler-api:1.1.12")
    annotationProcessor ("org.parceler:parceler:1.1.12")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}