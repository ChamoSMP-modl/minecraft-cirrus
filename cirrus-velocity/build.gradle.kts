plugins {
    id("all")
}

dependencies {
    compileOnly("com.velocitypowered:velocity-api:4.3.0")
    compileOnly(libs.packetevents.velocity)

    api(project(":cirrus-common"))
}