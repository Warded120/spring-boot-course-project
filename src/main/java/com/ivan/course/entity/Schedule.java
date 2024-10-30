package com.ivan.course.entity;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Schedule {

    @Getter
    private final List<LocalDateTime> schedule;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd yyyy - hh:mm a");

    public Schedule(LocalDate startDate) {
        this.schedule = generateSchedule(startDate);
    }

    private List<LocalDateTime> generateSchedule(LocalDate startDate) {
        List<LocalDateTime> lessonDates = new ArrayList<>();

        // Course duration: 3 months
        LocalDate endDate = startDate.plusMonths(3);
        LocalTime lessonTime = LocalTime.of(10, 0); // Default lesson time, e.g., 10:00 AM

        LocalDate currentDate = startDate;
        int lessonsPerWeek = 0;

        // Iterate over days, adding 3 lessons each week
        while (currentDate.isBefore(endDate)) {
            // Add lesson only on Monday, Wednesday, and Friday
            DayOfWeek day = currentDate.getDayOfWeek();
            if (day == DayOfWeek.MONDAY || day == DayOfWeek.WEDNESDAY || day == DayOfWeek.FRIDAY) {
                lessonDates.add(LocalDateTime.of(currentDate, lessonTime));
                lessonsPerWeek++;
            }
            // Reset lessonsPerWeek counter after Sunday
            if (lessonsPerWeek == 3) {
                lessonsPerWeek = 0;
                currentDate = currentDate.plusDays(2); // Skip to next week
            } else {
                currentDate = currentDate.plusDays(1);
            }
        }
        return lessonDates;
    }

    // Method to get schedule as formatted strings
    public List<String> getFormattedSchedule() {
        List<String> formattedDates = new ArrayList<>();
        for (LocalDateTime dateTime : schedule) {
            formattedDates.add(dateTime.format(formatter));
        }
        return formattedDates;
    }

    public List<String> getFormattedSchedule(int amount) {
        return getFormattedSchedule().subList(0, amount);
    }
}
