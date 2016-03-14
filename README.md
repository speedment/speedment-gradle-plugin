
[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

# speedment-gradle-plugin
This small plugin makes it possible to run Speedment as a Maven goal in your IDE. All you have to do is add the following code to your projects ```build.gradle```-file:
```xml
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.speedment.gradle:SpeedmentGradlePlugin:2.2.3"
  }
}

apply plugin: "com.speedment.gradle"
```

This will add two new goals, one that launches the Speedment GUI and one that generates code from a ```.groovy```-file.


### License

Speedment is available under the Apache 2 License.


### Copyright

Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.

Visit [www.speedment.com](http://www.speedment.com/) for more info.
