# Expenses Tracker API — Step-by-Step Postman Testing Guide

This guide shows exactly how to test each feature of the backend using Postman. It is aligned with the actual controllers under `src/main/java/com/expenses_tracker/controller/`.

## Prerequisites

- Java 17 installed
- MySQL running with a database named `expenses_tracker`
- Application configured at `server.port=8083` in `src/main/resources/application.properties`
- Start the backend:

```bash
mvnw.cmd spring-boot:run
```

Base URL: `http://localhost:8083`

Session-based authentication is used. After login, Postman will automatically store the session cookie if "Automatically follow redirects" and "Retain headers" are enabled (default). Send all subsequent authenticated requests in the same Postman session.

---

## 1) Initialize Sample Data (Optional)
Controller: `DataController`

- Create sample users and expenses
  - POST `${base}/api/data/init`
  - Expected: `"Sample data initialized successfully!"`

- Check data status
  - GET `${base}/api/data/status`
  - Expected: `"Users: <n>, Expenses: <m>"`

- Clear all data
  - DELETE `${base}/api/data/clear`
  - Expected: `"All data cleared successfully!"`

---

## 2) Authentication
Controller: `AuthController`

- Register user
  - POST `${base}/api/auth/register`
  - Body (JSON):
    ```json
    {
      "username": "john_doe",
      "email": "john@example.com",
      "password": "password123",
      "role": "USER"
    }
    ```
  - 201 Created: `{ "message": "User registered successfully" }`

- Login (stores session cookie)
  - POST `${base}/api/auth/login`
  - Body (JSON):
    ```json
    {
      "username": "john_doe",
      "password": "password123"
    }
    ```
  - 200 OK: `{ "roles": ["ROLE_USER"] }`

- Get current user
  - GET `${base}/api/auth/me`
  - 200 OK: `{ "id": <id>, "username": "john_doe", "email": "john@example.com" }`

- Delete current user
  - DELETE `${base}/api/auth/me`
  - 200 OK: `{ "message": "User account deleted successfully." }`

Notes:
- For ADMIN registration, set `"role": "ADMIN"` and then login to verify `"ROLE_ADMIN"`.

---

## 3) Users (basic CRUD + lookup)
Controller: `UserController`

- Create user (legacy/simple)
  - POST `${base}/api/users`
  - Body: `{ "username": "legacy_user", "email": "legacy@example.com", "password": "pass" }`

- Get all users
  - GET `${base}/api/users`

- Get user by ID
  - GET `${base}/api/users/{id}`

- Get user by username
  - GET `${base}/api/users/username/{username}`

- Update user
  - PUT `${base}/api/users/{id}`
  - Body: `{ "username": "updated_name" }`

- Delete user
  - DELETE `${base}/api/users/{id}`

---

## 4) User Preferences
Controller: `UserPreferencesController`

Authenticated users can view/update their own preferences. Admins can access any user.

- Get preferences
  - GET `${base}/api/users/{id}/preferences`

- Update preferences
  - PUT `${base}/api/users/{id}/preferences`
  - Body examples:
    - Toggle dark mode:
      ```json
      { "darkMode": true }
      ```
    - Toggle accessibility mode:
      ```json
      { "accessibilityMode": true }
      ```
    - Set preferred currency:
      ```json
      { "preferredCurrency": "INR" }
      ```

---

## 5) Expenses
Controller: `ExpenseController`

- Create expense
  - POST `${base}/api/expenses`
  - Body example:
    ```json
    {
      "title": "Lunch at Restaurant",
      "description": "Business lunch",
      "amount": 25.5,
      "date": "2024-01-15",
      "category": "Food",
      "paymentMethod": "Credit Card",
      "expenseType": "PERSONAL",
      "isPinned": false,
      "user": { "id": 1 }
    }
    ```

- Get all expenses
  - GET `${base}/api/expenses`

- Get by type
  - GET `${base}/api/expenses/type/PERSONAL`
  - GET `${base}/api/expenses/type/PROFESSIONAL`

- Get by ID
  - GET `${base}/api/expenses/{id}`

- Update
  - PUT `${base}/api/expenses/{id}`

- Delete
  - DELETE `${base}/api/expenses/{id}`

- Toggle pin
  - POST `${base}/api/expenses/{id}/togglePin`

- Search and filters
  - GET `${base}/api/expenses/search?keyword=lunch`
  - GET `${base}/api/expenses/filter/category?category=Food`
  - GET `${base}/api/expenses/filter/payment-method?paymentMethod=Credit%20Card`
  - GET `${base}/api/expenses/filter/date-range?startDate=2024-01-01&endDate=2024-01-31`
  - GET `${base}/api/expenses/filter/type-category?expenseType=PERSONAL&category=Food`

