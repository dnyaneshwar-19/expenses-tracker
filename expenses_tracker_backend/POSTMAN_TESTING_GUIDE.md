# Expenses Tracker API - Complete Postman Testing Guide

## Overview

This comprehensive guide provides step-by-step instructions for testing the Expenses Tracker API using Postman. The API now includes **complete authentication system** with user registration, login, role-based access control, and all existing expense tracking features.

## Base URL

```
http://localhost:8083
```

## 🔐 Authentication System Overview

The API now includes:

- **User Registration** with email and password
- **User Login** with session-based authentication
- **Role-based Access Control** (USER and ADMIN roles)
- **Protected Endpoints** requiring authentication
- **User Profile Management**
- **Family/Group Mode** for shared expense splitting
- **Dark Mode & Accessibility Mode** for user preferences
- **Downloadable Reports** in CSV, Excel, and PDF formats

---

## 1. 🚀 Initial Setup & Data Initialization

### Start the Application

```bash
mvnw.cmd spring-boot:run
```

### Initialize Sample Data (Optional)

**POST** `/api/data/init`

- **Description**: Creates sample users, categories, and expenses for testing
- **Body**: None required
- **Expected Response**: "Sample data initialized successfully!"

### Check Data Status

**GET** `/api/data/status`

- **Description**: Shows count of users, categories, and expenses
- **Expected Response**: "Users: 2, Categories: 4, Expenses: 3"

### Clear All Data (Optional)

**DELETE** `/api/data/clear`

- **Description**: Removes all data from the database
- **Expected Response**: "All data cleared successfully!"

---

## 2. 🔐 Authentication Testing

### 2.1 User Registration

#### Register a Regular User

**POST** `/api/auth/register`

- **Body** (JSON):

```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "USER"
}
```

- **Expected Response** (201):

```json
{
  "message": "User registered successfully"
}
```

#### Register an Admin User

**POST** `/api/auth/register`

- **Body** (JSON):

```json
{
  "username": "admin_user",
  "email": "admin@example.com",
  "password": "admin123",
  "role": "ADMIN"
}
```

- **Expected Response** (201):

```json
{
  "message": "User registered successfully"
}
```

#### Test Username Already Exists

**POST** `/api/auth/register`

- **Body** (JSON):

```json
{
  "username": "john_doe",
  "email": "different@example.com",
  "password": "password456",
  "role": "USER"
}
```

- **Expected Response** (400):

```json
{
  "error": "Username already taken"
}
```

### 2.2 User Login

#### Login as Regular User

**POST** `/api/auth/login`

- **Body** (JSON):

```json
{
  "username": "john_doe",
  "password": "password123"
}
```

- **Expected Response** (200):

```json
{
  "roles": ["ROLE_USER"]
}
```

#### Login as Admin User

**POST** `/api/auth/login`

- **Body** (JSON):

```json
{
  "username": "admin_user",
  "password": "admin123"
}
```

- **Expected Response** (200):

```json
{
  "roles": ["ROLE_ADMIN"]
}
```

#### Test Invalid Credentials

**POST** `/api/auth/login`

- **Body** (JSON):

```json
{
  "username": "john_doe",
  "password": "wrongpassword"
}
```

- **Expected Response** (401):

```json
{
  "error": "Invalid credentials"
}
```

### 2.3 User Profile Management

#### Get Current User Details

**GET** `/api/auth/me`

- **Headers**: Include session cookie from login
- **Expected Response** (200):

```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com"
}
```

#### Test Unauthorized Access

**GET** `/api/auth/me`

- **Headers**: No session cookie
- **Expected Response** (401):

```json
{
  "error": "Unauthorized"
}
```

#### Delete Current User Account

**DELETE** `/api/auth/me`

- **Headers**: Include session cookie from login
- **Expected Response** (200):

```json
{
  "message": "User account deleted successfully."
}
```

---

## 3. 👥 User Management (Legacy Endpoints)

### Create a User (Legacy)

**POST** `/api/users`

- **Body** (JSON):

```json
{
  "username": "legacy_user"
}
```

### Get All Users

**GET** `/api/users`

- **Expected Response**: Array of all users

### Get User by ID

**GET** `/api/users/{id}`

