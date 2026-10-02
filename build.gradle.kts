plugins {
    id("java")
}

group = "seedfinding.com"
version = "1.0-SNAPSHOT"

// ---------------------------------------------------------------------------
// Java version
//
// dev.xpple:cubiomes is compiled for Java 23 (class file 67), and its Gradle
// metadata declares org.gradle.jvm.version = 23. If the Gradle daemon runs on
// an older JVM, Gradle silently filters that dependency out of the compile
// classpath, which shows up in the IDE as "cannot resolve symbol dev".
// Pinning a toolchain makes the build independent of whatever JVM launched it.
// ---------------------------------------------------------------------------
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release = 25
}

tasks.withType<Javadoc>().configureEach {
    // The generated binding Javadoc is huge; never fail the build on it.
    (options as StandardJavadocDocletOptions).addBooleanOption("Xdoclint:none", true)
}

repositories {
    mavenCentral()

    // Faster / more reliable mirror for the cubiomes binding specifically.
    // Scoped to the dev.xpple group so nothing else is affected. Remove this
    // block if you prefer to resolve everything from Maven Central directly.
    maven("https://maven.aliyun.com/repository/public") {
        content { includeGroup("dev.xpple") }
    }

    maven("https://maven.seedfinding.com")
    maven("https://maven.xpple.dev/maven2")

    // Fallback for "fix-cubiomes.ps1": a plain file dependency needs no repo,
    // but declaring a local flat dir keeps the artifact resolvable if you would
    // rather publish the jar into a local maven layout later.
    flatDir { dirs("libs") }
}

dependencies {
    implementation("com.seedfinding:mc_math:1.171.0")
    implementation("com.seedfinding:mc_seed:1.171.1")
    implementation("com.seedfinding:mc_core:1.210.0")
    implementation("com.seedfinding:mc_noise:1.171.1")
    implementation("com.seedfinding:mc_biome:1.171.1")
    implementation("com.seedfinding:mc_terrain:1.171.1")
    implementation("com.seedfinding:mc_reversal:1.171.1")
    implementation("com.seedfinding:mc_feature:1.171.10")

    // Normal path. If the jar cannot be downloaded (see fix-cubiomes.ps1),
    // replace this line with:
    //     implementation(files("libs/cubiomes-0.0.0-2026.9.22.1790089484+18edd56575a60fe7129705bf972de0a511437527.jar"))
    implementation("dev.xpple:cubiomes:0.0.0-2026.9.22.1790089484+18edd56575a60fe7129705bf972de0a511437527")
}

tasks.test {
    useJUnitPlatform()
}
