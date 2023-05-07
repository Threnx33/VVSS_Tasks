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

public class RepositoryIntegrationTest {
    private TasksService service;
    private ArrayTaskList spiedRepository;

    @BeforeEach
    void setUp() {
        ArrayTaskList repository = new ArrayTaskList();
        this.spiedRepository = spy(repository);
        this.service = new TasksService(this.spiedRepository);
    }

    @Test
    void test_get_observable_list() {
        Task t1 = mock(Task.class);
        Task t2 = mock(Task.class);
        Task t3 = mock(Task.class);

        when(this.spiedRepository.size()).thenReturn(3);
        when(this.service.getObservableList()).thenReturn(FXCollections.observableArrayList(Arrays.asList(t1, t2, t3)));
        doNothing().when(this.spiedRepository).add(t1);

        this.spiedRepository.add(t1);
        this.spiedRepository.add(t2);
        this.spiedRepository.add(t3);

        verify(this.spiedRepository, times(1)).add(t1);
        assertEquals(3, this.service.getObservableList().size());
    }

    @Test
    void test_get_interval_in_hours() {
        Task t = new Task("title1", new Date("2020/4/27"), new Date("2020/4/30"), 5160);

        doNothing().when(this.spiedRepository).add(t);
        this.spiedRepository.add(t);
        verify(this.spiedRepository, times(1)).add(t);
        assertEquals("01:26", this.service.getIntervalInHours(t));
    }
}