---

## 6) Budgets
Controller: `BudgetController`

- Create budget
  - POST `${base}/api/budgets`
  - Body example:
    ```json
    {
      "category": "Food",
      "limitAmount": 500.0,
      "startDate": "2024-01-01",
      "endDate": "2024-01-31",
      "user": { "id": 1 }
    }
    ```

- Get all budgets
  - GET `${base}/api/budgets`

- Get budgets by user
  - GET `${base}/api/budgets/user/{userId}`

- Get budget by ID
  - GET `${base}/api/budgets/{id}`

- Update budget
  - PUT `${base}/api/budgets/{id}`

- Delete budget
  - DELETE `${base}/api/budgets/{id}`

- Get active budget by user & category
  - GET `${base}/api/budgets/active/user/{userId}/category/{category}`

- Get total spending in date range
  - GET `${base}/api/budgets/spending/user/{userId}/category/{category}?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

---

## 7) Recurring Bills
Controller: `RecurringBillController`

- Create recurring bill
  - POST `${base}/api/recurring-bills`
  - Body example:
    ```json
    {
      "name": "Monthly Rent",
      "amount": 1200.0,
      "category": "Housing",
      "dayOfMonthDue": 1,
      "user": { "id": 1 }
    }
    ```

- Get all recurring bills
  - GET `${base}/api/recurring-bills`

- Get bills by user
  - GET `${base}/api/recurring-bills/user/{userId}`

- Get/Update/Delete by ID
  - GET `${base}/api/recurring-bills/{id}`
  - PUT `${base}/api/recurring-bills/{id}`
  - DELETE `${base}/api/recurring-bills/{id}`

---

## 8) Notifications
Controller: `NotificationController`

- Get notifications by user
  - GET `${base}/api/notifications/{userId}`

- Get unread notifications by user
  - GET `${base}/api/notifications/{userId}/unread`

- Mark one as read
  - POST `${base}/api/notifications/{notificationId}/mark-read`

- Mark all as read for user
  - POST `${base}/api/notifications/{userId}/mark-all-read`

---

## 9) Groups and Group Expenses
Controller: `GroupController`

All endpoints require an authenticated session. The current user becomes the creator/member.

- Create group
  - POST `${base}/api/groups`
  - Body example:
    ```json
    {
      "name": "Family Expenses",
      "description": "Monthly family expense tracking"
    }
    ```
  - 201 Created: `{ "message": "Group created successfully", "groupId": <id>, "groupName": "Family Expenses" }`

- Get groups for current user
  - GET `${base}/api/groups`

- Get group by ID (must be a member)
  - GET `${base}/api/groups/{groupId}`

- Add members
  - POST `${base}/api/groups/{groupId}/members`
  - Body:
    ```json
    { "userIds": [2, 3] }
    ```

- Add group expense (splits equally if not provided)
  - POST `${base}/api/groups/{groupId}/expenses`
  - Body example:
    ```json
    {
      "title": "Family Dinner",
      "description": "Restaurant dinner",
      "amount": 500.0,
      "category": "Food",
      "paymentMethod": "Credit Card",
      "expenseType": "PERSONAL",
      "splitDetails": { "1": 200.0, "2": 300.0 }
    }
    ```

- Group summary
  - GET `${base}/api/groups/{groupId}/summary`

- Update/Delete group
  - PUT `${base}/api/groups/{groupId}`
  - DELETE `${base}/api/groups/{groupId}`

---

## 10) Reports (Download Files)
Controller: `ReportController`

Authenticated. User must be owner/admin; group access requires membership/admin.

- Available formats
  - GET `${base}/api/reports/formats`

- User reports
  - GET `${base}/api/reports/user/{userId}?format=csv|excel|pdf`

- Group reports
  - GET `${base}/api/reports/group/{groupId}?format=csv|excel|pdf`

Postman tip: In the request "Save Response" dropdown, click "Save to a file" to download the generated file.

---

## Recommended Postman Setup

- Create environment variable `base` = `http://localhost:8083`
- Keep the same Postman session after login to preserve the session cookie.
- For 401 responses, login again and retry.

---

## Quick End-to-End Flow

1. POST `${base}/api/auth/register` (USER)
2. POST `${base}/api/auth/login`
3. GET `${base}/api/auth/me`
4. POST `${base}/api/budgets` (create budget)
5. POST `${base}/api/expenses` (add expense)
6. GET `${base}/api/notifications/{userId}` (check alerts)
7. GET `${base}/api/reports/user/{userId}?format=csv` (download report)

If you prefer seed data, start with `${base}/api/data/init` and then exercise the features.
