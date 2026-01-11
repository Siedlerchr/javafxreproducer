plugins {
    java
    application
    id("org.gradlex.extra-java-module-info") version "1.13.1"
    id("org.gradlex.jvm-dependency-conflict-resolution") version "2.5"
    id("org.gradlex.java-module-dependencies") version "1.11"
    id("org.gradlex.java-module-packaging") version "1.2"
}

group = "org.jabreftest.test"

// "1.0-SNAPSHOT" cannot be used; otherwise we get
//   java.lang.IllegalArgumentException: Version [1.0-SNAPSHOT] contains invalid component [0-SNAPSHOT]
// 0.1.0 cannaot be used; otherwise we get
//   Bundler Mac Application Image skipped because of a configuration problem: The first number in an app-version cannot be zero or negative.
version = "1.0.0"

repositories {
    mavenCentral()
}

val javafx = "25.0.1"
val junitVersion = "6.0.2"

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(25))
  }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("javafx.reproducer")
    mainClass.set("org.jabreftest.test.javafxreproducer.HelloApplication")
}

dependencies {
    implementation("org.openjfx:javafx-controls:$javafx")
    implementation("org.openjfx:javafx-fxml:$javafx")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
}

jvmDependencyConflicts.patch {
    listOf("base", "controls", "fxml", "graphics").forEach { jfxModule ->
        module("org.openjfx:javafx-$jfxModule") {
            addTargetPlatformVariant("", "none", "none") // matches the empty Jars: to get better errors
            addTargetPlatformVariant("linux", OperatingSystemFamily.LINUX, MachineArchitecture.X86_64)
            addTargetPlatformVariant("linux-aarch64", OperatingSystemFamily.LINUX, MachineArchitecture.ARM64)
            addTargetPlatformVariant("mac", OperatingSystemFamily.MACOS, MachineArchitecture.X86_64)
            addTargetPlatformVariant("mac-aarch64", OperatingSystemFamily.MACOS, MachineArchitecture.ARM64)
            addTargetPlatformVariant("win", OperatingSystemFamily.WINDOWS, MachineArchitecture.X86_64)
        }
    }
}

extraJavaModuleInfo {
    failOnAutomaticModules = true
    failOnModifiedDerivedModuleNames = true
    skipLocalJars = true
}

// Source: https://github.com/jjohannes/java-module-system/blob/main/gradle/plugins/src/main/kotlin/targets.gradle.kts
// Configure variants for OS. Target name can be any string, but should match the name used in GitHub actions.
javaModulePackaging {
    // Configuration shared by all targets and applications
    vendor = "JabRef"
    jlinkOptions.addAll(
        "--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"
        // "--ignore-signing-information",
        // "--compress", "zip-6",
        // "--no-header-files",
        // "--no-man-pages",
        // "--bind-services",
    )

    target("ubuntu-22.04") {
        operatingSystem = OperatingSystemFamily.LINUX
        architecture = MachineArchitecture.X86_64
        packageTypes = listOf("app-image", "deb", "rpm")
    }
    target("ubuntu-22.04-arm") {
        operatingSystem = OperatingSystemFamily.LINUX
        architecture = MachineArchitecture.ARM64
        packageTypes = listOf("app-image", "deb", "rpm")
    }
    target("macos-13") {
        operatingSystem = OperatingSystemFamily.MACOS
        architecture = MachineArchitecture.X86_64
        packageTypes = listOf("app-image", "dmg", "pkg")
        singleStepPackaging = true
    }
    target("macos-14") {
        operatingSystem = OperatingSystemFamily.MACOS
        architecture = MachineArchitecture.ARM64
        packageTypes = listOf("app-image", "dmg", "pkg")
        singleStepPackaging = true
    }
    target("windows-latest") {
        operatingSystem = OperatingSystemFamily.WINDOWS
        architecture = MachineArchitecture.X86_64
        packageTypes = listOf("app-image", "msi")
    }
}
