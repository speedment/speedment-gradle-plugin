
[![Join the chat at https://gitter.im/speedment/speedment](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/speedment/speedment?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

# speedment-maven-plugin
This small plugin makes it possible to run Speedment as a Maven goal in your IDE. All you have to do is add the following code to your projects ```pom.xml```-file:
```xml
<plugin>
    <artifactId>speedment-maven-plugin</artifactId>
    <groupId>com.speedment</groupId>
    <version>${speedment.version}</version>
</plugin>
```

This will add two new goals, one that launches the Speedment GUI and one that generates code from a ```.groovy```-file.

![Screenshot from the IDE](http://frslnd.se/github/illustrations/speedment_maven_goals.png)

### License

Speedment is available under the Apache 2 License.


### Copyright

Copyright (c) 2008-2015, Speedment, Inc. All Rights Reserved.

Visit [www.speedment.com](http://www.speedment.com/) for more info.

[![Analytics](https://ga-beacon.appspot.com/UA-64937309-1/speedment-maven-plugin/main)](https://github.com/igrigorik/ga-beacon)
