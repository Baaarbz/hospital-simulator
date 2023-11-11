plugins {
	id("java")
}

group = "com.barbzdev"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

dependencies {
	val slf4jVersion = "2.0.9"
	implementation("org.slf4j:slf4j-api:$slf4jVersion")
	implementation("org.slf4j:slf4j-simple:$slf4jVersion")

	testImplementation(platform("org.junit:junit-bom:5.9.1"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.mockito:mockito-junit-jupiter:5.7.0")
	testImplementation("org.mockito:mockito-inline:5.2.0")
}

tasks.test {
	useJUnitPlatform()
}
