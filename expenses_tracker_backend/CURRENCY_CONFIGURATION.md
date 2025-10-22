# Currency Configuration - Expenses Tracker Backend

## Overview

This document outlines the currency configuration for the Expenses Tracker Backend application. The application is configured to use **Indian Rupees (₹)** as the primary currency throughout the system.

## Currency Symbol and Formatting

### Primary Currency Symbol

- **Symbol**: ₹ (Unicode U+20B9)
- **Code**: INR (Indian Rupee)
- **Decimal Places**: 2 (standard for rupees)

### Formatting Pattern

- **Display Format**: `₹%.2f` (e.g., ₹25.50, ₹1,200.00)
- **Database Storage**: BigDecimal with 2 decimal places
- **API Response**: Formatted strings with ₹ symbol

## Implementation Details

### 1. Notification Messages

All notification messages use the rupee symbol:

**Budget Alerts** (`ExpenseServiceImpl.java`):

```java
// Budget exceeded notification
String.format("Budget Alert: You have exceeded your %s budget of ₹%.2f!",
    category, budget.getLimitAmount())

// Budget warning notification
String.format("Budget Alert: You have only ₹%.2f left in your %s budget!",
    remainingBudget, category)
```

**Bill Reminders** (`NotificationService.java`):

```java
String.format("Your '%s' bill of ₹%.2f is due in %d days.",
    bill.getName(), bill.getAmount(), daysAhead)
```

### 2. Sample Data

Sample expense amounts are realistic for Indian currency:

```java
// Lunch expense
expense1.setAmount(new BigDecimal("25.50"));  // ₹25.50

// Client dinner
expense2.setAmount(new BigDecimal("120.00"));  // ₹120.00

// Uber ride
expense3.setAmount(new BigDecimal("15.75"));  // ₹15.75

// Movie ticket
expense4.setAmount(new BigDecimal("30.00")); // ₹30.00
```

### 3. Documentation Examples

All documentation uses rupee examples:

**POSTMAN_TESTING_GUIDE.md**:

- Budget limit: ₹500
- Expense total: ₹450
- Alert message: "Budget Alert: You have only ₹50.00 left in your Food budget!"

## Database Configuration

### Amount Fields

All monetary fields use `BigDecimal` for precise decimal arithmetic:

- `Expense.amount` - Individual expense amounts
- `Budget.limitAmount` - Budget limit amounts
- `RecurringBill.amount` - Recurring bill amounts

### Precision

- **Storage**: BigDecimal with unlimited precision
- **Display**: Formatted to 2 decimal places
- **Calculations**: Maintains precision for financial calculations

## API Response Format

### JSON Response Example

```json
{
  "id": 1,
  "title": "Lunch",
  "amount": 25.5,
  "category": "Food",
  "date": "2024-01-15"
}
```

### Notification Response Example

```json
{
  "id": 1,
  "message": "Budget Alert: You have only ₹50.00 left in your Food budget!",
  "isRead": false,
  "createdAt": "2024-01-15T10:30:00"
}
```

## Frontend Integration

### Currency Display

When integrating with frontend applications:

1. **Amount Display**: Use ₹ symbol prefix
2. **Formatting**: Format to 2 decimal places
3. **Input Validation**: Accept decimal values up to 2 places
4. **Currency Symbol**: Always use ₹ (not Rs. or other variants)

### Example Frontend Code

```javascript
// Format amount for display
const formatCurrency = (amount) => {
  return `₹${amount.toFixed(2)}`;
};

// Example usage
formatCurrency(25.5); // Returns "₹25.50"
```

## Testing

### Test Data

All test data uses realistic rupee amounts:

- Small expenses: ₹10.00 - ₹50.00
- Medium expenses: ₹50.00 - ₹500.00
- Large expenses: ₹500.00 - ₹5000.00

### Budget Testing

- Budget limits: ₹100 - ₹10000
- Alert thresholds: 90% of budget limit
- Sample budgets: ₹500 (Food), ₹2000 (Transport), ₹1000 (Entertainment)

## Configuration Files

### No Currency Configuration Required

The application does not require separate currency configuration files because:

- Currency symbol is hardcoded in notification messages
- Amount formatting is handled by String.format()
- Database stores amounts as BigDecimal (currency-agnostic)

## Migration Notes

### If Changing Currency

To change currency in the future:

1. **Update Notification Messages**: Replace ₹ with new symbol
2. **Update Documentation**: Change examples to new currency
3. **Update Sample Data**: Adjust amounts to new currency scale
4. **Frontend Integration**: Update display formatting

### Backward Compatibility

- Database amounts remain unchanged (BigDecimal)
- API responses maintain same numeric format
- Only display formatting changes

## Best Practices

### Amount Handling

1. **Always use BigDecimal** for monetary calculations
2. **Never use float/double** for currency amounts
3. **Format display** with appropriate currency symbol
4. **Validate input** for reasonable amount ranges

### Notification Messages

1. **Include currency symbol** in all user-facing messages
2. **Use consistent formatting** (₹%.2f pattern)
3. **Provide context** (budget category, remaining amount)

### Documentation

1. **Use realistic amounts** for examples
2. **Include currency symbol** in all examples
3. **Maintain consistency** across all documentation

## Summary

The Expenses Tracker Backend is fully configured for Indian Rupees (₹) with:

- ✅ Consistent rupee symbol usage in notifications
- ✅ Realistic rupee amounts in sample data
- ✅ Proper BigDecimal handling for precision
- ✅ Comprehensive documentation with rupee examples
- ✅ No additional configuration required

The system is ready for production use with Indian currency.
