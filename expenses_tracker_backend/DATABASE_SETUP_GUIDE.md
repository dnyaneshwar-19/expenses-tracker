# Database Setup Guide for Expenses Tracker

## Issue Resolution

The error "Public Key Retrieval is not allowed" is a common MySQL connection issue. Here are the solutions:

## Solution 1: Updated MySQL Configuration (Recommended)

The application.properties file has been updated with the correct MySQL connection parameters:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expenses_tracker?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8
```

## Solution 2: Use H2 In-Memory Database (Alternative)

If MySQL is not available or you want to test quickly, you can use H2 database:

### Run with H2 Database:

```bash
mvnw.cmd spring-boot:run -Dspring.profiles.active=h2
```

This will use the H2 in-memory database instead of MySQL.

## Solution 3: MySQL Database Setup

### Prerequisites:

1. MySQL Server must be running
2. Database `expenses_tracker` must exist
3. User must have proper permissions

### Steps to create the database:

1. **Connect to MySQL:**

   ```sql
   mysql -u root -p
   ```

2. **Create the database:**

   ```sql
   CREATE DATABASE expenses_tracker;
   ```

3. **Grant permissions (if needed):**

   ```sql
   GRANT ALL PRIVILEGES ON expenses_tracker.* TO 'root'@'localhost';
   FLUSH PRIVILEGES;
   ```

4. **Verify the database exists:**
   ```sql
   SHOW DATABASES;
   ```

## Solution 4: Alternative MySQL Connection Parameters

If the current configuration doesn't work, try these alternative connection strings:

### Option A: With SSL enabled

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expenses_tracker?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

### Option B: With different SSL settings

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expenses_tracker?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false&verifyServerCertificate=false
```

## Running the Application

### Method 1: With MySQL (Default)

```bash
mvnw.cmd spring-boot:run
```

### Method 2: With H2 Database

```bash
mvnw.cmd spring-boot:run -Dspring.profiles.active=h2
```

### Method 3: With specific MySQL profile

```bash
mvnw.cmd spring-boot:run -Dspring.profiles.active=mysql
```

## Testing the Application

Once the application starts successfully, you can test it:

1. **Check if the application is running:**

   ```bash
   curl http://localhost:8080/api/data/status
   ```

2. **Initialize sample data:**

   ```bash
   curl -X POST http://localhost:8080/api/data/init
   ```

3. **Check data status:**
   ```bash
   curl http://localhost:8080/api/data/status
   ```

## Troubleshooting

### If MySQL connection still fails:

1. **Check MySQL service status:**

   - Windows: `services.msc` → MySQL service
   - Or: `net start mysql`

2. **Verify MySQL is running on port 3306:**

   ```bash
   netstat -an | findstr 3306
   ```

3. **Test MySQL connection manually:**
   ```bash
   mysql -u root -p -h localhost -P 3306
   ```

### If H2 database is used:

- Access H2 console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Application Endpoints

Once running, the application provides these endpoints:

- **Data Management:**

  - `POST /api/data/init` - Initialize sample data
  - `GET /api/data/status` - Check data status
  - `DELETE /api/data/clear` - Clear all data

- **User Management:**

  - `GET /api/users` - Get all users
  - `POST /api/users` - Create user
  - `GET /api/users/{id}` - Get user by ID

- **Expense Management:**
  - `GET /api/expenses` - Get all expenses
  - `GET /api/expenses/type/PERSONAL` - Get personal expenses
  - `GET /api/expenses/type/PROFESSIONAL` - Get professional expenses
  - `POST /api/expenses` - Create expense

## Next Steps

1. Choose your preferred database solution (MySQL or H2)
2. Run the application with the appropriate command
3. Test the endpoints using Postman or curl
4. Follow the POSTMAN_TESTING_GUIDE.md for comprehensive testing
