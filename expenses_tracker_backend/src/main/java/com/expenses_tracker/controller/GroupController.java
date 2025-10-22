package com.expenses_tracker.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenses_tracker.entity.Expense;
import com.expenses_tracker.entity.Group;
import com.expenses_tracker.entity.User;
import com.expenses_tracker.repository.ExpenseRepository;
import com.expenses_tracker.repository.GroupRepository;
import com.expenses_tracker.repository.UserRepository;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    /**
     * Create a new group
     */
    @PostMapping
    public ResponseEntity<?> createGroup(@RequestBody Group group, 
                                       @AuthenticationPrincipal UserDetails currentUser) {
        try {
            User user = getUserFromDetails(currentUser);
            
            Group newGroup = new Group();
            newGroup.setName(group.getName());
            newGroup.setDescription(group.getDescription());
            newGroup.setCreatedDate(LocalDateTime.now());
            
            // Add creator as first member
            newGroup.addMember(user);
            
            Group savedGroup = groupRepository.save(newGroup);
            
            return ResponseEntity.status(201).body(Map.of(
                "message", "Group created successfully",
                "groupId", savedGroup.getId(),
                "groupName", savedGroup.getName()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get all groups for current user
     */
    @GetMapping
    public ResponseEntity<?> getUserGroups(@AuthenticationPrincipal UserDetails currentUser) {
        try {
            User user = getUserFromDetails(currentUser);
            List<Group> groups = groupRepository.findByUserId(user.getId());
            
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get group by ID
     */
    @GetMapping("/{groupId}")
    public ResponseEntity<?> getGroup(@PathVariable Long groupId,
                                    @AuthenticationPrincipal UserDetails currentUser) {
        try {
            User user = getUserFromDetails(currentUser);
            
            Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
            
            // Check if user is member
            if (!groupRepository.isUserMemberOfGroup(groupId, user.getId())) {
                return ResponseEntity.status(403).body(Map.of("error", "Access denied"));
            }
            
            return ResponseEntity.ok(group);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Add members to group
     */
    @PostMapping("/{groupId}/members")
    public ResponseEntity<?> addMembersToGroup(@PathVariable Long groupId,
                                             @RequestBody Map<String, List<Long>> request,
                                             @AuthenticationPrincipal UserDetails currentUser) {
        try {
            User currentUserEntity = getUserFromDetails(currentUser);
            
            Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
            
            // Check if current user is member
            if (!groupRepository.isUserMemberOfGroup(groupId, currentUserEntity.getId())) {
                return ResponseEntity.status(403).body(Map.of("error", "Access denied"));
            }
            
            List<Long> userIds = request.get("userIds");
            if (userIds == null || userIds.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "User IDs required"));
            }
            
            for (Long userId : userIds) {
                User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
                group.addMember(user);
            }
            
            groupRepository.save(group);
            
            return ResponseEntity.ok(Map.of("message", "Members added successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Add expense to group
     */
    @PostMapping("/{groupId}/expenses")
    public ResponseEntity<?> addGroupExpense(@PathVariable Long groupId,
                                           @RequestBody Expense expense,
                                           @AuthenticationPrincipal UserDetails currentUser) {
        try {
            User currentUserEntity = getUserFromDetails(currentUser);
            
            Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
            
            // Check if current user is member
            if (!groupRepository.isUserMemberOfGroup(groupId, currentUserEntity.getId())) {
                return ResponseEntity.status(403).body(Map.of("error", "Access denied"));
            }
            
            // Set group and user
            expense.setGroup(group);
            expense.setUser(currentUserEntity);
            
            // If no split details provided, split equally among all members
            if (expense.getSplitDetails() == null || expense.getSplitDetails().isEmpty()) {
                Map<Long, BigDecimal> splitDetails = new HashMap<>();
                BigDecimal totalAmount = expense.getAmount();
                int memberCount = group.getMembers().size();
                BigDecimal amountPerMember = totalAmount.divide(BigDecimal.valueOf(memberCount), 2, java.math.RoundingMode.HALF_UP);
                
                for (User member : group.getMembers()) {
                    splitDetails.put(member.getId(), amountPerMember);
                }
                expense.setSplitDetails(splitDetails);
            }
            
            Expense savedExpense = expenseRepository.save(expense);
            
            return ResponseEntity.status(201).body(Map.of(
                "message", "Group expense added successfully",
                "expenseId", savedExpense.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Get group summary with total expenses and member shares
     */
    @GetMapping("/{groupId}/summary")
    public ResponseEntity<?> getGroupSummary(@PathVariable Long groupId,
                                           @AuthenticationPrincipal UserDetails currentUser) {
        try {
            User user = getUserFromDetails(currentUser);
            
            Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
            
            // Check if user is member
            if (!groupRepository.isUserMemberOfGroup(groupId, user.getId())) {
                return ResponseEntity.status(403).body(Map.of("error", "Access denied"));
            }
            
            // Get total expenses for group
            BigDecimal totalExpenses = groupRepository.getTotalExpensesForGroup(groupId);
            
            // Calculate each member's share
            Map<String, BigDecimal> memberTotals = new HashMap<>();
            
            for (User member : group.getMembers()) {
                memberTotals.put(member.getUsername(), BigDecimal.ZERO);
            }
            
            // Calculate shares from all group expenses
            List<Expense> groupExpenses = expenseRepository.findByGroupId(groupId);
            for (Expense expense : groupExpenses) {
                Map<Long, BigDecimal> splits = expense.getSplitDetails();
                if (splits != null) {
                    for (Map.Entry<Long, BigDecimal> entry : splits.entrySet()) {
                        User member = userRepository.findById(entry.getKey()).orElse(null);
                        if (member != null) {
                            BigDecimal currentTotal = memberTotals.get(member.getUsername());
                            memberTotals.put(member.getUsername(), currentTotal.add(entry.getValue()));
                        }
                    }
                }
            }
            
            // Format with ₹ symbol
            Map<String, String> formattedTotals = new HashMap<>();
            for (Map.Entry<String, BigDecimal> entry : memberTotals.entrySet()) {
                formattedTotals.put(entry.getKey(), "₹" + entry.getValue().toString());
            }
            
            Map<String, Object> summary = new HashMap<>();
            summary.put("groupId", groupId);
            summary.put("groupName", group.getName());
            summary.put("totalExpenses", "₹" + totalExpenses.toString());
            summary.put("memberCount", group.getMembers().size());
            summary.put("memberShares", formattedTotals);
            summary.put("expenseCount", groupExpenses.size());
            
            return ResponseEntity.ok(summary);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Update group details
     */
    @PutMapping("/{groupId}")
    public ResponseEntity<?> updateGroup(@PathVariable Long groupId,
                                        @RequestBody Group groupDetails,
                                        @AuthenticationPrincipal UserDetails currentUser) {
        try {
            User user = getUserFromDetails(currentUser);
            
            Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
            
            // Check if user is member
            if (!groupRepository.isUserMemberOfGroup(groupId, user.getId())) {
                return ResponseEntity.status(403).body(Map.of("error", "Access denied"));
            }
            
            group.setName(groupDetails.getName());
            group.setDescription(groupDetails.getDescription());
            
            Group updatedGroup = groupRepository.save(group);
            
            return ResponseEntity.ok(updatedGroup);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Delete group
     */
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable Long groupId,
                                       @AuthenticationPrincipal UserDetails currentUser) {
        try {
            User user = getUserFromDetails(currentUser);
            
            Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
            
            // Check if user is member
            if (!groupRepository.isUserMemberOfGroup(groupId, user.getId())) {
                return ResponseEntity.status(403).body(Map.of("error", "Access denied"));
            }
            
            groupRepository.delete(group);
            
            return ResponseEntity.ok(Map.of("message", "Group deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private User getUserFromDetails(UserDetails userDetails) {
        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found in database"));
    }
}
