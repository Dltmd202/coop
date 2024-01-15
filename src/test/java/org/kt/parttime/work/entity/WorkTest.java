package org.kt.parttime.work.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class WorkTest {
    @Test
    void 시간_계산_테스트1(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 17, 30);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 4, 18, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();

        // then
        assertThat(workTime).isEqualTo(0.5F);
    }

    @Test
    void 시간_계산_테스트2(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 18, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 4, 18, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();

        // then
        assertThat(workTime).isEqualTo(0);
    }

    @Test
    void 시간_계산_테스트3(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 18, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 4, 18, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();

        // then
        assertThat(workTime).isEqualTo(0);
    }

    @Test
    void 시간_계산_테스트4(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 22, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 5, 6, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();
        Float dayWorkTime = work.getDayWorkTime();
        Float nightWorkTime = work.getNightWorkTime();

        // then
        assertThat(workTime).isEqualTo(8);
        assertThat(nightWorkTime).isEqualTo(8);
        assertThat(dayWorkTime).isEqualTo(0);
    }

    @Test
    void 시간_계산_테스트5(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 21, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 5, 6, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();
        Float dayWorkTime = work.getDayWorkTime();
        Float nightWorkTime = work.getNightWorkTime();

        // then
        assertThat(workTime).isEqualTo(9);
        assertThat(dayWorkTime).isEqualTo(1);
        assertThat(nightWorkTime).isEqualTo(8);
    }


    @Test
    void 시간_계산_테스트6(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 21, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 5, 6, 30);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();
        Float dayWorkTime = work.getDayWorkTime();
        Float nightWorkTime = work.getNightWorkTime();

        // then
        assertThat(workTime).isEqualTo(9.5F);
        assertThat(nightWorkTime).isEqualTo(8);
        assertThat(dayWorkTime).isEqualTo(1.5F);
    }

    @Test
    void 시간_계산_테스트7(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 21, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 4, 23, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();
        Float dayWorkTime = work.getDayWorkTime();
        Float nightWorkTime = work.getNightWorkTime();

        // then
        assertThat(workTime).isEqualTo(2);
        assertThat(dayWorkTime).isEqualTo(1);
        assertThat(nightWorkTime).isEqualTo(1);
    }

    @Test
    void 시간_계산_테스트8(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 22, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 4, 22, 30);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();
        Float dayWorkTime = work.getDayWorkTime();
        Float nightWorkTime = work.getNightWorkTime();

        // then
        assertThat(workTime).isEqualTo(0.5F);
        assertThat(dayWorkTime).isEqualTo(0);
        assertThat(nightWorkTime).isEqualTo(0.5F);
    }

    @Test
    void 시간_계산_테스트9(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 8, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 4, 20, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();
        Float dayWorkTime = work.getDayWorkTime();
        Float nightWorkTime = work.getNightWorkTime();

        // then
        assertThat(workTime).isEqualTo(12);
        assertThat(dayWorkTime).isEqualTo(12);
        assertThat(nightWorkTime).isEqualTo(0);
    }

    @Test
    void 시간_계산_테스트10(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 6, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 4, 22, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();
        Float dayWorkTime = work.getDayWorkTime();
        Float nightWorkTime = work.getNightWorkTime();

        // then
        assertThat(workTime).isEqualTo(16);
        assertThat(dayWorkTime).isEqualTo(16);
        assertThat(nightWorkTime).isEqualTo(0);
    }

    @Test
    void 시간_계산_테스트11(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 4, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 4, 22, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();
        Float dayWorkTime = work.getDayWorkTime();
        Float nightWorkTime = work.getNightWorkTime();

        // then
        assertThat(workTime).isEqualTo(18);
        assertThat(nightWorkTime).isEqualTo(2);
        assertThat(dayWorkTime).isEqualTo(16);
    }

    @Test
    void 시간_계산_테스트12(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 4, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 6, 0, 0);
        Work work = new Work(startTime, endTime);

        // when
        Float workTime = work.getWorkTime();
        Float dayWorkTime = work.getDayWorkTime();
        Float nightWorkTime = work.getNightWorkTime();

        // then
        assertThat(workTime).isEqualTo(48);
        assertThat(nightWorkTime).isEqualTo(16);
        assertThat(dayWorkTime).isEqualTo(32);
    }

    @Test
    void 필드_초기화_테스트1(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        Work work = new Work(startTime, endTime);

        // when
        Integer year = work.getYear();
        Integer month = work.getMonth();
        Integer week = work.getWeek();

        // then
        assertThat(year).isEqualTo(2024);
        assertThat(month).isEqualTo(1);
        assertThat(week).isEqualTo(1);
    }

    @Test
    void 필드_초기화_테스트2(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2023, 12, 31, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 12, 31, 0, 0);
        Work work = new Work(startTime, endTime);

        // when
        Integer year = work.getYear();
        Integer month = work.getMonth();
        Integer week = work.getWeek();

        // then
        assertThat(year).isEqualTo(2023);
        assertThat(month).isEqualTo(12);
        assertThat(week).isEqualTo(6);
    }

    @Test
    void 필드_초기화_테스트3(){
        // given
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        Work work = new Work(startTime, endTime);

        // when
        Integer year = work.getYear();
        Integer month = work.getMonth();
        Integer week = work.getWeek();

        // then
        assertThat(year).isEqualTo(2024);
        assertThat(month).isEqualTo(1);
        assertThat(week).isEqualTo(1);
    }

}