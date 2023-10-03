package model

import kotlinx.serialization.Serializable

/*
To be able use @Serializable and Json class you need to add these dependencies then sync:
1) Add to dependencies of the Module build.gradle
    //Added for Kotlin Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

2) Add this apply plugin to the module build.gradle
    //Added for Kotlin Serialization
    id 'org.jetbrains.kotlin.plugin.serialization' version '1.5.30'
 */

@Serializable
data class Surah (
    val id : Int,
    val name: String,
    val englishName : String,
    val ayaCount : Int,
    val type: String = "Meccan"
)