package org.kt.parttime.common.dto;

import org.junit.jupiter.api.Test;
import org.kt.parttime.work.entity.Work;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

class TimeQueryTest {
    @Test
    void t1(){
        TimeQuery timeQuery = TimeQuery.of(2024, 1);
        LocalDateTime localDateTime = timeQuery.thisLocalDateTimeFirstDayOfWeek(1);
        assertThat(localDateTime.getDayOfMonth()).isEqualTo(1);
        assertThat(localDateTime.getMonth().getValue()).isEqualTo(1);
        assertThat(localDateTime.getHour()).isEqualTo(0);
        assertThat(localDateTime.getMinute()).isEqualTo(0);
    }

    @Test
    void t2(){
        TimeQuery timeQuery = TimeQuery.of(2024, 1);
        LocalDateTime localDateTime = timeQuery.thisLocalDateTimeFirstDayOfWeek(2);
        assertThat(localDateTime.getDayOfMonth()).isEqualTo(7);
    }

    @Test
    void t3(){
        TimeQuery timeQuery = TimeQuery.of(2023, 12);
        LocalDateTime localDateTime = timeQuery.thisLocalDateTimeFirstDayOfWeek(2);
        assertThat(localDateTime.getDayOfMonth()).isEqualTo(3);
    }

    @Test
    void t4(){
        TimeQuery timeQuery = TimeQuery.of(2024, 1);
        LocalDateTime localDateTime = timeQuery.thisLocalDateTimeLastDayOfWeek(1);
        assertThat(localDateTime.getDayOfMonth()).isEqualTo(6);
        assertThat(localDateTime.getMonth().getValue()).isEqualTo(1);
        assertThat(localDateTime.getHour()).isEqualTo(23);
        assertThat(localDateTime.getMinute()).isEqualTo(59);
    }

    @Test
    void t5(){
        TimeQuery timeQuery = TimeQuery.of(2024, 1);
        LocalDateTime localDateTime = timeQuery.thisLocalDateTimeLastDayOfWeek(2);
        assertThat(localDateTime.getDayOfMonth()).isEqualTo(13);
        assertThat(localDateTime.getHour()).isEqualTo(23);
        assertThat(localDateTime.getMinute()).isEqualTo(59);
    }

    @Test
    void t6(){
        TimeQuery timeQuery = TimeQuery.of(2023, 12);
        LocalDateTime localDateTime = timeQuery.thisLocalDateTimeLastDayOfWeek(2);
        assertThat(localDateTime.getDayOfMonth()).isEqualTo(9);
        assertThat(localDateTime.getHour()).isEqualTo(23);
        assertThat(localDateTime.getMinute()).isEqualTo(59);
    }

    @Test
    void t7(){
        TimeQuery timeQuery = TimeQuery.of(2023, 12);
        LocalDateTime localDateTime = timeQuery.thisLocalDateTimeFirstDayOfWeek(6);
        Work work = new Work(localDateTime, localDateTime);

        assertThat(localDateTime.getDayOfMonth()).isEqualTo(31);
        assertThat(work.getWeek()).isEqualTo(6);
    }

    @Test
    void t8(){
        TimeQuery timeQuery = TimeQuery.of(2023, 12);
        LocalDateTime localDateTime = timeQuery.thisLocalDateTimeLastDayOfWeek(6);
        Work work = new Work(localDateTime, localDateTime);

        assertThat(localDateTime.getDayOfMonth()).isEqualTo(31);
        assertThat(localDateTime.getHour()).isEqualTo(23);
        assertThat(localDateTime.getMinute()).isEqualTo(59);
        assertThat(work.getWeek()).isEqualTo(6);
    }

    @Test
    void t9(){
        for (int year = 2000; year <= 2100; year++) {
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day <= 31; day++) {
                    Work work;
                    try{
                        work = new Work(
                                LocalDateTime.of(year, month, day, 0, 0),
                                LocalDateTime.of(year, month, day, 5, 0)
                        );
                    } catch (Exception e) {
                        continue;
                    }

                    TimeQuery timeQuery = new TimeQuery(year, month);
                    LocalDateTime time1 = timeQuery.thisLocalDateTimeFirstDayOfWeek(work.getWeek());
                    LocalDateTime time2 = timeQuery.thisLocalDateTimeLastDayOfWeek(work.getWeek());

                    Work dummy1 = new Work(time1, time1);
                    Work dummy2 = new Work(time2, time2);

                    assertThat(work.getWeek()).isEqualTo(dummy1.getWeek());
                    assertThat(work.getWeek()).isEqualTo(dummy2.getWeek());
                }
            }
        }
    }



}