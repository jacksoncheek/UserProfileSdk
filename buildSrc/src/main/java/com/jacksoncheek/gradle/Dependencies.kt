package com.jacksoncheek.gradle

object Projects {
    val app = ":user-profile-sdk-sample-app"
    val builderCommon = ":user-profile-sdk-builder-common"
    val builderDev = ":user-profile-sdk-builder-dev"
    val builderProd = ":user-profile-sdk-builder-prod"
    val core = ":user-profile-sdk-core"
    val ui = ":user-profile-sdk-ui"
    val common = ":user-profile-sdk-common"
}

object Versions {
    // Build
    val minSdk = 21
    val targetSdk = 28
    val compileSdk = 28
    val buildTools = "27.0.3"
    val sourceCompat = "1.7"
    val targetCompat = "1.7"
    val versionCode = 1
    val versionName = "1.0"

    // Libs
    val kotlin = "1.3.21"
    val kotlinCoroutines = "1.2.1"
    val kotlinCoroutinesAdapter = "0.9.2"
    val okHttp3 = "3.10.0"
    val retrofit = "2.4.0"
    val moshi = "1.5.0"
    val multidex = "1.0.2"
    val supportLibrary = "28.0.0"
    val kotlinKtx = "0.2"
    val constraintLayout = "1.1.3"
    val playServces = "11.4.0"
    val jUnit = "4.12"
    val mockito = "2.21.0"
    val mockitoKotlin = "2.1.0"
    val assertJCore = "1.7.0"
    val chuck = "1.1.0"
    val architectureComponents = "1.1.1"
    val androidGradlePlugin = "3.1.2"
    val espresso = "3.0.2"
    val hamcrest = "1.3"
    val testRules = "1.0.2"
    val androidTestingSupportLib = "1.0.2"
    val okHttp3IdlingResource = "1.0.0"
    val orgJson = "20180130"
    val picasso = "2.71828"
    val glide = "4.9.0"
}

object Libs {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
    val kotlinCoroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.kotlinCoroutinesAdapter}"
    val okHttp3 = "com.squareup.okhttp3:okhttp:${Versions.okHttp3}"
    val okHttp3LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp3}"
    val okHttp3MockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp3}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitConverterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    val json = "org.json:json:20090211"
    val multidex = "com.android.support:multidex:${Versions.multidex}"
    val supportCompat = "com.android.support:support-compat:${Versions.supportLibrary}"
    val supportCoreUtils = "com.android.support:support-core-utils:${Versions.supportLibrary}"
    val supportPercent = "com.android.support:percent:${Versions.supportLibrary}"
    val supportAnnotations = "com.android.support:support-annotations:${Versions.supportLibrary}"
    val supportFragment = "com.android.support:support-fragment:${Versions.supportLibrary}"
    val supportV4 = "com.android.support:support-v4:${Versions.supportLibrary}"
    val supportV13 = "com.android.support:support-v13:${Versions.supportLibrary}"
    val supportAppCompat = "com.android.support:appcompat-v7:${Versions.supportLibrary}"
    val supportRecyclerView = "com.android.support:recyclerview-v7:${Versions.supportLibrary}"
    val supportCardView = "com.android.support:cardview-v7:${Versions.supportLibrary}"
    val supportGridLayout = "com.android.support:gridlayout-v7:${Versions.supportLibrary}"
    val supportDesign = "com.android.support:design:${Versions.supportLibrary}"
    val supportPreferenceV7 = "com.android.support:preference-v7:${Versions.supportLibrary}"
    val supportConstraintLayout = "com.android.support.constraint:constraint-layout:${Versions.constraintLayout}"
    val kotlinKtx = "androidx.core:core-ktx${Versions.kotlinKtx}"
    val playServicesBase = "com.google.android.gms:play-services-base:${Versions.playServces}"
    val playServicesAuth = "com.google.android.gms:play-services-auth:${Versions.playServces}"
    val playservicesGcm = "com.google.android.gms:play-services-gcm:${Versions.playServces}"
    val playServicesLocation = "com.google.android.gms:play-services-location:${Versions.playServces}"
    val playServicesAds = "com.google.android.gms:play-services-ad:${Versions.playServces}"
    val chuck = "com.readystatesoftware.chuck:library:${Versions.chuck}"
    val chuckNoOp = "com.readystatesoftware.chuck:library-no-op:${Versions.chuck}"
    val orgJson = "org.json:json:${Versions.orgJson}"
    val archComponentViewModel = "android.arch.lifecycle:viewmodel:${Versions.architectureComponents}"
    val archComponentLiveData = "android.arch.lifecycle:lifedata:${Versions.architectureComponents}"
    val archComponentLifecycleRuntime = "android.arch.lifecycle:runtime:${Versions.architectureComponents}"
    val archComponentLifecycleExtensions = "android.arch.lifecycle:extensions:${Versions.architectureComponents}"
    val archComponentLifecycleCompiler = "android.arch.lifecycle:compiler:${Versions.architectureComponents}"
    val archComponentRoomRuntime = "android.arch.persistence.room:runtime:${Versions.architectureComponents}"
    val archComponentRoomCompiler = "android.arch.persistence.room:compiler:${Versions.architectureComponents}"
    val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
}

object TestLibs {
    val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    val okHttpIdlingResource = "com.jakewharton.espresso:okhttp3-idling-resource:${Versions.okHttp3IdlingResource}"
    val assertJCore = "org.assertj:assertj-core:${Versions.assertJCore}"
    val hamcrest = "org.hamcrest:hamcrest-library:${Versions.hamcrest}"
    val jUnit = "junit:junit:${Versions.jUnit}"
    val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    val mockitoAndroid = "org.mockito:mockito-android:${Versions.mockito}"
    val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    val mockitoInline = "org.mockito:mockito-inline:${Versions.mockito}"
    val espresso = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    val espressoIntents = "com.android.support.test.espresso:espresso-intents:${Versions.espresso}"
    val androidTestRunner = "com.android.support.test:runner:${Versions.androidTestingSupportLib}"
    val androidTestOrchstrator = "com.android.support.test:orchestrator:${Versions.androidTestingSupportLib}"
    val testRules = "com.android.support.test:rules:${Versions.testRules}"
    val archComponentCoreTesting = "android.arch.core:core-testing:${Versions.architectureComponents}"
    val archComponentTesting = "android.arch.persistence.room:testing:${Versions.architectureComponents}"
}

object GradlePlugins {
    val android = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
}

object Modules {
    val supportAnnotations = mapOf(
            GROUP to "com.android.support",
            MODULE to "support-annotations"
    )

    val supportV4 = mapOf(
            GROUP to "com.android.support",
            MODULE to "support-v4"
    )
}

private const val GROUP = "group"
private const val MODULE = "module"