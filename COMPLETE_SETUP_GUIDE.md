# Expenses Tracker - Complete Setup & Testing Guide

## Overview

This is a full-stack expense tracking application with:
- **Backend**: Spring Boot (Java 17) with MySQL
- **Frontend**: React with modern UI/UX
- **Features**: Authentication, Expenses, Budgets, Groups, Notifications, Reports

---

## Prerequisites

### Required Software
- **Java 17** or higher
- **Maven** (included via mvnw)
- **Node.js 16+** and npm
- **MySQL 8.0+**

---

## Backend Setup

### 1. Database Configuration

Create MySQL database:
```sql
CREATE DATABASE expenses_tracker;
```

### 2. Configure Application Properties

File: `expenses_tracker_backend/src/main/resources/application.properties`

Update these lines with your MySQL credentials:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3. Start Backend

```bash
cd expenses_tracker_backend
mvnw.cmd spring-boot:run
```

Backend will start on: `http://localhost:8083`

### 4. Verify Backend

Open browser: `http://localhost:8083/api/data/status`

You should see: `"Users: 0, Expenses: 0"`

---

## Frontend Setup

### 1. Install Dependencies

```bash
cd expenses_tracker_frontend
npm install
```

### 2. Start Frontend

```bash
npm start
```

Frontend will open automatically at: `http://localhost:3000`

---

## Quick Start Testing

### Option A: Use Sample Data

1. **Initialize Sample Data**
   - Open Postman or browser
   - POST to: `http://localhost:8083/api/data/init`
   - This creates 2 sample users and expenses

