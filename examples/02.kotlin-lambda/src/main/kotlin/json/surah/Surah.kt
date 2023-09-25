package json.surah

import kotlinx.serialization.Serializable

/*
To be able use @Serializable and Json class you need to add these dependencies then sync:
2) Add to dependencies of the 2nd (Module) build.gradle
    //Added for Kotlin Serialization
    implementation 'org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2'

3) Add this apply plugin to the 2nd build.gradle before line "android {"
    //Added for Kotlin Serialization
    id 'org.jetbrains.kotlin.plugin.serialization' version '1.5.30'
 */

@Serializable
data class Surah (
    val id : Int,
    val name: String,
    val englishName : String,
    val ayaCount : Int,
    val type: String
)