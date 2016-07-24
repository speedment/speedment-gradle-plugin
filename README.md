# Speedment Gradle plugin

[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Plugin adds Gradle tasks to run Speedment GUI and to generate source files from configuration.

## Getting Started

Add and apply plugin in your project's `build.gradle` file.


````groovy
buildscript {
    repositories {
        maven {
            url 'https://plugins.gradle.org/m2/'
        }
    }
    dependencies {
        classpath 'gradle.plugin.com.speedment.gradle:SpeedmentGradlePlugin:2.3.5'
        // Optional dependency to library with custom components.
        // classpath files('custom-components.jar')
    }
}


//ext {
//    Optional path to config file. Default is: src/main/json/speedment.json
//    speedmentConfigFile = '/path/to/my/config/file.json'
//
//    Optional list of components. See http://www.ageofjava.com/2016/04/how-to-generate-customized-java-8-code.html
//    speedmentComponentConstructors = [
//            new CustomComponentConstructor()
//    ]
//}

apply plugin: 'com.speedment.gradle'

// ...

dependencies {
    compile('com.speedment:speedment:2.3.5')
}
````

Then call one of two new targets.

* `gradle speedment.Gui` Opens Speedment GUI.
* `gradle speedment.Generate` Generates Java classes from config file.

## License

Speedment is available under the Apache 2 License.

## Copyright

Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.

Visit [www.speedment.com](http://www.speedment.com/) for more info.
