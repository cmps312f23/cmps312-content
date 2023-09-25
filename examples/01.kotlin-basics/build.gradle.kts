plugins {
    kotlin("jvm") version "1.9.0"
}

group = "qa.edu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

kotlin {
    jvmToolchain(8)
}