- **Example**: `/api/users/1`
- **Expected Response**: User object

### Get User by Username

**GET** `/api/users/username/{username}`

- **Example**: `/api/users/username/john_doe`
- **Expected Response**: User object

---

## 4. 💰 Expense Management

### 4.1 Create Expenses

#### Create Personal Expense

**POST** `/api/expenses`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "title": "Lunch at Restaurant",
  "description": "Business lunch with client",
  "amount": 25.5,
  "date": "2024-01-15",
  "category": "Food",
  "paymentMethod": "Credit Card",
  "expenseType": "PERSONAL",
  "isPinned": false,
  "user": {
    "id": 1
  }
}
```

#### Create Professional Expense

**POST** `/api/expenses`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "title": "Client Meeting Dinner",
  "description": "Client meeting dinner",
  "amount": 120.0,
  "date": "2024-01-15",
  "category": "Business",
  "paymentMethod": "Company Card",
  "expenseType": "PROFESSIONAL",
  "isPinned": true,
  "user": {
    "id": 1
  }
}
```

### 4.2 Retrieve Expenses

#### Get All Expenses

**GET** `/api/expenses`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of all expenses

#### Get Expenses by Type

**GET** `/api/expenses/type/PERSONAL`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of personal expenses

**GET** `/api/expenses/type/PROFESSIONAL`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of professional expenses

#### Get Expense by ID

**GET** `/api/expenses/{id}`

- **Example**: `/api/expenses/1`
- **Headers**: Include session cookie from login
- **Expected Response**: Single expense object

### 4.3 Update and Delete Expenses

#### Update an Expense

**PUT** `/api/expenses/{id}`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "title": "Updated Lunch",
  "description": "Updated description",
  "amount": 30.0,
  "date": "2024-01-16",
  "category": "Food",
  "paymentMethod": "Cash",
  "expenseType": "PERSONAL",
  "isPinned": true
}
```

#### Delete an Expense

**DELETE** `/api/expenses/{id}`

- **Headers**: Include session cookie from login
- **Expected Response**: 200 OK

### 4.4 Expense Actions

#### Toggle Pin Status

**POST** `/api/expenses/{id}/togglePin`

- **Headers**: Include session cookie from login
- **Expected Response**: 200 OK

### 4.5 Expense Search and Filtering

#### Search Expenses by Keyword

**GET** `/api/expenses/search?keyword=lunch`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of matching expenses

#### Filter by Category

**GET** `/api/expenses/filter/category?category=Food`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of food expenses

#### Filter by Payment Method

**GET** `/api/expenses/filter/payment-method?paymentMethod=Credit Card`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of credit card expenses

#### Filter by Date Range

**GET** `/api/expenses/filter/date-range?startDate=2024-01-01&endDate=2024-01-31`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of expenses in date range

#### Filter by Type and Category

**GET** `/api/expenses/filter/type-category?expenseType=PERSONAL&category=Food`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of personal food expenses

---

## 5. 📊 Budget Management

### 5.1 Create Budgets

#### Create Monthly Food Budget

**POST** `/api/budgets`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "category": "Food",
  "limitAmount": 500.0,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31",
  "user": {
    "id": 1
  }
}
```

#### Create Transport Budget

**POST** `/api/budgets`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "category": "Transport",
  "limitAmount": 200.0,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31",
  "user": {
    "id": 1
  }
}
```

### 5.2 Retrieve Budgets

#### Get All Budgets

**GET** `/api/budgets`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of all budgets

#### Get Budgets by User

**GET** `/api/budgets/user/{userId}`

- **Example**: `/api/budgets/user/1`
- **Headers**: Include session cookie from login
- **Expected Response**: Array of user's budgets

#### Get Budget by ID

**GET** `/api/budgets/{id}`

- **Example**: `/api/budgets/1`
- **Headers**: Include session cookie from login
- **Expected Response**: Single budget object

### 5.3 Budget Analytics

#### Get Active Budget

**GET** `/api/budgets/active/user/{userId}/category/{category}`

- **Example**: `/api/budgets/active/user/1/category/Food`
- **Headers**: Include session cookie from login
- **Expected Response**: Active budget for category

#### Get Total Spending

**GET** `/api/budgets/spending/user/{userId}/category/{category}?startDate=2024-01-01&endDate=2024-01-31`

- **Example**: `/api/budgets/spending/user/1/category/Food?startDate=2024-01-01&endDate=2024-01-31`
- **Headers**: Include session cookie from login
- **Expected Response**: Total spending amount

### 5.4 Update and Delete Budgets

#### Update Budget

**PUT** `/api/budgets/{id}`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "category": "Food",
  "limitAmount": 600.0,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31"
}
```

