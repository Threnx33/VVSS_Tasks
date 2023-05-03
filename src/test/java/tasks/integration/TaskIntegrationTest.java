package tasks.integration;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TaskIntegrationTest {
    private TasksService service;
    private ArrayTaskList spiedRepository;
    private Task t1;
    private Task t2;
    private Task t3;

    @BeforeEach
    void setUp() {
        ArrayTaskList repository = new ArrayTaskList();
        this.spiedRepository = spy(repository);
        this.service = new TasksService(this.spiedRepository);
        this.t1 = mock(Task.class);
        this.t2 = mock(Task.class);
        this.t3 = new Task("task3", new Date("2020/5/10"), new Date("2020/5/20"), 5160);
    }

    @Test
    void test_get_observable_list() {
        when(this.spiedRepository.size()).thenReturn(3);
        when(this.service.getObservableList()).thenReturn(FXCollections.observableArrayList(Arrays.asList(this.t1, this.t2, this.t3)));
        doNothing().when(this.spiedRepository).add(this.t1);

        this.spiedRepository.add(this.t1);
        this.spiedRepository.add(this.t2);
        this.spiedRepository.add(this.t3);

        verify(this.spiedRepository, times(1)).add(this.t1);
        assertEquals(3, this.service.getObservableList().size());
    }

    @Test
    void test_get_interval_in_hours() {
        doNothing().when(this.spiedRepository).add(this.t3);
        this.spiedRepository.add(this.t3);
        verify(this.spiedRepository, times(1)).add(this.t3);
        assertEquals("01:26", this.service.getIntervalInHours(this.t3));
    }
}