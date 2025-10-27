# Expenses Tracker Frontend - Complete Guide

## Overview

This is a complete React frontend application that integrates all features from the Expenses Tracker backend API.

## Features Implemented

### 1. Authentication System
- **Login** - Session-based authentication
- **Register** - Create new user accounts (USER or ADMIN role)
- **Protected Routes** - Automatic redirect to login if not authenticated
- **Session Management** - Persistent login with cookies

### 2. Dashboard
- **Overview Statistics** - Total expenses, monthly expenses, active budgets, notifications
- **Charts & Visualizations**:
  - Monthly expenses bar chart
  - Category-wise pie chart
- **Recent Activity** - Latest 5 expenses

### 3. Expense Management
- **Full CRUD Operations**:
  - Create new expenses
  - View all expenses
  - Update existing expenses
  - Delete expenses
- **Advanced Features**:
  - Pin/Unpin expenses
  - Search by keyword
  - Filter by type (Personal/Professional)
  - Filter by category
  - Filter by payment method
  - Filter by date range
- **Expense Types**: Personal, Professional
- **Categories**: Food, Transport, Entertainment, Shopping, Bills, Healthcare, Education, Business, Other
- **Payment Methods**: Cash, Credit Card, Debit Card, UPI, Net Banking, Company Card

### 4. Budget Management
- **Create Budgets** - Set spending limits for categories
- **Track Progress** - Visual progress bars showing budget utilization
- **Budget Alerts**:
  - Warning when approaching limit (>80%)
  - Alert when over budget (>100%)
- **Date Range** - Set start and end dates for budgets

### 5. Group Expenses
- **Create Groups** - For family or shared expenses
- **Add Members** - Invite users to groups
- **Split Expenses** - Automatic or custom split among members
- **Group Summary** - View total expenses and member shares

### 6. Notifications
- **View Notifications** - All system notifications
- **Unread Notifications** - Filter unread only
- **Mark as Read** - Individual or bulk mark as read
- **Budget Alerts** - Automatic notifications for budget limits

### 7. User Settings
- **Appearance**:
  - Dark Mode toggle
  - Accessibility Mode toggle
- **Currency Preference** - INR, USD, EUR, GBP
- **Reports**:
  - Download CSV reports
  - Download Excel reports
  - Download PDF reports
- **Account Management**:
  - Delete account option

## Tech Stack

- **React** 19.2.0
- **React Router** - Client-side routing
- **Axios** - HTTP client for API calls
- **Recharts** - Charts and data visualization
- **Lucide React** - Modern icon library
- **CSS3** - Custom styling with gradients and animations

## Installation & Setup

### Prerequisites
- Node.js 16+ installed
- Backend running on `http://localhost:8083`

### Steps

1. **Install Dependencies**
```bash
cd expenses_tracker_frontend
npm install
```

2. **Start Development Server**
```bash
npm start
```

The app will open at `http://localhost:3000`

## Project Structure

```
src/
├── components/
│   ├── Auth/
│   │   ├── Login.js
│   │   ├── Register.js
│   │   └── Auth.css
│   ├── Dashboard/
│   │   ├── Dashboard.js
│   │   └── Dashboard.css
│   ├── Expenses/
│   │   ├── ExpenseList.js
│   │   ├── ExpenseForm.js
│   │   └── Expenses.css
│   ├── Budgets/
│   │   ├── BudgetList.js
│   │   ├── BudgetForm.js
│   │   └── Budgets.css
│   ├── Groups/
│   │   ├── GroupList.js
│   │   ├── GroupForm.js
│   │   └── Groups.css
│   ├── Notifications/
│   │   ├── NotificationList.js
│   │   └── Notifications.css
│   ├── Settings/
│   │   ├── Settings.js
│   │   └── Settings.css
│   ├── Layout/
│   │   ├── Navbar.js
│   │   └── Navbar.css
│   └── PrivateRoute.js
├── context/
│   └── AuthContext.js
├── services/
│   └── api.js
├── App.js
├── App.css
└── index.js
```

