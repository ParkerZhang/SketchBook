To build a runable Jar with Maven

pom.xml
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-jar-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <addClasspath>true</addClasspath>
                            <classpathPrefix>${project.build.finalName}.lib/</classpathPrefix>
                            <mainClass>sketch.anatomy.Main</mainClass>
                        </manifest>
                    </archive>
                </configuration>
        </plugin>


Build: 
    mvn clean package

Run:
    java -jar target/Jar-1.0-jar-with-dependencies.jar
    Hello Jar!

Note: 
    Maven overrides MANIFEST.MF in project folders 