import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val iosocketVersion: String by project
val javacompileVersion: String by project

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow")
    application
    id("org.openjfx.javafxplugin")
}

java {
    // Version de compatibilité Java 11 (pour Temurin 11)
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("de.jensd:fontawesomefx:8.9")
    implementation("io.socket:socket.io-client:${iosocketVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")

    implementation("org.openjfx:javafx-base:${javacompileVersion}")
    implementation("org.openjfx:javafx-graphics:${javacompileVersion}")
    implementation("org.openjfx:javafx-controls:${javacompileVersion}")
    implementation("org.openjfx:javafx-web:${javacompileVersion}")
//    implementation("org.openjfx:javafx-media:${javacompileVersion}")
    implementation("org.openjfx:javafx-fxml:${javacompileVersion}")


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("WindowsAfk")
}

tasks {
    // Pour compiler et empaqueter tout en un seul fichier JAR exécutable
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("SmartFish")
        archiveClassifier.set("")
        archiveVersion.set("1.0.0") // TODO : Remplacer par la propriété de version
        mergeServiceFiles() // Si tu as des fichiers META-INF à fusionner
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


javafx {
    // Modules JavaFX utilisés
    version = javacompileVersion
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.web")
}