#### Delete Budget

**DELETE** `/api/budgets/{id}`

- **Headers**: Include session cookie from login
- **Expected Response**: 200 OK

---

## 6. 🔄 Recurring Bill Management

### 6.1 Create Recurring Bills

#### Create Monthly Rent Bill

**POST** `/api/recurring-bills`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "name": "Monthly Rent",
  "amount": 1200.0,
  "category": "Housing",
  "dayOfMonthDue": 1,
  "user": {
    "id": 1
  }
}
```

#### Create Netflix Subscription

**POST** `/api/recurring-bills`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "name": "Netflix Subscription",
  "amount": 15.99,
  "category": "Entertainment",
  "dayOfMonthDue": 15,
  "user": {
    "id": 1
  }
}
```

### 6.2 Retrieve Recurring Bills

#### Get All Recurring Bills

**GET** `/api/recurring-bills`

- **Headers**: Include session cookie from login
- **Expected Response**: Array of all recurring bills

#### Get Bills by User

**GET** `/api/recurring-bills/user/{userId}`

- **Example**: `/api/recurring-bills/user/1`
- **Headers**: Include session cookie from login
- **Expected Response**: Array of user's recurring bills

#### Get Bill by ID

**GET** `/api/recurring-bills/{id}`

- **Example**: `/api/recurring-bills/1`
- **Headers**: Include session cookie from login
- **Expected Response**: Single recurring bill object

### 6.3 Update and Delete Recurring Bills

#### Update Recurring Bill

**PUT** `/api/recurring-bills/{id}`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "name": "Updated Rent",
  "amount": 1300.0,
  "category": "Housing",
  "dayOfMonthDue": 1
}
```

#### Delete Recurring Bill

**DELETE** `/api/recurring-bills/{id}`

- **Headers**: Include session cookie from login
- **Expected Response**: 200 OK

---

## 7. 🔔 Notification Management

### 7.1 Retrieve Notifications

#### Get All Notifications for User

**GET** `/api/notifications/{userId}`

- **Example**: `/api/notifications/1`
- **Headers**: Include session cookie from login
- **Expected Response**: Array of user's notifications

#### Get Unread Notifications

**GET** `/api/notifications/{userId}/unread`

- **Example**: `/api/notifications/1/unread`
- **Headers**: Include session cookie from login
- **Expected Response**: Array of unread notifications

### 7.2 Manage Notifications

#### Mark Notification as Read

**POST** `/api/notifications/{notificationId}/mark-read`

- **Example**: `/api/notifications/1/mark-read`
- **Headers**: Include session cookie from login
- **Expected Response**: 200 OK

#### Mark All Notifications as Read

**POST** `/api/notifications/{userId}/mark-all-read`

- **Example**: `/api/notifications/1/mark-all-read`
- **Headers**: Include session cookie from login
- **Expected Response**: 200 OK

---

## 8. 👥 Family/Group Mode - Shared Expense Splitting

### 8.1 Group Management

#### Create a Group

**POST** `/api/groups`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "name": "Family Expenses",
  "description": "Monthly family expense tracking"
}
```

- **Expected Response** (201):

```json
{
  "message": "Group created successfully",
  "groupId": 1,
  "groupName": "Family Expenses"
}
```

#### Get User's Groups

**GET** `/api/groups`

- **Headers**: Include session cookie from login
- **Expected Response** (200): Array of groups

#### Get Group Details

**GET** `/api/groups/{groupId}`

- **Example**: `/api/groups/1`
- **Headers**: Include session cookie from login
- **Expected Response** (200): Group object with members

#### Add Members to Group

**POST** `/api/groups/{groupId}/members`

