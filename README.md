# DMX-Lighting

### Introduction

A project made with Spring Boot & Angular for recording and playing lightscenes through the Art-Net/DMX protocol.
The purpose of this project is to be deployed on a custom Raspberry Pi, with physical buttons. 

[![Schets.png](https://i.postimg.cc/6q9tGFf1/Schets.png)](https://postimg.cc/YGyJ5nV1)
[![front.png](https://i.postimg.cc/43J5q5wR/front.png)](https://postimg.cc/9DnZqyvL)

### Deployment

If you wish to run the project on your computer for testing, follow these instructions:

#### Angular

Make sure npm is installed on your computer.
If you are deploying for the first time, run this command in the client folder:

```
npm install
```

After running previous command, you can start the running the Angular application with this command:

```
npm start
```

After this is completed, the Angular application should be running at http://localhost:4200/<br>
Note: the Angular application won't work properly without running the Spring Boot backend first.<br> 
Follow the instructions below.

#### Spring Boot

**Warning**<br>
To make sure the directories for scenes and settings are created on your computer, make sure the parentDir variable in 
*DMX-Lighting/server/src/main/java/lighting/server/IO/IOServiceImpl.java* is set to:

```java
private Path parentDir = Paths.get(System.getProperty("user.dir"));
```
You can replace the "user.dir" property with another location on your drive.
<br>
<br>
After this, run following command in the server folder:
```
mvn spring-boot:run
```

This will create the scenes and settings directories in the server directory that you ran previous command, unless you have changed the parentDir variable.



