import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

val version: String by project
val iosocketVersion: String by project
val javacompileVersion: String by project

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    application
//    id("org.openjfx.javafxplugin")
}

java {
    // Version de compatibilité Java 11 (pour Temurin 11)
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("de.jensd:fontawesomefx:8.9")
    implementation("io.socket:socket.io-client:${iosocketVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("WindowsAfk")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("SmartFish")
        archiveClassifier.set("")
        archiveVersion.set(version)
        manifest {
            attributes(
                "Main-Class" to application.mainClass.get()
            )
        }
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.withType<KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)  // Cible Java 8
    }
}