# Bug Tracking System

An IDE-independent Bug Tracking System built with standard Java (Swing) and JDBC.

## Features
- Standard folder structure, no IDE-specific configuration files (`.idea`, `.project`, etc.). 
- Runs seamlessly anywhere with a standard `javac` and `java` setup.
- Designed for MySQL Database integration.

## How to Run
1. **Install Java**: Ensure you have a standard JDK installed (Java 8 or higher).
2. **Add MySQL**:
   - Download the MySQL Connector/J (`mysql-connector.jar`).
   - Place the downloaded JDBC JAR inside the `lib/` directory of this project and rename it to `mysql-connector.jar` (or update instructions accordingly).
   - Set up your MySQL Database and update credentials in `src/DBConnection.java`.
3. **Compile the App**:
   From the project root folder, run:
   ```shell
   javac -cp ".;lib/mysql-connector.jar" src/*.java
   ```
4. **Run Main.java OR jar file**:
   Launch the software using:
   ```shell
   java -cp ".;lib/mysql-connector.jar;src" Main
   ```

## Exporting as an Executable JAR
If you are using an IDE, you can easily export the project as a Runnable JAR:
- **IntelliJ IDEA**: File -> Project Structure -> Artifacts -> Add JAR -> From modules with dependencies
- **Eclipse / NetBeans**: Export -> Runnable JAR file

Then run the software using:
```shell
java -jar bug-tracker.jar
```
