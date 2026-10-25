plugins {
    id("all")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")
    compileOnly(libs.packetevents.spigot)

    api(project(":cirrus-common"))
}