- **Example**: `/api/groups/1/members`
- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "userIds": [2, 3, 4]
}
```

- **Expected Response** (200):

```json
{
  "message": "Members added successfully"
}
```

### 8.2 Group Expense Management

#### Add Group Expense

**POST** `/api/groups/{groupId}/expenses`

- **Example**: `/api/groups/1/expenses`
- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "title": "Family Dinner",
  "description": "Restaurant dinner for family",
  "amount": 500.0,
  "date": "2024-01-15",
  "category": "Food",
  "paymentMethod": "Credit Card",
  "expenseType": "PERSONAL",
  "isPinned": false,
  "splitDetails": {
    "1": 200.0,
    "2": 150.0,
    "3": 150.0
  }
}
```

- **Expected Response** (201):

```json
{
  "message": "Group expense added successfully",
  "expenseId": 1
}
```

#### Get Group Summary

**GET** `/api/groups/{groupId}/summary`

- **Example**: `/api/groups/1/summary`
- **Headers**: Include session cookie from login
- **Expected Response** (200):

```json
{
  "groupId": 1,
  "groupName": "Family Expenses",
  "totalExpenses": "₹500.00",
  "memberCount": 3,
  "memberShares": {
    "john_doe": "₹200.00",
    "jane_smith": "₹150.00",
    "bob_wilson": "₹150.00"
  },
  "expenseCount": 1
}
```

#### Update Group Details

**PUT** `/api/groups/{groupId}`

- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "name": "Updated Family Expenses",
  "description": "Updated description"
}
```

#### Delete Group

**DELETE** `/api/groups/{groupId}`

- **Headers**: Include session cookie from login
- **Expected Response** (200):

```json
{
  "message": "Group deleted successfully"
}
```

---

## 9. 🎨 Dark Mode & Accessibility Mode

### 9.1 User Preferences Management

#### Get User Preferences

**GET** `/api/users/{id}/preferences`

- **Example**: `/api/users/1/preferences`
- **Headers**: Include session cookie from login
- **Expected Response** (200):

```json
{
  "darkMode": false,
  "accessibilityMode": false,
  "preferredCurrency": "INR"
}
```

#### Update User Preferences

**PUT** `/api/users/{id}/preferences`

- **Example**: `/api/users/1/preferences`
- **Headers**: Include session cookie from login
- **Body** (JSON):

```json
{
  "darkMode": true,
  "accessibilityMode": true,
  "preferredCurrency": "INR"
}
```

- **Expected Response** (200):

```json
{
  "message": "Preferences updated successfully",
  "darkMode": true,
  "accessibilityMode": true,
  "preferredCurrency": "INR"
}
```

#### Test Dark Mode Toggle

**PUT** `/api/users/{id}/preferences`

- **Body** (JSON):

```json
{
  "darkMode": true
}
```

#### Test Accessibility Mode Toggle

**PUT** `/api/users/{id}/preferences`

- **Body** (JSON):

```json
{
  "accessibilityMode": true
}
```

---

## 10. 📊 Downloadable Reports

### 10.1 User Expense Reports

#### Generate CSV Report

**GET** `/api/reports/user/{userId}?format=csv`

- **Example**: `/api/reports/user/1?format=csv`
- **Headers**: Include session cookie from login
- **Expected Response**: CSV file download

#### Generate Excel Report

**GET** `/api/reports/user/{userId}?format=excel`

- **Example**: `/api/reports/user/1?format=excel`
- **Headers**: Include session cookie from login
- **Expected Response**: Excel file download (.xlsx)

#### Generate PDF Report

**GET** `/api/reports/user/{userId}?format=pdf`

- **Example**: `/api/reports/user/1?format=pdf`
- **Headers**: Include session cookie from login
- **Expected Response**: PDF file download

### 10.2 Group Expense Reports

#### Generate Group CSV Report

**GET** `/api/reports/group/{groupId}?format=csv`

- **Example**: `/api/reports/group/1?format=csv`
- **Headers**: Include session cookie from login
- **Expected Response**: CSV file download

#### Generate Group Excel Report

**GET** `/api/reports/group/{groupId}?format=excel`

- **Example**: `/api/reports/group/1?format=excel`
- **Headers**: Include session cookie from login
- **Expected Response**: Excel file download (.xlsx)

#### Generate Group PDF Report

**GET** `/api/reports/group/{groupId}?format=pdf`

- **Example**: `/api/reports/group/1?format=pdf`
- **Headers**: Include session cookie from login
- **Expected Response**: PDF file download

### 10.3 Report Information

#### Get Available Report Formats

**GET** `/api/reports/formats`

- **Expected Response** (200):

```json
{
  "formats": ["csv", "excel", "pdf"],
  "descriptions": {
    "csv": "Comma-separated values file",
    "excel": "Microsoft Excel spreadsheet",
    "pdf": "Portable Document Format"
  }
}
```

---

## 11. 🧪 Complete Testing Scenarios

### Scenario 1: Complete User Journey

1. **Register User**

   ```bash
   POST /api/auth/register
   {
     "username": "testuser",
     "email": "test@example.com",
     "password": "password123",
     "role": "USER"
   }
   ```

2. **Login User**

   ```bash
   POST /api/auth/login
   {
     "username": "testuser",
     "password": "password123"
   }
   ```

3. **Get User Profile**

   ```bash
   GET /api/auth/me
   ```

4. **Create Budget**

   ```bash
   POST /api/budgets
   {
     "category": "Food",
     "limitAmount": 500.00,
     "startDate": "2024-01-01",
     "endDate": "2024-01-31",
     "user": {"id": 1}
   }
   ```

5. **Add Expenses**

   ```bash
   POST /api/expenses
   {
     "title": "Lunch",
     "description": "Daily lunch",
     "amount": 25.50,
     "date": "2024-01-15",
     "category": "Food",
     "paymentMethod": "Credit Card",
     "expenseType": "PERSONAL",
     "user": {"id": 1}
   }
   ```

6. **Check Notifications** (Budget alerts will be generated)
   ```bash
   GET /api/notifications/1/unread
   ```

### Scenario 2: Admin User Testing

1. **Register Admin**

   ```bash
   POST /api/auth/register
   {
     "username": "admin",
     "email": "admin@example.com",
     "password": "admin123",
     "role": "ADMIN"
   }
   ```

2. **Login as Admin**

   ```bash
   POST /api/auth/login
   {
     "username": "admin",
     "password": "admin123"
   }
   ```

3. **Verify Admin Role**
   - Response should include `"roles": ["ROLE_ADMIN"]`

### Scenario 3: Budget Alert Testing

1. **Create Budget with Low Limit**

   ```bash
   POST /api/budgets
   {
     "category": "Food",
     "limitAmount": 50.00,
     "startDate": "2024-01-01",
     "endDate": "2024-01-31",
     "user": {"id": 1}
   }
   ```

2. **Add Expenses Near Budget Limit**

   ```bash
   POST /api/expenses
   {
     "title": "Expensive Meal",
     "amount": 45.00,
     "category": "Food",
     "user": {"id": 1}
   }
   ```

3. **Check for Budget Alert Notifications**
   ```bash
   GET /api/notifications/1/unread
   ```

### Scenario 4: Complete Group Expense Journey

1. **Register Multiple Users**

   ```bash
   POST /api/auth/register
   {
     "username": "user1",
     "email": "user1@example.com",
     "password": "password123",
     "role": "USER"
   }

   POST /api/auth/register
   {
     "username": "user2",
     "email": "user2@example.com",
     "password": "password123",
     "role": "USER"
   }
   ```

2. **Login as User1**

   ```bash
   POST /api/auth/login
   {
     "username": "user1",
     "password": "password123"
   }
   ```

3. **Create Group**

   ```bash
   POST /api/groups
   {
     "name": "Family Expenses",
     "description": "Monthly family expense tracking"
   }
   ```

4. **Add Members to Group**

   ```bash
   POST /api/groups/1/members
   {
     "userIds": [2]
   }
   ```

5. **Add Group Expense**

   ```bash
   POST /api/groups/1/expenses
   {
     "title": "Family Dinner",
     "description": "Restaurant dinner",
     "amount": 600.0,
     "category": "Food",
     "splitDetails": {
       "1": 300.0,
       "2": 300.0
     }
   }
   ```

6. **Get Group Summary**
   ```bash
   GET /api/groups/1/summary
   ```

### Scenario 5: User Preferences Testing

1. **Login User**

   ```bash
   POST /api/auth/login
   ```

2. **Get Current Preferences**

   ```bash
   GET /api/users/1/preferences
   ```

3. **Enable Dark Mode**

   ```bash
   PUT /api/users/1/preferences
   {
     "darkMode": true
   }
   ```

4. **Enable Accessibility Mode**
   ```bash
   PUT /api/users/1/preferences
   {
     "accessibilityMode": true
   }
   ```

### Scenario 6: Report Generation Testing

1. **Add Some Expenses**

   ```bash
   POST /api/expenses
   ```

2. **Generate CSV Report**

   ```bash
   GET /api/reports/user/1?format=csv
   ```

3. **Generate Excel Report**

   ```bash
   GET /api/reports/user/1?format=excel
   ```

4. **Generate PDF Report**

   ```bash
   GET /api/reports/user/1?format=pdf
   ```

5. **Generate Group Report**
   ```bash
   GET /api/reports/group/1?format=csv
   ```

---

## 9. 🔧 Postman Collection Setup

### Environment Variables

Create a Postman environment with:

- `base_url`: `http://localhost:8083`
- `session_cookie`: (will be set automatically after login)

