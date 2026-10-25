plugins {
    id("all")
}

dependencies {
    api(project(":cirrus-api"))

    compileOnly("net.kyori:adventure-api:5.2.0")
    compileOnly(libs.packetevents.api)

    testImplementation("net.kyori:adventure-api:5.2.0")
    testImplementation(libs.packetevents.api)
}