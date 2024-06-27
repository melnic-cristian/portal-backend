# README for Simple Library Management Portal Backend

## Description
This is the backend application for the online library management portal, built using Grails 6.2.0. The application manages data related to books, authors and genres for the efficient operation of the online library.

## System Requirements
- Grails 6.2.0
- Java 11 or higher
- MySQL 8.0 or a compatible version
- Gradle for dependency management

## Setup Instructions

### 1. Install Dependencies
Ensure you have the following installed:
- [Grails 6.2.0](https://grails.org/download.html)
- [Java Development Kit (JDK) 11](https://adoptopenjdk.net/)
- [MySQL 8.0](https://dev.mysql.com/downloads/mysql/)

### 2. Configure MySQL
Create a database for the application:
```sql
CREATE DATABASE online_library;
CREATE USER 'library_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON online_library.* TO 'library_user'@'localhost';
FLUSH PRIVILEGES; 
```

### 3. Configure the Application
Update the application.yml file located in grails-app/conf/ with your database connection details:

```yaml copyCode
dataSource:
pooled: true
jmxExport: true
driverClassName: com.mysql.cj.jdbc.Driver
dialect: org.hibernate.dialect.MySQL8Dialect
username: library_user
password: password
url: jdbc:mysql://localhost:3306/online_library
```

### 4. Run the Application

Navigate to the root directory of the application and execute the following command to run the application:

```bash copyCode
./gradlew bootRun
```

The application should be accessible at http://localhost:8081.

### Troubleshooting
- Ensure MySQL is running and accessible.
- Verify that the database credentials in application.yml are correct.
- Check for any missing dependencies and ensure all required versions are installed.

### Additional Notes

If you need to change the default port (8081), update the application.yml with the desired port configuration:

```yaml copyCode
server: 
  port: 8081
```