### Pre-request Scripts

Add this to your login request:

```javascript
pm.test("Login successful", function () {
  pm.response.to.have.status(200);
  var jsonData = pm.response.json();
  pm.environment.set("session_cookie", pm.cookies.get("JSESSIONID"));
});
```

### Collection Structure

```
Expenses Tracker API
├── Authentication
│   ├── Register User
│   ├── Register Admin
│   ├── Login User
│   ├── Login Admin
│   ├── Get Current User
│   └── Delete Account
├── User Management
│   ├── Get All Users
│   ├── Get User by ID
│   ├── Get User by Username
│   ├── Get User Preferences
│   └── Update User Preferences
├── Group Management
│   ├── Create Group
│   ├── Get User Groups
│   ├── Get Group Details
│   ├── Add Members to Group
│   ├── Update Group
│   └── Delete Group
├── Group Expenses
│   ├── Add Group Expense
│   └── Get Group Summary
├── Expense Management
│   ├── Create Expense
│   ├── Get All Expenses
│   ├── Get Expenses by Type
│   ├── Update Expense
│   ├── Delete Expense
│   ├── Toggle Pin
│   ├── Search Expenses
│   └── Filter Expenses
├── Budget Management
│   ├── Create Budget
│   ├── Get All Budgets
│   ├── Get Active Budget
│   ├── Get Spending
│   ├── Update Budget
│   └── Delete Budget
├── Recurring Bills
│   ├── Create Bill
│   ├── Get All Bills
│   ├── Update Bill
│   └── Delete Bill
├── Notifications
│   ├── Get Notifications
│   ├── Get Unread Notifications
│   ├── Mark as Read
│   └── Mark All as Read
└── Reports
    ├── Get Available Formats
    ├── Generate User CSV Report
    ├── Generate User Excel Report
    ├── Generate User PDF Report
    ├── Generate Group CSV Report
    ├── Generate Group Excel Report
    └── Generate Group PDF Report
```

---

## 10. 🚨 Error Handling

### Common Error Responses

#### 401 Unauthorized

```json
{
  "error": "Unauthorized"
}
```

#### 400 Bad Request

```json
{
  "error": "Username already taken"
}
```

#### 404 Not Found

```json
{
  "error": "User not found with id: 999"
}
```

#### 500 Internal Server Error

```json
{
  "error": "Internal server error"
}
```

---

## 11. 📝 Testing Checklist

### Authentication Testing

- [ ] Register new user
- [ ] Register new admin
- [ ] Login with valid credentials
- [ ] Login with invalid credentials
- [ ] Get current user details
- [ ] Delete user account
- [ ] Test username uniqueness

### User Preferences Testing

- [ ] Get user preferences
- [ ] Update dark mode preference
- [ ] Update accessibility mode preference
- [ ] Update preferred currency
- [ ] Test preference persistence

### Group Management Testing

- [ ] Create group
- [ ] Get user groups
- [ ] Get group details
- [ ] Add members to group
- [ ] Update group details
- [ ] Delete group
- [ ] Test group access control