## API Integration

All API calls are centralized in `src/services/api.js`:

- **authAPI** - Authentication endpoints
- **userAPI** - User management
- **expenseAPI** - Expense CRUD and filters
- **budgetAPI** - Budget management
- **recurringBillAPI** - Recurring bills
- **notificationAPI** - Notifications
- **groupAPI** - Group expenses
- **reportAPI** - Report generation
- **dataAPI** - Test data seeding

## Usage Guide

### First Time Setup

1. **Register an Account**
   - Navigate to `/register`
   - Fill in username, email, password
   - Select role (USER or ADMIN)
   - Click "Register"

2. **Login**
   - Navigate to `/login`
   - Enter credentials
   - Session cookie is automatically stored

3. **Explore Features**
   - Dashboard - Overview of your finances
   - Expenses - Manage all expenses
   - Budgets - Set and track budgets
   - Groups - Create shared expense groups
   - Notifications - View alerts
   - Settings - Customize preferences

### Creating an Expense

1. Go to **Expenses** page
2. Click **"Add Expense"** button
3. Fill in the form:
   - Title (required)
   - Amount (required)
   - Description
   - Date
   - Category
   - Payment Method
   - Type (Personal/Professional)
   - Pin option
4. Click **"Create"**

### Setting a Budget

1. Go to **Budgets** page
2. Click **"Add Budget"** button
3. Fill in:
   - Category
   - Budget Limit
   - Start Date
   - End Date
4. Click **"Create"**
5. Track progress with visual indicators

### Creating a Group

1. Go to **Groups** page
2. Click **"Create Group"**
3. Enter group name and description
4. Add members (requires user IDs)
5. Add group expenses with split details

### Downloading Reports

1. Go to **Settings** page
2. Scroll to **Reports** section
3. Click format button:
   - Download CSV
   - Download Excel
   - Download PDF
4. File downloads automatically

## Styling & Design

- **Color Scheme**: Purple gradient (`#667eea` to `#764ba2`)
- **Modern UI**: Clean cards, smooth transitions, hover effects
- **Responsive**: Mobile-friendly design
- **Icons**: Lucide React for consistent iconography
- **Charts**: Recharts for data visualization

## Environment Configuration

The API base URL is set in `src/services/api.js`:

```javascript
const API_BASE_URL = 'http://localhost:8083/api';
```

Change this if your backend runs on a different port.

## Authentication Flow

1. User logs in via `/api/auth/login`
2. Backend sets session cookie (`JSESSIONID`)
3. Axios automatically includes cookie in subsequent requests (`withCredentials: true`)
4. Protected routes check authentication status
5. Logout clears session

## Error Handling

- API errors display user-friendly messages
- Form validation prevents invalid submissions
- Loading states during async operations
- Empty states when no data exists

## Performance Optimizations

- Lazy loading of components (can be added)
- Efficient re-renders with React hooks
- Debounced search (can be added)
- Cached API responses (can be added)

## Future Enhancements

- [ ] Real-time notifications with WebSocket
- [ ] Offline support with Service Workers
- [ ] Advanced analytics dashboard
- [ ] Export/Import data
- [ ] Multi-language support
- [ ] Theme customization
- [ ] Recurring bill reminders
- [ ] Budget forecasting

## Troubleshooting

### Login Not Working
- Check backend is running on port 8083
- Verify credentials are correct
- Check browser console for errors
- Clear cookies and try again

### Data Not Loading
- Ensure backend API is accessible
- Check network tab in DevTools
- Verify CORS is configured correctly in backend
- Check authentication status

### Charts Not Displaying
- Ensure recharts is installed
- Check if data is being fetched
- Verify data format matches chart requirements

## Support

For issues or questions:
1. Check backend logs
2. Check browser console
3. Verify API endpoints in backend
4. Review network requests in DevTools

## License

This project is part of the Expenses Tracker application.
