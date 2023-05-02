package tasks.model;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;


class TasksOperationsTest {

    private TasksOperations tasksOperations;

    @BeforeEach
    void setUp() {
        tasksOperations = new TasksOperations(FXCollections.observableArrayList(new ArrayList<>()));
    }

    @Test
    void tc_01() {
        var start = Date.from(Instant.now());
        var end = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        var tasks = tasksOperations.incoming(start, end);
        var result = new ArrayList<>();
        tasks.forEach(result::add);
        assert result.size() == 0;
    }

    @Test
    void tc_02() {
        var start = Date.from(Instant.now());
        var end = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        var task = new Task("test", start, end, 1);
        tasksOperations.tasks.add(task);
        var tasks = tasksOperations.incoming(start, end);
        var result = new ArrayList<>();
        tasks.forEach(result::add);
        assert result.size() == 0;
    }

    @Test
    void tc_03() {
        var start = Date.from(Instant.now());
        var end = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        var task = new Task("test", start, end, 1);
        task.setActive(true);
        tasksOperations.tasks.add(task);
        var tasks = tasksOperations.incoming(start, end);
        var result = new ArrayList<>();
        tasks.forEach(result::add);
        assert result.size() == 1;
    }

    @Test
    void tc_04() {
        var start = Date.from(Instant.now());
        var end = Date.from(Instant.now().plus(30, ChronoUnit.DAYS));
        var task = new Task("test", start, Date.from(Instant.now().plus(20,ChronoUnit.DAYS)), 1);
        task.setActive(true);
        tasksOperations.tasks.add(task);
        var tasks = tasksOperations.incoming(start, end);
        var result = new ArrayList<>();
        tasks.forEach(result::add);
        assert result.size() == 1;
    }

}