### Group Expense Testing

- [ ] Add group expense
- [ ] Add group expense with custom split
- [ ] Add group expense with equal split
- [ ] Get group summary
- [ ] Test expense splitting calculations

### Expense Management Testing

- [ ] Create personal expense
- [ ] Create professional expense
- [ ] Get all expenses
- [ ] Filter by type
- [ ] Filter by category
- [ ] Filter by date range
- [ ] Search expenses
- [ ] Update expense
- [ ] Delete expense
- [ ] Toggle pin status

### Budget Management Testing

- [ ] Create budget
- [ ] Get active budget
- [ ] Calculate spending
- [ ] Update budget
- [ ] Delete budget
- [ ] Test budget alerts

### Recurring Bills Testing

- [ ] Create recurring bill
- [ ] Get bills by user
- [ ] Update bill
- [ ] Delete bill

### Notification Testing

- [ ] Get notifications
- [ ] Get unread notifications
- [ ] Mark as read
- [ ] Mark all as read
- [ ] Test budget alert notifications

### Report Generation Testing

- [ ] Get available report formats
- [ ] Generate user CSV report
- [ ] Generate user Excel report
- [ ] Generate user PDF report
- [ ] Generate group CSV report
- [ ] Generate group Excel report
- [ ] Generate group PDF report
- [ ] Test report file downloads

---

## 12. 🎯 Best Practices

### Session Management

- Always include session cookies in authenticated requests
- Test session expiration scenarios
- Verify proper logout functionality

### Data Validation

- Test with invalid data formats
- Test with missing required fields
- Test with boundary values

### Security Testing

- Test unauthorized access attempts
- Verify role-based access control
- Test password validation

### Performance Testing

- Test with large datasets
- Monitor response times
- Test concurrent requests

---

## 🎉 Conclusion

This comprehensive guide covers all aspects of the Expenses Tracker API including the new authentication system. The API now provides:

- ✅ **Complete Authentication System** with registration and login
- ✅ **Role-based Access Control** (USER and ADMIN)
- ✅ **Session Management** for secure operations
- ✅ **Comprehensive Expense Tracking** with filtering and search
- ✅ **Budget Management** with alerts and analytics
- ✅ **Recurring Bill Management** with notifications
- ✅ **Smart Notifications** for budget alerts and bill reminders
- ✅ **Complete CRUD Operations** for all entities

The API is now production-ready with proper security, authentication, and comprehensive functionality for personal and business expense tracking! 🚀

## 🎉 New Features Summary

### ✅ **Family/Group Mode**

- **Shared Expense Splitting** for multiple users
- **Group Management** with member addition/removal
- **Equal or Custom Split** calculations
- **Group Summary** with member shares in ₹
- **Access Control** for group members

### ✅ **Dark Mode & Accessibility Mode**

- **User Preferences** storage in database
- **Dark Mode Toggle** for better night usage
- **Accessibility Mode** for senior/colorblind users
- **Preferred Currency** settings
- **Mobile-friendly** preference management

### ✅ **Downloadable Reports**

- **CSV Reports** for data analysis
- **Excel Reports** (.xlsx) with formatting
- **PDF Reports** for printing/sharing
- **User Reports** for individual expenses
- **Group Reports** with split details
- **Timestamped Filenames** for organization

### 🔧 **Technical Implementation**

- **BigDecimal** for precise money calculations
- **₹ Symbol** formatting in all responses
- **Session-based Authentication** maintained
- **Role-based Access Control** preserved
- **CORS Configuration** for frontend integration
- **Mobile-optimized** JSON responses

### 📱 **Mobile & Desktop Ready**

- **Compact JSON** structure for mobile
- **Clear Field Names** for easy parsing
- **File Downloads** with proper headers
- **Responsive Design** considerations
- **Cross-platform** compatibility

The Expenses Tracker Backend now provides a complete solution for:

- **Personal Expense Tracking** with authentication
- **Family/Group Expense Management** with splitting
- **User Customization** with preferences
- **Data Export** in multiple formats
- **Professional Business** expense tracking
- **Budget Management** with alerts
- **Recurring Bill** tracking
- **Smart Notifications** system

Perfect for both individual users and families/groups managing shared expenses! 🎯
