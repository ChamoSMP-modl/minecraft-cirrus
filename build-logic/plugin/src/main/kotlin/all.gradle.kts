plugins {
    id("java-library")
    id("maven-publish")
}

group = "net.chamosmp.cirrus"
version = "5.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25

    withSourcesJar()
}

repositories {
    mavenCentral()
    maven(url = "https://libraries.minecraft.net")
    maven(url = "https://repo.codemc.io/repository/maven-releases/")
    maven(url = "https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("org.jspecify:jspecify:1.0.0")

    lib("com.google.guava:guava:33.7.2-jre")
    lib("com.google.code.gson:gson:2.15.0")

    compileOnly("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.projectlombok:lombok:1.18.48")


    compileOnly("org.slf4j:slf4j-api:1.8.0-beta4")

    compileOnly("com.mojang:authlib:1.5.21")

    testImplementation("org.slf4j:slf4j-api:1.8.0-beta4")

    testImplementation(platform("org.junit:junit-bom:6.1.3"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

fun DependencyHandlerScope.lib(value: String) {
    compileOnly(value)
    testImplementation(value)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "ChamoSMP-Releases"
            url = uri("https://maven.chamosmp.net/releases")
            credentials {
                username = System.getenv("REPOSILITE_USER")
                password = System.getenv("REPOSILITE_TOKEN")
            }
        }
    }
}