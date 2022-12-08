plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/nms/")
    maven("https://repo.unnamed.team/repository/unnamed-public/")
}

dependencies {
    implementation(project(":api"))
    implementation(libs.inject.trew)
    implementation(libs.commandflow.bukkit)
    implementation(libs.mongo.sync)
    implementation(libs.nmessage)
    implementation(libs.nmessage.yaml)
    compileOnly(libs.spigot) {
        exclude(group = "net.md-5", module = "bungeecord-chat")
    }
}
