package tasks.services;

import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.model.TasksOperations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private ArrayTaskList mockTasks;

    private TasksService tasksService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tasksService = new TasksService(mockTasks);
    }

    @Test
    public void testGetObservableList() {
        // Arrange
        Task task1 = new Task("Task 1", new Date(2023, 5, 3, 10, 0), new Date(2023, 5, 3, 10, 0), 3600);
        Task task2 = new Task("Task 2", new Date(2023, 5, 4, 12, 0), new Date(2023, 5, 3, 10, 0), 7200);
        List<Task> expectedTasks = Arrays.asList(task1, task2);
        when(mockTasks.getAll()).thenReturn(expectedTasks);

        // Act
        ObservableList<Task> observableList = tasksService.getObservableList();

        // Assert
        assertEquals(expectedTasks, observableList);
    }

    @Test
    public void testGetIntervalInHours() {
        // Arrange
        Task task = new Task("Task", new Date(2023, 5, 3, 10, 0), new Date(2023, 5, 3, 10, 0), 7200);
        String expectedInterval = "02:00";

        // Act
        String actualInterval = tasksService.getIntervalInHours(task);

        // Assert
        assertEquals(expectedInterval, actualInterval);
    }

    @Test
    public void testFormTimeUnit() {
        // Arrange
        int timeUnit = 2;
        String expectedTimeUnit = "02";

        // Act
        String actualTimeUnit = tasksService.formTimeUnit(timeUnit);

        // Assert
        assertEquals(expectedTimeUnit, actualTimeUnit);
    }

    @Test
    public void testParseFromStringToSeconds() {
        // Arrange
        String stringTime = "01:30";
        int expectedSeconds = 5400;

        // Act
        int actualSeconds = tasksService.parseFromStringToSeconds(stringTime);

        // Assert
        assertEquals(expectedSeconds, actualSeconds);
    }

}