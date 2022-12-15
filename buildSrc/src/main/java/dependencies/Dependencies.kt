package dependencies

object Version {
    val kotlin ="1.3.50"
    val daggerHilt = "2.42"
    const val retrofit = "2.3.0"
    const val okhttp = "4.9.2"
    const val gson = "2.8.6"
    const val retrofitCoroutines = "0.9.2"

}
object Deps{
    val Android= object {
        val androidCore = "androidx.core:core-ktx:1.9.0"
        val appcompat ="androidx.appcompat:appcompat:1.5.1"
        val androidMaterial = "com.google.android.material:material:1.7.0"
        val androidContraintLayout= "androidx.constraintlayout:constraintlayout:2.1.4"
    }
    val dagger = object {
        val daggerHilt = "com.google.dagger:hilt-android:${Version.daggerHilt}"
        val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.daggerHilt}"
    }

    val RXJava = object {
        val rxAndroid = "io.reactivex.rxjava3:rxandroid:3.0.0"
        val retrofitAdapter = "com.github.akarnokd:rxjava3-retrofit-adapter:3.0.0"
        val rxJava3 = "io.reactivex.rxjava3:rxjava:3.0.0"
    }

    val Retrofit = object {
        val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
        val gson = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
    }

    val Gson = object {
        val gson = "com.google.code.gson:gson:${Version.gson}"
    }

    val OkHttp = object {
        val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
        val logging = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"
    }
    val Paging = object {
        val runtime = "androidx.paging:paging-runtime:3.1.1"
        val rxjava = "androidx.paging:paging-rxjava3:3.1.1"
    }

    val Mockk = object {
        val mockk = "io.mockk:mockk:1.13.3"
        val mockkAndroid = "io.mockk:mockk-android:1.13.3"
    }

    val Truth = object {
        val truth = "com.google.truth:truth:1.1.3"
    }

    val AndroidCoreTesting = object {
        val core = "androidx.arch.core:core-testing:2.1.0"
    }

    val Navigation = object {
        val navigationFragment = "androidx.navigation:navigation-fragment-ktx:2.5.3"
        val navigation = "androidx.navigation:navigation-ui-ktx:2.5.3"
    }

    val glide = object {
      val glide= "com.github.bumptech.glide:glide:4.14.2"
      val annotationProcessor= "com.github.bumptech.glide:compiler:4.14.2"
    }

    val Test = object {
        val junit ="junit:junit:4.13.2"
        val androidTest= "androidx.test.ext:junit:1.1.4"
        val expresso= "androidx.test.espresso:espresso-core:3.5.0"
    }

}