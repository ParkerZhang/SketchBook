a work bench for ugrading avro 1.8.1 to its latest. 

Issue 1
    StringUtils was removed. 

Copilot works much better than any other peers

Learn more

 
You
Sent by you:
Apache commons lang removed from avro-tools 1.8.1
Copilot
Sent by Copilot:
It seems that Apache Commons Lang was indeed removed from Avro-tools version 1.8.1. This change might have been made to reduce dependencies or to streamline the tool’s functionality. If you’re encountering issues or need specific functionalities that were provided by Apache Commons Lang, you might need to manually include the library in your project.

If you have any specific questions or need further assistance with Avro-tools, feel free to ask!

Learn more

1

avro.apache.org
2

avro.apache.org
3

community.cloudera.com
You
Sent by you:
how to add it to pom.xml
Copilot
Sent by Copilot:
To add Apache Commons Lang to your pom.xml, you need to include the dependency for the library. Here’s how you can do it:

Open your pom.xml file.
Add the following dependency inside the <dependencies> section:
XML

<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.12.0</version> <!-- or the latest version -->
</dependency>
AI-generated code. Review and use carefully. More info on FAQ.
This will include Apache Commons Lang in your project, allowing you to use its utilities.

If you need further assistance or have any other questions, feel free to ask!