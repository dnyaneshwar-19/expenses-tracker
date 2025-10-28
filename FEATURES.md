# 💰 Expenses Tracker - Complete Features Documentation

## Table of Contents
1. [Overview](#overview)
2. [User Management Features](#user-management-features)
3. [Expense Management Features](#expense-management-features)
4. [Budget Management Features](#budget-management-features)
5. [Recurring Bills Features](#recurring-bills-features)
6. [Notifications System](#notifications-system)
7. [Reports & Analytics](#reports--analytics)
8. [User Preferences & Settings](#user-preferences--settings)
9. [Category Management](#category-management)
10. [Security Features](#security-features)
11. [UI/UX Features](#uiux-features)
12. [Technical Architecture](#technical-architecture)

---

## Overview

**Expenses Tracker** is a comprehensive full-stack web application designed to help users manage their personal and professional finances effectively. Built with Spring Boot (backend) and React (frontend), it provides a modern, intuitive interface for tracking expenses, managing budgets, handling recurring bills, and generating detailed financial reports.

### Technology Stack
- **Backend**: Spring Boot 3.5.6, Spring Security, JPA/Hibernate, MySQL 8.0
- **Frontend**: React 19.2.0, React Router 6.20.0, Axios 1.12.2, Recharts 2.10.3
- **Security**: Session-based authentication with BCrypt password encryption
- **Build Tools**: Maven (backend), Create React App (frontend)

### Key Highlights
- ✅ Complete CRUD operations for expenses
- ✅ Custome Category Creation
- ✅ Many Diffrent Fillters to find your expenses
- ✅ Real-time budget tracking with alerts
- ✅ Recurring bill management with reminders
- ✅ Report generation (CSV)
- ✅ Interactive dashboard with charts
- ✅ Notification of Budget alters & Bill reminder Dark mode 
- ✅ Session-based secure authentication

---

## User Management Features

### 1. User Registration & Authentication

#### Registration Features
- **Secure Account Creation**: Users register with username, email, and password
- **Password Encryption**: BCrypt hashing with automatic salt generation
- **Role Assignment**: Automatic USER role assignment, optional ADMIN role
- **Duplicate Prevention**: Validates unique username and email
- **Email Validation**: Ensures valid email format

**API Endpoint**: `POST /api/auth/register`

**Request Example**:
```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123",
  "role": "USER"
}
```

**Use Cases**:
- New users create accounts to start tracking expenses
- Admin accounts for system management
- Email-based identification for future features

#### Login & Session Management
- **Session-Based Authentication**: Secure Spring Security sessions
- **HTTP-Only Cookies**: Enhanced security against XSS attacks
- **Credential Validation**: Username/password verification
- **Automatic Session Creation**: Session created upon successful login
- **Session Persistence**: Maintains login state across requests

**API Endpoint**: `POST /api/auth/login`

**Response Example**:
```json
{
  "roles": ["ROLE_USER"],
  "username": "john_doe"
}
```

**Use Cases**:
- Secure access to personal financial data
- Multi-device session management
- Role-based feature access

#### Current User Information
- **Get Authenticated User**: Retrieve current logged-in user details
- **Session Validation**: Verifies active session
- **User Profile Data**: Returns ID, username, email

**API Endpoint**: `GET /api/auth/me`

**Use Cases**:
- Display user information in UI
- Validate session status
- Personalize user experience

#### Account Deletion
- **Self-Service Deletion**: Users can delete their own accounts
- **Cascade Delete**: Removes all associated data (expenses, budgets, bills, notifications)
- **Session Cleanup**: Clears authentication session
- **Permanent Removal**: Complete data deletion

**API Endpoint**: `DELETE /api/auth/me`

**Use Cases**:
- Account closure
- Privacy compliance (GDPR)
- Data removal requests

---

## Expense Management Features

### 2. Complete Expense CRUD Operations

#### Create Expense
**Fields**:
- **Title**: Short descriptive name
- **Description**: Detailed information
- **Amount**: Precise decimal amount (BigDecimal)
- **Date**: Transaction date
- **Payment Method**: Cash, Credit Card, Debit Card, UPI, Net Banking, etc.
- **Category**: Food, Transport, Entertainment, Bills, Healthcare, etc.
- **Expense Type**: PERSONAL or PROFESSIONAL
- **User Association**: Automatically linked to authenticated user

**API Endpoint**: `POST /api/expenses`

**Request Example**:
```json
{
  "title": "Grocery Shopping",
  "description": "Weekly groceries from supermarket",
  "amount": 2500.00,
  "date": "2024-01-15",
  "paymentMethod": "Credit Card",
  "category": "Food",
  "expenseType": "PERSONAL"
}
```

**Use Cases**:
- Record daily purchases
- Track business expenses
- Maintain detailed expense history
- Categorize spending for analysis

#### Read/View Expenses
- **List All User Expenses**: View complete expense history
- **Individual Expense**: Get specific expense details
- **Filter by Type**: View PERSONAL or PROFESSIONAL expenses only
- **Automatic User Filtering**: Users see only their own expenses
- **Sorted Display**: Chronological ordering (newest first)

**API Endpoints**:
- `GET /api/expenses` - All user expenses
- `GET /api/expenses/{id}` - Specific expense
- `GET /api/expenses/type/{expenseType}` - Filter by type

**Use Cases**:
- Review spending history
- Audit expense records
- Tax preparation
- Monthly spending review

#### Update Expense
- **Edit Any Field**: Modify title, description, amount, date, category, payment method
- **Validation**: Ensures data integrity
- **Immediate Update**: Changes reflect instantly

**API Endpoint**: `PUT /api/expenses/{id}`

**Use Cases**:
- Correct entry errors
- Update expense details
- Reclassify expenses
- Adjust amounts after refunds

#### Delete Expense
- **Permanent Removal**: Complete deletion from database
- **Confirmation Required**: Frontend prompts for confirmation
- **No Recovery**: Deleted expenses cannot be recovered

**API Endpoint**: `DELETE /api/expenses/{id}`

**Use Cases**:
- Remove duplicate entries
- Delete test data
- Clean up incorrect entries

### 3. Advanced Expense Features

#### Pin/Unpin Expenses
- **Mark Important Expenses**: Pin frequently referenced expenses
- **Priority Display**: Pinned expenses appear at top
- **Toggle Functionality**: Easy pin/unpin with single action
- **Visual Indicator**: UI shows pinned status

**API Endpoint**: `POST /api/expenses/{id}/togglePin`

**Use Cases**:
- Mark recurring expenses
- Highlight important business expenses
- Track pending reimbursements
- Monitor large purchases

#### Search Functionality
- **Keyword Search**: Search by title or description
- **Real-time Results**: Instant search as you type
- **Case-Insensitive**: Flexible matching
- **Partial Matching**: Finds partial word matches

**API Endpoint**: `GET /api/expenses/search?keyword={keyword}`

**Use Cases**:
- Find specific transactions quickly
- Locate expenses by merchant name
- Search by description keywords
- Retrieve old records

#### Advanced Filtering

**Filter by Category**
- **API**: `GET /api/expenses/filter/category?category={category}`
- **Use Cases**: View all food expenses, analyze transportation costs

**Filter by Payment Method**
- **API**: `GET /api/expenses/filter/payment-method?paymentMethod={method}`
- **Use Cases**: Track credit card spending, monitor cash transactions

**Filter by Date Range**
- **API**: `GET /api/expenses/filter/date-range?startDate={start}&endDate={end}`
- **Use Cases**: Monthly reports, quarterly analysis, tax period tracking

**Filter by Type and Category**
- **API**: `GET /api/expenses/filter/type-category?expenseType={type}&category={category}`
- **Use Cases**: Professional food expenses, personal entertainment spending

---

## Budget Management Features

### 4. Budget Creation & Tracking

#### Create Budget
- **Category-Based**: Set limits per expense category
- **Time-Bound**: Define start and end dates
- **Limit Amount**: Maximum spending threshold
- **User-Specific**: Personal budget management
- **Multiple Budgets**: Create multiple budgets for different categories

**API Endpoint**: `POST /api/budgets`

**Request Example**:
```json
{
  "userId": 1,
  "category": "Food",
  "limitAmount": 5000.00,
  "startDate": "2024-01-01",
  "endDate": "2024-01-31"
}
```

**Use Cases**:
- Set monthly spending limits
- Control category-specific spending
- Plan quarterly budgets
- Manage annual financial goals

#### View & Manage Budgets
- **List All Budgets**: View all user budgets
- **Individual Budget**: Get specific budget details
- **Active Budget Lookup**: Find active budget for category
- **Update Budget**: Modify limits, dates, categories
- **Delete Budget**: Remove expired or unused budgets

**API Endpoints**:
- `GET /api/budgets/user/{userId}` - User budgets
- `GET /api/budgets/{id}` - Specific budget
- `PUT /api/budgets/{id}` - Update budget
- `DELETE /api/budgets/{id}` - Delete budget

#### Real-time Spending Calculation
- **Automatic Calculation**: System calculates spending vs budget
- **Category Matching**: Matches expenses to budget categories
- **Date Range Filtering**: Only counts expenses within budget period
- **Remaining Budget**: Calculates available amount

**API Endpoint**: `GET /api/budgets/{id}/spending`

**Calculation Logic**:
```
Total Spending = Sum of expenses where:
  - expense.category = budget.category
  - expense.date >= budget.startDate
  - expense.date <= budget.endDate

Remaining = budget.limitAmount - Total Spending
```

#### Budget Alerts & Notifications
- **90% Threshold Alert**: Warning when spending reaches 90% of limit
- **Over-Limit Alert**: Critical alert when budget exceeded
- **Automatic Creation**: System generates notifications automatically
- **Batch Checking**: Check all user budgets at once

**API Endpoint**: `POST /api/budgets/check-alerts/user/{userId}`

**Alert Types**:
1. **Approaching Limit (90%+)**: "⚠️ Budget Alert: You have only ₹X left in your {category} budget!"
2. **Over Limit (100%+)**: "🚨 Budget Alert: You have exceeded your {category} budget of ₹X! Current spending: ₹Y"

**Response Example**:
```json
{
  "budgetsChecked": 5,
  "alertsCreated": 2,
  "message": "2 budget alert(s) created"
}
```

**Use Cases**:
- Prevent overspending
- Early warning system
- Stay within limits
- Financial discipline

---

## Recurring Bills Features

### 5. Recurring Bill Management

#### Create Recurring Bill
**Features**:
- **Bill Details**: Name, amount, category, description
- **Frequency Options**: DAILY, WEEKLY, MONTHLY, YEARLY
- **Due Date Tracking**: Day of month when bill is due (1-31)
- **Next Due Date**: Track upcoming payment date
- **Reminder Settings**:
  - Days before due date (default: 2 days)
  - Reminder time (hour: 0-23, minute: 0-59)

**API Endpoint**: `POST /api/recurring-bills`

**Request Example**:
```json
{
  "userId": 1,
  "billName": "Netflix Subscription",
  "amount": 649.00,
  "category": "Entertainment",
  "frequency": "MONTHLY",
  "dayOfMonthDue": 15,
  "nextDueDate": "2024-02-15",
  "description": "Monthly streaming subscription",
  "reminderDaysBefore": 2,
  "reminderHour": 9,
  "reminderMinute": 0
}
```

**Use Cases**:
- Track subscription services (Netflix, Spotify, Amazon Prime)
- Monitor utility bills (electricity, water, internet, phone)
- Manage rent/mortgage payments
- Track insurance premiums
- Monitor loan EMIs

#### View & Manage Bills
- **List All Bills**: View all recurring bills
- **Individual Bill**: Get specific bill details
- **Update Bill**: Modify details, frequency, reminders
- **Delete Bill**: Remove cancelled subscriptions

**API Endpoints**:
- `GET /api/recurring-bills/user/{userId}` - User bills
- `GET /api/recurring-bills/{id}` - Specific bill
- `PUT /api/recurring-bills/{id}` - Update bill
- `DELETE /api/recurring-bills/{id}` - Delete bill

#### Payment Tracking

**Mark as Paid**:
- Records payment completion
- Updates isPaid flag to true
- Records payment date
- Prepares for next billing cycle

**API Endpoint**: `POST /api/recurring-bills/{id}/mark-paid`

**Mark as Unpaid**:
- Reverts to unpaid status
- Creates reminder notification
- Calculates days until due
- Alerts user of upcoming payment

**API Endpoint**: `POST /api/recurring-bills/{id}/mark-unpaid`

**Notification Example**: "Bill Alert: Netflix Subscription is now due in 5 days (Due: 2024-02-15). Amount: ₹649.00"

**Use Cases**:
- Track payment status
- Handle failed payments
- Reschedule payments
- Maintain payment history

---

## Notifications System

### 6. Notification Management

#### Automatic Notification Creation
- **Budget Alerts**: Created when budgets are exceeded or approaching limit
- **Bill Reminders**: Created when bills marked unpaid
- **System Notifications**: Generated by various system events
- **Timestamp Tracking**: Records creation time

**Service**: `NotificationService.createNotification(userId, message)`

#### View Notifications
- **All Notifications**: Complete notification history
- **Unread Only**: Filter unread notifications
- **Chronological Order**: Sorted by creation time
- **User-Specific**: Each user sees only their notifications

**API Endpoints**:
- `GET /api/notifications/{userId}` - All notifications
- `GET /api/notifications/{userId}/unread` - Unread only

#### Mark as Read/Unread
- **Individual Marking**: Mark specific notification
- **Bulk Marking**: Mark all as read at once
- **Status Toggle**: Switch between read/unread
- **Visual Indicators**: UI shows read status

**API Endpoints**:
- `POST /api/notifications/{notificationId}/mark-read`
- `POST /api/notifications/{notificationId}/mark-unread`
- `POST /api/notifications/{userId}/mark-all-read`

#### Delete Notifications
- **Individual Deletion**: Remove specific notification
- **Bulk Deletion**: Delete all user notifications
- **Permanent Removal**: Cannot be recovered

**API Endpoints**:
- `DELETE /api/notifications/{notificationId}`
- `DELETE /api/notifications/user/{userId}/delete-all`

**Use Cases**:
- Stay informed about budget status
- Track bill payments
- Receive financial alerts
- Manage notification list

---

## Reports & Analytics

### 7. Report Generation

#### CSV Reports
- **Format**: Comma-Separated Values (.csv)
- **Content**: All user expenses with complete details
- **Compatible**: Excel, Google Sheets, data analysis tools
- **Use**: Data import, custom analysis

**API Endpoint**: `GET /api/reports/user/{userId}?format=csv`

**CSV Structure**:
```csv
ID,Title,Description,Amount,Date,Category,Payment Method,Expense Type
1,Grocery Shopping,Weekly groceries,2500.00,2024-01-15,Food,Credit Card,PERSONAL
```

**Use Cases**:
- Import into Excel
- Custom charts and graphs
- Share with accountants
- Tax preparation

#### Excel Reports
- **Format**: Microsoft Excel (.xlsx)
- **Content**: Formatted spreadsheet
- **Features**: Column headers, formatted data
- **Professional**: Ready for presentations

**API Endpoint**: `GET /api/reports/user/{userId}?format=excel`

**Use Cases**:
- Professional expense reports
- Business presentations
- Detailed financial analysis
- Management reporting

#### PDF Reports
- **Format**: Portable Document Format (.pdf)
- **Content**: Formatted, printable report
- **Features**: Professional layout, headers, totals
- **Print-Ready**: Suitable for physical copies

**API Endpoint**: `GET /api/reports/user/{userId}?format=pdf`

**Use Cases**:
- Print physical copies
- Email to stakeholders
- Official documentation
- Tax filing
- Reimbursement claims

#### Report Features
- **Automatic Filename**: Includes user ID and timestamp
- **Access Control**: Users access only their reports, admins access all
- **Authentication Required**: Must be logged in
- **Format Validation**: Validates requested format

**Filename Format**: `user_expenses_{userId}_{timestamp}.{extension}`

### 8. Dashboard Analytics

#### Visual Charts
- **Bar Charts**: Monthly/weekly spending trends
- **Pie Charts**: Category-wise distribution
- **Responsive Design**: Adapts to screen size
- **Interactive**: Tooltips and hover effects

**Chart Library**: Recharts 2.10.3

**Chart Types**:
1. **Category Distribution (Pie Chart)**: Percentage per category, color-coded
2. **Time-Based Spending (Bar Chart)**: Weekly/monthly/yearly views
3. **Budget Utilization (Progress Bars)**: Visual budget usage, color-coded warnings

#### Statistics Dashboard
- **Total Expenses**: Lifetime total spending
- **Monthly Expenses**: Current month spending
- **Budget Utilization**: Number of active budgets
- **Unread Notifications**: Alert count
- **Recent Expenses**: Latest transactions
- **Top Categories**: Highest spending categories

**Auto-Refresh**: Updates every 30 seconds

**Use Cases**:
- Quick financial overview
- Spending pattern analysis
- Budget monitoring
- Trend identification

---

## User Preferences & Settings

### 9. Display & Accessibility

#### Dark Mode
- **Toggle Theme**: Switch between light and dark
- **Persistent**: Saved to database and localStorage
- **Immediate Application**: Changes apply instantly
- **Eye Comfort**: Reduces eye strain

**API Endpoint**: `PUT /api/users/{id}/preferences`

**Request**: `{ "darkMode": true }`

**Use Cases**:
- Night-time usage
- Reduce eye strain
- Personal preference
- Battery saving (OLED screens)

#### Accessibility Mode
- **Enhanced Accessibility**: Improved contrast and readability
- **Screen Reader Support**: Better assistive technology compatibility
- **Keyboard Navigation**: Enhanced shortcuts
- **Font Size Options**: Adjustable text sizes

**Use Cases**:
- Users with visual impairments
- Better readability
- Accessibility compliance
- Inclusive design

### 10. Currency Preferences

#### Multi-Currency Support
**Supported Currencies**:
- INR (Indian Rupee) - ₹
- USD (US Dollar) - $
- EUR (Euro) - €
- GBP (British Pound) - £

**Features**:
- Default: INR
- Display Format: Currency symbol with amount
- Persistent Setting: Saved to user profile
- Application-Wide: Affects all displays

**API Endpoint**: `PUT /api/users/{id}/preferences`

**Request**: `{ "preferredCurrency": "USD" }`

**Use Cases**:
- International users
- Multi-currency tracking
- Travel expenses
- Foreign transactions

### 11. Profile Management

#### Profile Information
- **Username**: Display name
- **Email**: Contact email
- **Birthdate**: Date of birth (optional)
- **Profile Photo**: Profile picture URL

#### Profile Photo Upload
- **Upload Support**: Multipart form data
- **URL Storage**: Stores photo URL (up to 1000 characters)
- **Display**: Shows in navbar and profile

**API Endpoint**: `POST /api/users/{id}/upload-photo`

**Use Cases**:
- Personalize account
- Visual identification
- Professional appearance

---

## Category Management

### 12. Category Features

#### Predefined Categories
- Food & Dining
- Transportation
- Shopping
- Entertainment
- Bills & Utilities
- Healthcare
- Education
- Travel
- Personal Care
- Other

#### Create Custom Category
- **Unique Name**: Case-insensitive uniqueness
- **Description**: Category purpose
- **Icon**: Category icon identifier
- **Duplicate Prevention**: Validates unique names

**API Endpoint**: `POST /api/categories`

**Request Example**:
```json
{
  "name": "Gym & Fitness",
  "description": "Gym membership and fitness expenses",
  "icon": "dumbbell"
}
```

#### Manage Categories
- **View All**: List all categories
- **Update**: Modify name, description, icon
- **Delete**: Remove unused categories

**API Endpoints**:
- `GET /api/categories` - All categories
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

#### Category Colors (Frontend)
- **Color-Coded**: Visual distinction
- **Consistent**: Same color across app
- **Chart Integration**: Used in pie/bar charts

**Use Cases**:
- Create specialized categories
- Organize expenses better
- Track specific spending areas
- Personalize tracking

---

## Security Features

### 13. Authentication & Authorization

#### Session-Based Authentication
- **Spring Security**: Industry-standard framework
- **Session Management**: Server-side session storage
- **HTTP-Only Cookies**: Prevents XSS attacks
- **CSRF Protection**: Cross-Site Request Forgery prevention
- **Secure Sessions**: Encrypted session data

#### Password Security
- **BCrypt Hashing**: Industry-standard hashing
- **Salt Generation**: Automatic salt per password
- **One-Way Encryption**: Cannot be decrypted
- **Configurable Strength**: Default 10 rounds

#### Role-Based Access Control
- **User Roles**: USER and ADMIN
- **Permission Levels**:
  - USER: Access own data only
  - ADMIN: Access all user data
- **Endpoint Protection**: Role-based access
- **Data Isolation**: Users cannot access others' data

### 14. Data Security

#### SQL Injection Prevention
- **JPA/Hibernate**: Parameterized queries
- **Prepared Statements**: Automatic prevention
- **Input Validation**: Server-side validation
- **Type Safety**: Strong typing

#### XSS Protection
- **Input Sanitization**: Clean user inputs
- **Output Encoding**: Encode before display
- **Content Security Policy**: HTTP headers
- **HTTP-Only Cookies**: JavaScript cannot access

#### CORS Configuration
- **Allowed Origins**: Configured origins (localhost:3000)
- **Credentials Support**: Cross-origin credentials
- **Method Restrictions**: Specific HTTP methods
- **Header Control**: Controlled headers

**Configuration**: `@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")`

---

## UI/UX Features

### 15. User Interface

#### Modern Design
- **Gradient Backgrounds**: Beautiful gradient color schemes
- **Card-Based Layout**: Clean, organized card components
- **Responsive Design**: Mobile-friendly, adapts to all screen sizes
- **Smooth Animations**: Transitions and hover effects
- **Loading States**: Spinners and skeleton screens
- **Empty States**: Helpful messages when no data

#### Navigation
- **Navbar**: Persistent navigation bar
- **User Menu**: Profile dropdown with quick actions
- **Breadcrumbs**: Clear navigation path
- **Quick Actions**: Floating action buttons

#### Forms
- **Validation**: Real-time form validation
- **Error Messages**: Clear, helpful error messages
- **Auto-Save**: Draft saving (future feature)
- **Date Pickers**: Calendar-based date selection
- **Dropdowns**: Searchable category/payment method selectors

#### Interactive Elements
- **Tooltips**: Helpful hover information
- **Modals**: Confirmation dialogs
- **Toasts**: Success/error notifications
- **Progress Bars**: Budget utilization indicators
- **Badges**: Notification counts

### 16. User Experience

#### Performance
- **Fast Loading**: Optimized API calls
- **Lazy Loading**: Load data as needed
- **Caching**: LocalStorage for preferences
- **Auto-Refresh**: Dashboard updates every 30 seconds

#### Accessibility
- **Keyboard Navigation**: Full keyboard support
- **Screen Reader**: ARIA labels and roles
- **Color Contrast**: WCAG compliant
- **Focus Indicators**: Clear focus states

#### Error Handling
- **Graceful Degradation**: Fallbacks for errors
- **User-Friendly Messages**: Clear error explanations
- **Retry Options**: Retry failed operations
- **Offline Detection**: Detect network issues

---

## Technical Architecture

### 17. Backend Architecture

#### Layered Architecture
1. **Controller Layer**: REST API endpoints
2. **Service Layer**: Business logic
3. **Repository Layer**: Data access
4. **Entity Layer**: Database models

#### Design Patterns
- **Dependency Injection**: Spring IoC container
- **Repository Pattern**: Data access abstraction
- **DTO Pattern**: Data transfer objects
- **Service Pattern**: Business logic encapsulation

#### Database Design
- **Relational Model**: MySQL 8.0
- **JPA/Hibernate**: ORM framework
- **Entity Relationships**:
  - User → Expenses (One-to-Many)
  - User → Budgets (One-to-Many)
  - User → RecurringBills (One-to-Many)
  - User → Notifications (One-to-Many)
  - User → Roles (Many-to-Many)

### 18. Frontend Architecture

#### Component Structure
- **Functional Components**: React hooks-based
- **Context API**: Global state management (AuthContext)
- **Custom Hooks**: Reusable logic
- **Service Layer**: API integration (api.js)

#### Routing
- **React Router**: Client-side routing
- **Protected Routes**: Authentication-required routes
- **Lazy Loading**: Code splitting (future)

#### State Management
- **Local State**: useState for component state
- **Global State**: Context API for auth
- **Server State**: API responses
- **Persistent State**: localStorage for preferences

### 19. API Design

#### RESTful Principles
- **Resource-Based**: URLs represent resources
- **HTTP Methods**: GET, POST, PUT, DELETE
- **Status Codes**: Proper HTTP status codes
- **JSON Format**: Request/response in JSON

#### API Structure
```
/api/auth          - Authentication endpoints
/api/users         - User management
/api/expenses      - Expense operations
/api/budgets       - Budget management
/api/recurring-bills - Recurring bill operations
/api/notifications - Notification management
/api/categories    - Category operations
/api/reports       - Report generation
```

#### Error Handling
- **Consistent Format**: Standardized error responses
- **Error Codes**: HTTP status codes
- **Error Messages**: User-friendly messages
- **Stack Traces**: Development environment only

---

## Application Use Cases

### 20. Real-World Applications

#### Personal Finance Management
- Track daily expenses
- Monitor monthly spending
- Set and maintain budgets
- Manage subscription services
- Generate tax reports

#### Business Expense Tracking
- Separate personal and professional expenses
- Track client-related expenses
- Generate reimbursement reports
- Monitor project costs
- Prepare tax documentation

#### Family Budget Management
- Shared expense tracking
- Family budget planning
- Bill payment reminders
- Spending pattern analysis
- Financial goal setting

#### Student Finance Management
- Track education expenses
- Manage limited budgets
- Monitor scholarship funds
- Plan semester expenses
- Generate expense reports for parents

#### Freelancer/Contractor Use
- Track business expenses
- Separate personal/business spending
- Generate client invoices
- Tax preparation
- Profit/loss analysis

---

## Future Enhancements

### 21. Planned Features

#### Advanced Features
- Real-time notifications (WebSocket)
- Mobile app (React Native)
- Offline support (PWA)
- AI-powered insights
- Multi-language support
- Advanced analytics
- Budget forecasting
- Expense sharing between users
- Receipt scanning (OCR)
- Bank account integration

#### Improvements
- Export to more formats (JSON, XML)
- Scheduled reports (email)
- Custom report templates
- Advanced filtering options
- Bulk operations
- Data import from other apps
- API rate limiting
- Two-factor authentication
- Password reset via email
- Social login (Google, Facebook)

---

## Summary

**Expenses Tracker** is a feature-rich, secure, and user-friendly application that provides comprehensive financial management capabilities. With its intuitive interface, powerful features, and robust architecture, it serves as an excellent solution for personal and professional expense tracking needs.

### Key Statistics
- **29 Backend Controllers/Services**
- **24 Frontend Components**
- **50+ API Endpoints**
- **6 Main Feature Modules**
- **4 Currency Support**
- **3 Report Formats**
- **2 User Roles**
- **100% Responsive Design**

### Technology Highlights
- Modern Spring Boot 3.5.6 backend
- React 19.2.0 frontend
- MySQL 8.0 database
- Session-based security
- RESTful API design
- Responsive UI/UX
- Production-ready code

---

**Made with ❤️ using Spring Boot and React**
