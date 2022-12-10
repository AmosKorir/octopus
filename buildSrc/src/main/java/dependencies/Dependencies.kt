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

}