2. **Login with Sample User**
   - **Problem**: Sample users have plaintext passwords (won't work)
   - **Solution**: Use Option B instead

### Option B: Register New User (Recommended)

1. **Open Frontend**: `http://localhost:3000`

2. **Register**:
   - Click "Register here"
   - Username: `testuser`
   - Email: `test@example.com`
   - Password: `password123`
   - Role: `USER`
   - Click "Register"

3. **Login**:
   - Username: `testuser`
   - Password: `password123`
   - Click "Login"

4. **Explore Features**:
   - Dashboard - View overview
   - Expenses - Add/manage expenses
   - Budgets - Create budgets
   - Groups - Create expense groups
   - Notifications - View alerts
   - Settings - Customize preferences

---

## Feature Testing Guide

### 1. Expenses Management

**Create Expense:**
1. Go to "Expenses" page
2. Click "Add Expense"
3. Fill form:
   - Title: "Lunch at Restaurant"
   - Amount: 25.50
   - Category: Food
   - Type: Personal
   - Payment: Credit Card
4. Click "Create"

**Filter Expenses:**
- Use search box to find expenses
- Filter by type (Personal/Professional)
- Filter by category

**Edit/Delete:**
- Click edit icon to modify
- Click trash icon to delete

### 2. Budget Management

**Create Budget:**
1. Go to "Budgets" page
2. Click "Add Budget"
3. Fill form:
   - Category: Food
   - Limit: 500.00
   - Start Date: First day of month
   - End Date: Last day of month
4. Click "Create"

**Track Progress:**
- Visual progress bar shows spending
- Warning at 80% utilization
- Alert when over budget

### 3. Group Expenses

**Create Group:**
1. Go to "Groups" page
2. Click "Create Group"
3. Name: "Family Expenses"
4. Description: "Monthly family tracking"
5. Click "Create Group"

**Add Members:**
- Requires user IDs of other registered users
- Use Postman to add members via API

**Add Group Expense:**
- Use Postman: `POST /api/groups/{groupId}/expenses`
- Expense splits automatically among members

### 4. Notifications

**View Notifications:**
1. Go to "Notifications" page
2. See all system alerts
3. Budget warnings appear here

**Manage:**
- Click checkmark to mark as read
- Click "Mark All as Read" for bulk action

### 5. Settings & Reports

**Update Preferences:**
1. Go to "Settings" page
2. Toggle Dark Mode
3. Toggle Accessibility Mode
4. Change currency preference

**Download Reports:**
1. Scroll to Reports section
2. Click format:
   - CSV - Spreadsheet format
   - Excel - .xlsx file
   - PDF - Printable report
3. File downloads automatically

---

## API Testing with Postman

### Setup Postman

1. **Create Environment**:
   - Variable: `base` = `http://localhost:8083`

2. **Import Collection** (or create manually):
   - Use endpoints from `POSTMAN_STEP_BY_STEP_TESTING.md`

### Key Endpoints

**Authentication:**
```
POST {{base}}/api/auth/register
POST {{base}}/api/auth/login
GET {{base}}/api/auth/me
```

**Expenses:**
```
GET {{base}}/api/expenses
POST {{base}}/api/expenses
PUT {{base}}/api/expenses/{id}
DELETE {{base}}/api/expenses/{id}
```

**Budgets:**
```
GET {{base}}/api/budgets
POST {{base}}/api/budgets
GET {{base}}/api/budgets/user/{userId}
```

**Groups:**
```
GET {{base}}/api/groups
POST {{base}}/api/groups
POST {{base}}/api/groups/{groupId}/members
GET {{base}}/api/groups/{groupId}/summary
```

**Reports:**
```
GET {{base}}/api/reports/user/{userId}?format=csv
GET {{base}}/api/reports/user/{userId}?format=excel
GET {{base}}/api/reports/user/{userId}?format=pdf
```

---

## Architecture Overview

### Backend Structure
```
src/main/java/com/expenses_tracker/
├── config/          # Security, CORS configuration
├── controller/      # REST API endpoints
├── entity/          # JPA entities (User, Expense, Budget, etc.)
├── repository/      # Data access layer
├── service/         # Business logic
└── dto/             # Data transfer objects
```

### Frontend Structure
```
src/
├── components/      # React components
│   ├── Auth/       # Login, Register
│   ├── Dashboard/  # Main dashboard
│   ├── Expenses/   # Expense management
│   ├── Budgets/    # Budget tracking
│   ├── Groups/     # Group expenses
│   ├── Notifications/
│   ├── Settings/
│   └── Layout/     # Navbar
├── context/        # Auth context
├── services/       # API integration
└── App.js          # Main routing
```

---

## Common Issues & Solutions

### Backend Issues

**Issue: Port 8083 already in use**
- Solution: Change port in `application.properties`
- Or kill process using port 8083

**Issue: MySQL connection failed**
- Check MySQL is running
- Verify credentials in `application.properties`
- Ensure database `expenses_tracker` exists

**Issue: Login returns 401**
- Sample data users have plaintext passwords
- Register new user via `/api/auth/register`
- Or update `DataController` to encode passwords

### Frontend Issues

**Issue: Cannot connect to backend**
- Verify backend is running on port 8083
- Check `src/services/api.js` has correct base URL
- Check CORS configuration in backend

**Issue: Login not working**
- Clear browser cookies
- Check network tab for errors
- Verify credentials are correct

**Issue: Charts not displaying**
- Ensure recharts is installed: `npm install recharts`
- Check if data is being fetched
- Open browser console for errors

---

## Production Deployment

### Backend

1. **Build JAR**:
```bash
mvnw.cmd clean package -DskipTests
```

2. **Run JAR**:
```bash
java -jar target/expenses_tracker-0.0.1-SNAPSHOT.jar
```

3. **Environment Variables**:
- Set database credentials
- Configure production URL
- Enable HTTPS

### Frontend

1. **Build for Production**:
```bash
npm run build
```

2. **Deploy**:
- Serve `build/` folder with nginx/Apache
- Or deploy to Vercel/Netlify
- Update API base URL for production

---

## Security Considerations

- **Passwords**: BCrypt encrypted in database
- **Sessions**: HTTP-only cookies
- **CORS**: Configured for localhost (update for production)
- **SQL Injection**: Prevented by JPA/Hibernate
- **XSS**: React auto-escapes output

---

## Performance Tips

- **Database**: Add indexes on frequently queried columns
- **Backend**: Enable caching for read-heavy operations
- **Frontend**: Implement lazy loading for routes
- **API**: Use pagination for large datasets

---

## Next Steps

1. **Customize**: Modify colors, branding, features
2. **Extend**: Add new features (recurring bills UI, analytics)
3. **Test**: Write unit and integration tests
4. **Deploy**: Move to production environment
5. **Monitor**: Add logging and error tracking

---

## Support & Documentation

- **Backend API Guide**: `POSTMAN_STEP_BY_STEP_TESTING.md`
- **Frontend Guide**: `FRONTEND_GUIDE.md`
- **Database Setup**: `DATABASE_SETUP_GUIDE.md`
- **Currency Config**: `CURRENCY_CONFIGURATION.md`

---

## Technology Stack

**Backend:**
- Spring Boot 3.5.6
- Spring Security (Session-based)
- Spring Data JPA
- MySQL 8.0
- Maven

**Frontend:**
- React 19.2.0
- React Router 6.20.0
- Axios 1.12.2
- Recharts 2.10.3
- Lucide React (Icons)

**Tools:**
- Postman (API testing)
- Maven Wrapper (Backend build)
- npm (Frontend package manager)

---

## Success Checklist

- [ ] MySQL database created
- [ ] Backend starts without errors
- [ ] Frontend starts and opens in browser
- [ ] User registration works
- [ ] User login works and redirects to dashboard
- [ ] Can create expenses
- [ ] Can create budgets
- [ ] Can view notifications
- [ ] Can download reports
- [ ] All features accessible via UI

---

## Congratulations! 🎉

You now have a fully functional expense tracking application with:
- ✅ Complete authentication system
- ✅ Expense management with filters
- ✅ Budget tracking with alerts
- ✅ Group expense splitting
- ✅ Notification system
- ✅ Report generation (CSV, Excel, PDF)
- ✅ User preferences (dark mode, currency)
- ✅ Modern, responsive UI

Happy tracking! 💰
