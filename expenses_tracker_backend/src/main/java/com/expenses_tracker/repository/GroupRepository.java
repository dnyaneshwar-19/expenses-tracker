package com.expenses_tracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.expenses_tracker.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * Find all groups where the user is a member
     */
    @Query("SELECT g FROM Group g JOIN g.members m WHERE m.id = :userId")
    List<Group> findByUserId(@Param("userId") Long userId);

    /**
     * Find group by name (case insensitive)
     */
    Optional<Group> findByNameIgnoreCase(String name);

    /**
     * Check if user is member of group
     */
    @Query("SELECT COUNT(g) > 0 FROM Group g JOIN g.members m WHERE g.id = :groupId AND m.id = :userId")
    boolean isUserMemberOfGroup(@Param("groupId") Long groupId, @Param("userId") Long userId);

    /**
     * Get total expenses for a group
     */
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.group.id = :groupId")
    java.math.BigDecimal getTotalExpensesForGroup(@Param("groupId") Long groupId);

    /**
     * Get member count for a group
     */
    @Query("SELECT COUNT(m) FROM Group g JOIN g.members m WHERE g.id = :groupId")
    Long getMemberCount(@Param("groupId") Long groupId);
}
