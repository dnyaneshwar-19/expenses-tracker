# Login Issue - Complete Diagnosis & Fix Guide

## 🔍 Current Status

You're seeing "Login failed. Please check your credentials" even with correct username/password.

## 🎯 Root Cause Analysis

The issue is **NOT** with your credentials. It's one of these:

1. **Backend not restarted** after CORS configuration changes
2. **CORS/Cookie issues** between frontend (port 3000) and backend (port 8083)
3. **Session configuration** not properly set

## ✅ Complete Fix (Step-by-Step)

### Step 1: Stop Everything

1. **Stop Backend** (if running):
   - Go to terminal running backend
   - Press `Ctrl+C`

2. **Stop Frontend** (if running):
   - Go to terminal running frontend  
   - Press `Ctrl+C`

### Step 2: Clear Browser Data

1. **Open Browser DevTools**: Press `F12`
2. **Go to Application tab**
3. **Clear Storage**:
   - Click "Clear site data"
   - Or manually delete all cookies for `localhost`

### Step 3: Verify Database

Open MySQL and check:

```sql
USE expenses_tracker;

-- Check if your user exists
SELECT id, username, email FROM users WHERE username = 'riya';

-- Check if password is encrypted (should start with $2a$)
SELECT username, LEFT(password, 10) as password_start FROM users WHERE username = 'riya';

-- Check if user has roles
SELECT u.username, r.name 
FROM users u 
JOIN user_roles ur ON u.id = ur.user_id 
JOIN roles r ON ur.role_id = r.id 
WHERE u.username = 'riya';
```

**Expected Results:**
- User exists ✅
- Password starts with `$2a$` (BCrypt) ✅
- User has at least one role (ROLE_USER or ROLE_ADMIN) ✅

### Step 4: Start Backend with Logging

```bash
cd expenses_tracker_backend
mvnw.cmd clean spring-boot:run
```

**Wait for this message:**
```
Started ExpensesTrackerApplication in X.XXX seconds
```

### Step 5: Test Backend Directly

Open a new terminal and test:

```bash
curl -X POST http://localhost:8083/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"riya\",\"password\":\"YOUR_PASSWORD\"}"
```

**Expected Response:**
```json
{
  "roles": ["ROLE_USER"],
  "username": "riya"
}
```

**If you get 401 error:**
- Password is wrong OR
- User doesn't exist OR
- User has no roles

### Step 6: Check Backend Logs

Look for these messages in backend terminal:

```
Login attempt for user: riya
Authentication successful for: riya
Session created with ID: XXXXX
```

**If you see "Bad credentials":**
- The password in database doesn't match what you're entering
- Solution: Register a new user

### Step 7: Start Frontend

```bash
cd expenses_tracker_frontend
npm start
```

Browser should open at `http://localhost:3000`

### Step 8: Test Login

1. **Open Browser DevTools** (F12)
2. **Go to Network tab**
3. **Try to login**
4. **Check the login request**:
   - Should be POST to `http://localhost:8083/api/auth/login`
   - Status should be `200 OK`
   - Response should have `Set-Cookie` header

**If you see CORS error:**
- Backend wasn't restarted properly
- Go back to Step 4

**If you see 401 error:**
- Wrong credentials
- Check Step 3 (database)

## 🔧 Common Issues & Solutions

### Issue 1: "Network Error" or "CORS Error"

**Symptoms:**
- Red error in browser console
- Mentions "CORS" or "Access-Control-Allow-Origin"

**Solution:**
```bash
# Stop backend (Ctrl+C)
# Restart backend
cd expenses_tracker_backend
mvnw.cmd spring-boot:run
```

### Issue 2: "Invalid credentials" but password is correct

**Symptoms:**
- 401 error
- Backend logs show "Bad credentials"

**Solution:**
```sql
-- Check password encryption
SELECT username, password FROM users WHERE username = 'riya';

-- If password doesn't start with $2a$, it's not encrypted
-- Register a new user instead of using sample data
```

### Issue 3: Session not persisting

**Symptoms:**
- Login succeeds but redirects back to login
- Can't access protected pages

**Solution:**
1. Check `withCredentials: true` in `src/services/api.js`
2. Clear all browser cookies
3. Try login again

### Issue 4: Backend won't start

**Symptoms:**
- Error about JAVA_HOME
- Port already in use

**Solution:**
```bash
# For JAVA_HOME issue:
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%

# For port issue:
# Change port in application.properties
server.port=8084
# Also update frontend api.js to use 8084
```

## 🧪 Testing Checklist

After following all steps:

- [ ] Backend starts without errors
- [ ] Frontend starts without errors
- [ ] Can access `http://localhost:3000`
- [ ] Can see login page
- [ ] No CORS errors in console
- [ ] Login request goes to backend
- [ ] Login returns 200 OK
- [ ] Cookie is set (check Application tab)
- [ ] Redirects to dashboard
- [ ] Can see user data
- [ ] Can navigate to other pages

## 📱 Mobile Responsiveness

The app is now fully responsive. To test:

1. **Open DevTools** (F12)
2. **Click device toolbar** (Ctrl+Shift+M)
3. **Select a mobile device** (iPhone, Pixel, etc.)
4. **Test all pages**:
   - Login/Register pages adapt
   - Dashboard shows properly
   - Expenses grid becomes single column
   - Navigation becomes hamburger menu

## 🎯 Final Solution

If nothing works, do this:

### Option A: Fresh Start

```bash
# 1. Register a NEW user (don't use old data)
# 2. Use that new user to login
# 3. Should work immediately
```

### Option B: Reset Database

```sql
-- Clear all users
TRUNCATE TABLE user_roles;
TRUNCATE TABLE users;

-- Register fresh via frontend
-- Login should work
```

## 📞 Still Not Working?

If login still fails after all steps:

1. **Share backend logs** - Copy the terminal output
2. **Share browser console** - Copy any errors
3. **Share network tab** - Screenshot of failed request
4. **Confirm**:
   - Backend is running on 8083
   - Frontend is running on 3000
   - MySQL is running
   - Database exists

## ✨ Expected Behavior After Fix

1. **Open** `http://localhost:3000`
2. **Click** "Register here"
3. **Fill form**:
   - Username: `testuser`
   - Email: `test@example.com`
   - Password: `password123`
   - Role: USER
4. **Click** "Register" → Success message
5. **Login** with same credentials
6. **Redirects** to dashboard ✅
7. **See** your data ✅
8. **Navigate** to all pages ✅

---

**Last Updated**: October 22, 2025
**Status**: Complete fix applied - Follow steps above
