plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.1"
}

group = "de.mickymaus209"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    mavenLocal()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    implementation ("com.zaxxer:HikariCP:3.4.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}


tasks {
    jar {
        destinationDir =
            file("C:\\Users\\janbu\\iCloudDrive\\Development\\Java\\Minecraft\\Servers\\Minecraft Test-Server 1.8.8\\plugins")
    }
}