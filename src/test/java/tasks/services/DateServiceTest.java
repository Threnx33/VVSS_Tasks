package tasks.services;

import com.sun.tools.jconsole.JConsoleContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tasks.model.ArrayTaskList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DateServiceTest {
    @Mock
    private TasksService tasksService;

    @Mock
    private DateService dateService;

    @BeforeEach
    void setUp() {
        dateService = new DateService(tasksService);
    }

    @AfterEach
    void tearDown() {
    }

    @Order(1)
    @Test
    @Tag("ecp_valid")
    @DisplayName("Test valid - ECP")
    void getDateMergedWithTime_ECP_valid() {
        String time = "12:40";
        System.out.println(new Date());
        Date date = dateService.getDateMergedWithTime(time, new Date());
        assertEquals(date.getHours(), 12);
        assertEquals(date.getMinutes(), 40);
    }

    @Order(2)
    @Test
    @DisplayName("Test invalid - ECP")
    @Timeout(1)
    void getDateMergedWithTime_ECP_Invalid(){
        String time = "70:70";
        try{
            Date date = dateService.getDateMergedWithTime(time, new Date());
        }catch(IllegalArgumentException ex){
            assertTrue(true);
        }
    }

    @ParameterizedTest
    @Order(3)
    @Timeout(1)
    @ValueSource(strings = {"25:00", "24:60"})
    @DisplayName("Test invalid - BVA")
    void getDateMergedWithTime_BVA_invalid(String time){//border limit
        try{
            Date date = dateService.getDateMergedWithTime(time, new Date());
        }catch(IllegalArgumentException ex){
            assertTrue(true);
        }
    }

    @ParameterizedTest
    @Order(4)
    @Timeout(1)
    @ValueSource(strings = {"25:60"})
    @DisplayName("Test invalid - BVA")
    void getDateMergedWithTime_BVA_invalid2(String time){//border limit
        try{
            Date date = dateService.getDateMergedWithTime(time, new Date());
        }catch(IllegalArgumentException ex){
            System.out.println("Ora invalida");
            assertTrue(true);
        }
    }

    @Order(5)
    @Test
    @Timeout(1)
    @DisplayName("Test valid - BVA")
    void getDateMergedWithTime_BVA_valid(){
        String time = "23:59";
        Date date = dateService.getDateMergedWithTime(time, new Date());
        System.out.println(new Date());
        assertEquals(date.getHours(), 23);
        assertEquals(date.getMinutes(), 59);

        String time2 = "00:00";
        Date date2 = dateService.getDateMergedWithTime(time2, new Date());
        assertEquals(date2.getHours(), 0);
        assertEquals(date2.getMinutes(), 0);
    }
    @Order(6)
    @Test
    @Timeout(1)
    @DisplayName("Test invalid - BVA")
    void getDateMergedWithTime_BVA_invalid2(){
        String time = "12:20";
        Date date = new Date(); date.setYear(2022);
        System.out.println(date.getYear());
        try{
            Date dateRet = dateService.getDateMergedWithTime(time, date);
        }catch (IllegalArgumentException ex){
            assertTrue(true);
        }
    }
}