# 💰 Expenses Tracker - Full Stack Application

A modern, full-featured expense tracking application built with Spring Boot and React.

![Status](https://img.shields.io/badge/status-ready-green)
![Backend](https://img.shields.io/badge/backend-Spring%20Boot%203.5.6-green)
![Frontend](https://img.shields.io/badge/frontend-React%2019.2.0-blue)
![Database](https://img.shields.io/badge/database-MySQL%208.0-orange)

## 🌟 Features

### Core Features
- ✅ **User Authentication** - Secure login/registration with session management
- ✅ **Expense Management** - Full CRUD with advanced filtering
- ✅ **Budget Tracking** - Set limits and get alerts
- ✅ **Group Expenses** - Split expenses with family/friends
- ✅ **Notifications** - Real-time alerts for budget limits
- ✅ **Reports** - Download CSV, Excel, and PDF reports
- ✅ **User Preferences** - Dark mode, accessibility, currency

### Advanced Features
- 🔍 **Smart Search** - Find expenses by keyword
- 📊 **Analytics Dashboard** - Visual charts and statistics
- 📌 **Pin Expenses** - Mark important expenses
- 🎨 **Modern UI** - Beautiful, responsive design
- 🔐 **Role-based Access** - USER and ADMIN roles
- 💱 **Multi-currency** - Support for INR, USD, EUR, GBP

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Node.js 16+
- MySQL 8.0+
- Maven (included)

### 1. Setup Database
```sql
CREATE DATABASE expenses_tracker;
```

### 2. Configure Backend
Edit `expenses_tracker_backend/src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3. Start Application

**Option A: Use Start Script (Windows)**
```bash
START_APP.bat
```

**Option B: Manual Start**
```bash
# Terminal 1 - Backend
cd expenses_tracker_backend
mvnw.cmd spring-boot:run

# Terminal 2 - Frontend
cd expenses_tracker_frontend
npm install
npm start
```

### 4. Access Application
- **Frontend**: http://localhost:3000
- **Backend API**: http://localhost:8083

### 5. First Time Setup
1. Click "Register" on the login page
2. Create your account
3. Login and start tracking expenses!

## 📁 Project Structure

```
expenses_tracker/
├── expenses_tracker_backend/     # Spring Boot API
│   ├── src/main/java/
│   │   └── com/expenses_tracker/
│   │       ├── controller/       # REST endpoints
│   │       ├── entity/          # Database models
│   │       ├── repository/      # Data access
│   │       ├── service/         # Business logic
│   │       └── config/          # Security config
│   └── src/main/resources/
│       └── application.properties
│
├── expenses_tracker_frontend/    # React UI
│   ├── src/
│   │   ├── components/          # React components
│   │   ├── context/             # Global state
│   │   ├── services/            # API integration
│   │   └── App.js               # Main app
│   └── package.json
│
├── COMPLETE_SETUP_GUIDE.md      # Detailed setup
├── IMPLEMENTATION_SUMMARY.md     # What was built
├── POSTMAN_STEP_BY_STEP_TESTING.md  # API testing
└── START_APP.bat                # Quick start script
```

## 🎯 Key Endpoints

### Authentication
- `POST /api/auth/register` - Create account
- `POST /api/auth/login` - Login
- `GET /api/auth/me` - Get current user

### Expenses
- `GET /api/expenses` - List all expenses
- `POST /api/expenses` - Create expense
- `PUT /api/expenses/{id}` - Update expense
- `DELETE /api/expenses/{id}` - Delete expense
- `GET /api/expenses/search?keyword=` - Search

### Budgets
- `GET /api/budgets` - List budgets
- `POST /api/budgets` - Create budget
- `GET /api/budgets/user/{userId}` - User budgets

### Groups
- `GET /api/groups` - List groups
- `POST /api/groups` - Create group
- `POST /api/groups/{id}/expenses` - Add group expense

### Reports
- `GET /api/reports/user/{userId}?format=csv` - CSV report
- `GET /api/reports/user/{userId}?format=excel` - Excel report
- `GET /api/reports/user/{userId}?format=pdf` - PDF report

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.5.6
- **Security**: Spring Security (Session-based)
- **Database**: MySQL 8.0 with JPA/Hibernate
- **Build Tool**: Maven
- **Java Version**: 17

### Frontend
- **Library**: React 19.2.0
- **Routing**: React Router 6.20.0
- **HTTP Client**: Axios 1.12.2
- **Charts**: Recharts 2.10.3
- **Icons**: Lucide React
- **Build Tool**: Create React App

## 📊 Screenshots

### Dashboard
Beautiful overview with charts and statistics

### Expenses
Manage all your expenses with advanced filters

### Budgets
Track spending against budget limits with visual indicators

### Groups
Split expenses with family and friends

## 📖 Documentation

- **[Complete Setup Guide](COMPLETE_SETUP_GUIDE.md)** - Step-by-step setup
- **[Frontend Guide](expenses_tracker_frontend/FRONTEND_GUIDE.md)** - React app documentation
- **[API Testing Guide](POSTMAN_STEP_BY_STEP_TESTING.md)** - Postman testing
- **[Implementation Summary](IMPLEMENTATION_SUMMARY.md)** - What was built
- **[Database Setup](expenses_tracker_backend/DATABASE_SETUP_GUIDE.md)** - Database configuration

## 🧪 Testing

### Backend Testing
```bash
cd expenses_tracker_backend
mvnw.cmd test
```

### Frontend Testing
```bash
cd expenses_tracker_frontend
npm test
```

### API Testing
Use Postman with the provided guide: `POSTMAN_STEP_BY_STEP_TESTING.md`

## 🔐 Security Features

- ✅ Password encryption (BCrypt)
- ✅ Session-based authentication
- ✅ HTTP-only cookies
- ✅ CORS configuration
- ✅ SQL injection prevention
- ✅ XSS protection
- ✅ Role-based access control

## 🎨 UI Features

- ✅ Modern gradient design
- ✅ Responsive layout (mobile-friendly)
- ✅ Dark mode support
- ✅ Accessibility mode
- ✅ Smooth animations
- ✅ Loading states
- ✅ Error handling
- ✅ Empty states

## 📈 Future Enhancements

- [ ] Real-time notifications (WebSocket)
- [ ] Mobile app (React Native)
- [ ] Offline support (PWA)
- [ ] AI-powered insights
- [ ] Multi-language support
- [ ] Advanced analytics
- [ ] Recurring bill reminders
- [ ] Budget forecasting

## 🐛 Troubleshooting

### Backend won't start
- Check MySQL is running
- Verify database credentials
- Ensure port 8083 is free

### Frontend won't start
- Run `npm install` first
- Check Node.js version (16+)
- Ensure port 3000 is free

### Login not working
- Register a new user (don't use sample data)
- Clear browser cookies
- Check backend logs

### Can't connect to API
- Verify backend is running on port 8083
- Check CORS configuration
- Review network tab in browser DevTools

## 📝 License

This project is for educational purposes.

## 👨‍💻 Development

### Backend Development
```bash
cd expenses_tracker_backend
mvnw.cmd spring-boot:run
```

### Frontend Development
```bash
cd expenses_tracker_frontend
npm start
```

### Build for Production

**Backend:**
```bash
mvnw.cmd clean package -DskipTests
java -jar target/expenses_tracker-0.0.1-SNAPSHOT.jar
```

**Frontend:**
```bash
npm run build
# Deploy the build/ folder
```

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Commit changes
4. Push to branch
5. Create Pull Request

## 📞 Support

For issues or questions:
1. Check documentation files
2. Review troubleshooting section
3. Check browser console for errors
4. Review backend logs

## ⭐ Features Checklist

- [x] User authentication
- [x] Expense CRUD
- [x] Budget tracking
- [x] Group expenses
- [x] Notifications
- [x] Reports (CSV, Excel, PDF)
- [x] Search and filters
- [x] Charts and analytics
- [x] User preferences
- [x] Dark mode
- [x] Responsive design
- [x] Session management
- [x] Role-based access

## 🎉 Success!

You now have a complete, production-ready expense tracking application!

**Start tracking your expenses today!** 💰

---

**Made with ❤️ using Spring Boot and React**
