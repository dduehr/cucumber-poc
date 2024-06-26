plugins {
	id("org.springframework.boot") version "3.3.1"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.h2database:h2")
	testImplementation("io.cucumber:cucumber-java:7.18.0")
	testImplementation("io.cucumber:cucumber-junit:7.18.0")
	testImplementation("io.cucumber:cucumber-spring:7.18.0")
	testImplementation("io.cucumber:cucumber-junit-platform-engine:7.18.0")
	testImplementation("io.rest-assured:rest-assured:5.4.0")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testRuntimeOnly("org.junit.platform:junit-platform-console")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks {
	val consoleLauncherTest by registering(JavaExec::class) {
		dependsOn(testClasses)
		doFirst {
			println("Running parallel test")
		}
		classpath = sourceSets["test"].runtimeClasspath
		mainClass.set("org.junit.platform.console.ConsoleLauncher")
		args("--include-engine", "cucumber")
		args("--details", "tree")
		args("--scan-classpath")

		systemProperty("cucumber.execution.parallel.enabled", true)
		systemProperty("cucumber.execution.parallel.config.strategy", "dynamic")
		systemProperty(
			"cucumber.plugin",
			"pretty, summary, timeline:build/reports/timeline, html:build/reports/cucumber.html"
		)
		systemProperty("cucumber.publish.quiet", true)
	}

	test {
		dependsOn(consoleLauncherTest)
		exclude("**/*")
	}
}
