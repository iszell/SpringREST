# BOM (Bill Of Materials) for framework based developments

# Contents
* Lombok
* MapStruct
* OpenAPI 3 (Swagger) annotations
* Spring Boot 3.0.6
* Spring Cloud 2022.0.2

# Usage
Add the following lines to the `pom.xml` of your project
```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>hu.siz.framework</groupId>
                <artifactId>bom</artifactId>
                <version>1.0.0</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

# Maven repository
Setup your Maven `settings.xml` like this
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
        </repository>
        <repository>
          <id>github</id>
          <url>https://maven.pkg.github.com/mcbrigade/bom</url>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
  </profiles>

  <servers>
    <server>
      <id>github</id>
      <username><your GitHub username></username>
      <password><your GitHub access token></password>
    </server>
  </servers>
</settings>
```

See setup at https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry