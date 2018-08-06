package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Override
    @Transactional
    Meal save(Meal meal);

    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int userId);

    Meal findByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.id=:id")
    Meal getByIdWithUser(@Param("id") int id);
}
