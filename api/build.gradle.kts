plugins {
    `java-library`
}

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/nms/")
}

dependencies {
    api(libs.annotations)
    compileOnly(libs.spigot) {
        exclude(group = "net.md-5", module = "bungeecord-chat")
    }
}
