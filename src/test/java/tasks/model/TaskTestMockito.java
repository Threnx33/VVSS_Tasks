package tasks.model;

import org.junit.Assert;
import org.testng.annotations.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

public class TaskTestMockito {

    @Test
    public void testSetTitle() {
        Task task = Mockito.mock(Task.class);
        task.setTitle("new title");
        Mockito.verify(task).setTitle("new title");
    }

    @Test
    public void testSetTime() {
        Task task = Mockito.mock(Task.class);
        task.setTime(new Date());
        Mockito.verify(task).setTime(Mockito.any(Date.class));
    }

    @Test
    public void testNextTimeAfter() {
        Task task = Mockito.spy(new Task("test", new Date()));
        Mockito.when(task.isRepeated()).thenReturn(true);
        Mockito.when(task.getStartTime()).thenReturn(new Date(1234567890000L));
        Mockito.when(task.getEndTime()).thenReturn(new Date(1234567990002L));
        Mockito.when(task.getRepeatInterval()).thenReturn(60);
        Date current = new Date(1234567895000L);
        Date expected = new Date(1234567950000L);
        Assert.assertEquals(null, task.nextTimeAfter(current));
    }
}