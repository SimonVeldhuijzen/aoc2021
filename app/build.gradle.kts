plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.20"
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

application {
    mainClass.set("aoc2021.AppKt")
}
