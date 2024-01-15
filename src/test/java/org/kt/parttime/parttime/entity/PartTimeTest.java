package org.kt.parttime.parttime.entity;

import org.junit.jupiter.api.Test;
import org.kt.parttime.work.entity.Work;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


class PartTimeTest {
    @Test
    void 일간_임금계산_테스트1(){
        // given
        Work work = new Work(
                LocalDateTime.of(2023, 12, 1, 16, 0),
                LocalDateTime.of(2023, 12, 1, 19, 0)
        );

        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);

        // when
        int wage = time.calculateDailyWage(work);

        // then
        // (9620원) * (3시간)
        assertThat(wage).isEqualTo(28_860);
    }

    @Test
    void 일간_임금계산_테스트2(){
        // given
        Work work = new Work(
                LocalDateTime.of(2023, 12, 1, 16, 0),
                LocalDateTime.of(2023, 12, 1, 23, 0)
        );

        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);

        // when
        int wage = time.calculateDailyWage(work);

        // then
        // (9620원) * (7시간)         - 정규업무
        // (9620원 / 2) * (1시간)     - 야간근무
        assertThat(wage).isEqualTo(72_150);
    }

    @Test
    void 일간_임금계산_테스트3(){
        // given
        Work work = new Work(
                LocalDateTime.of(2023, 12, 1, 6, 0),
                LocalDateTime.of(2023, 12, 1, 22, 0)
        );

        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);

        // when
        int wage = time.calculateDailyWage(work);

        // then
        // (9620원) * (16시간)         - 정규업무
        // (9620원 / 2) * (8시간)      - 초과근무
        assertThat(wage).isEqualTo(192_400);
    }

    @Test
    void 일간_임금계산_테스트4(){
        // given
        Work work = new Work(
                LocalDateTime.of(2023, 12, 1, 4, 0),
                LocalDateTime.of(2023, 12, 1, 22, 0)
        );

        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);

        // when
        int wage = time.calculateDailyWage(work);

        // then
        // (9620원) * (18시간)         - 정규업무
        // (9620원 / 2) * (10시간)     - 초과근무
        // (9620원 / 2) * (2시간)      - 야간근무
        assertThat(wage).isEqualTo(230_880);
    }

    @Test
    void 일간_임금계산_테스트5(){
        // given
        Work work = new Work(
                LocalDateTime.of(2023, 12, 1, 22, 0),
                LocalDateTime.of(2023, 12, 1, 23, 30)
        );

        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);

        // when
        int wage = time.calculateDailyWage(work);

        // then
        // (9620원) * (1.5시간)         - 정규업무
        // (9620원 / 2) * (1.5시간)     - 야간근무
        assertThat(wage).isEqualTo(21_645);
    }

    @Test
    void 일간_임금계산_테스트6(){
        // given
        Work work = new Work(
                LocalDateTime.of(2023, 12, 1, 0, 0),
                LocalDateTime.of(2023, 12, 1, 6, 0)
        );

        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);

        // when
        int wage = time.calculateDailyWage(work);

        // then
        // (9620원) * (6시간)         - 정규업무
        // (9620원 / 2) * (6시간)     - 야간근무
        assertThat(wage).isEqualTo(86_580);
    }

    @Test
    void 일간_임금계산_테스트7(){
        // given
        Work work = new Work(
                LocalDateTime.of(2023, 12, 1, 0, 0),
                LocalDateTime.of(2023, 12, 1, 7, 0)
        );

        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);

        // when
        int wage = time.calculateDailyWage(work);

        // then
        // (9620원) * (7시간)         - 주간근무
        // (9620원 / 2) * (6시간)     - 야간근무
        assertThat(wage).isEqualTo(96_200);
    }

    @Test
    void 일간_임금계산_테스트8(){
        // given
        Work work = new Work(
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 1, 10, 0)
        );

        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);

        // when
        int wage = time.calculateDailyWage(work);

        // then
        // (9620원) * (10시간)         - 주간근무
        // (9620원 / 2) * (6시간)      - 야간근무
        // (9620원 / 2) * (2시간)      - 초과근무
        assertThat(wage).isEqualTo(134_680);
    }


    @Test
    void 주간_임금계산_테스트1(){
        // given
        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);
        List<Work> works = List.of(
                new Work(
                        LocalDateTime.of(2023, 12, 1, 16, 0),
                        LocalDateTime.of(2023, 12, 1, 19, 0)
                )
        );

        // when
        int wage = time.calculateWeeklyWage(works);

        // then
        assertThat(wage).isEqualTo(28_860);
    }

    @Test
    void 주간_임금계산_테스트2(){
        // given
        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);
        List<Work> works = List.of(
                new Work(
                        LocalDateTime.of(2023, 12, 5, 16, 0),
                        LocalDateTime.of(2023, 12, 5, 18, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 8, 16, 0),
                        LocalDateTime.of(2023, 12, 8, 19, 0)
                )
        );

        // when
        Integer wage = time.calculateWeeklyWage(works);

        // then
        assertThat(wage).isEqualTo(48_100);
    }

    @Test
    void 주간_임금계산_테스트3(){
        // given
        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);
        List<Work> works = List.of(
                new Work(
                        LocalDateTime.of(2024, 1, 1, 9, 0),
                        LocalDateTime.of(2024, 1, 1, 17, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 2, 9, 0),
                        LocalDateTime.of(2024, 1, 2, 17, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 3, 9, 0),
                        LocalDateTime.of(2024, 1, 3, 17, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 4, 9, 0),
                        LocalDateTime.of(2024, 1, 4, 17, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 5, 9, 0),
                        LocalDateTime.of(2024, 1, 5, 17, 0)
                )

        );

        // when
        Integer wage = time.calculateWeeklyWage(works);

        // then
        // (9620원 * 8시간) * 5        - (월-금 정규임금)
        // (9620원 * (40 / 5)시간)     - 주휴수당
        assertThat(wage).isEqualTo(461_760);
    }


    @Test
    void 주간_임금계산_테스트4(){
        // given
        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);
        List<Work> works = List.of(
                new Work(
                        LocalDateTime.of(2024, 1, 1, 0, 0),
                        LocalDateTime.of(2024, 1, 1, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 2, 0, 0),
                        LocalDateTime.of(2024, 1, 2, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 3, 0, 0),
                        LocalDateTime.of(2024, 1, 3, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 4, 0, 0),
                        LocalDateTime.of(2024, 1, 4, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 5, 0, 0),
                        LocalDateTime.of(2024, 1, 5, 10, 0)
                )

        );

        // when
        Integer wage = time.calculateWeeklyWage(works);

        // then
        // ((9620원 * 10시간) * 5일)      - (월-금 정규임금)
        // ((9620 / 2)원 * 6 시간) * 5일)  - (월-금 야간수당)
        // ((9620 / 2)원 * 2시간) * 5일)  - (월-금 초과근무)
        // (9620원 * (50 / 5)시간)        - 주휴수당
        assertThat(wage).isEqualTo(769_600);
    }

    @Test
    void 주간_임금계산_테스트5(){
        // given
        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);
        List<Work> works = List.of(
                new Work(
                        LocalDateTime.of(2024, 1, 1, 0, 0),
                        LocalDateTime.of(2024, 1, 1, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 2, 0, 0),
                        LocalDateTime.of(2024, 1, 2, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 3, 0, 0),
                        LocalDateTime.of(2024, 1, 3, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 4, 0, 0),
                        LocalDateTime.of(2024, 1, 4, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 5, 0, 0),
                        LocalDateTime.of(2024, 1, 5, 10, 0)
                )

        );

        // when
        Integer wage = time.calculateWeeklyWage(works);
        Integer dailyWage = time.calculateDailyWage(works.get(0));

        // then
        // ((9620원 * 10시간) * 5일)       - (월-금 정규임금)
        // ((9620 / 2)원 * 6 시간) * 5일)  - (월-금 야간수당)
        // ((9620 / 2)원 * 2시간) * 5일)   - (월-금 초과근무)
        // (9620원 * (50 / 5)시간)        - 주휴수당
        assertThat(wage).isEqualTo(769_600);
    }

    @Test
    void 월급_계산_테스트1(){
        // given
        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);
        List<Work> works = List.of(
                new Work(
                        LocalDateTime.of(2024, 1, 1, 0, 0),
                        LocalDateTime.of(2024, 1, 1, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 8, 0, 0),
                        LocalDateTime.of(2024, 1, 8, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 15, 0, 0),
                        LocalDateTime.of(2024, 1, 15, 10, 0)
                ),
                new Work(
                        LocalDateTime.of(2024, 1, 22, 0, 0),
                        LocalDateTime.of(2024, 1, 22, 10, 0)
                )
        );

        // when
        Integer wage = time.calculateMonthlyWage(works);

        // then
        assertThat(wage).isEqualTo(538_720);
    }

    @Test
    void 월급_계산_테스트2(){
        // given
        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);
        List<Work> works = List.of(
            new Work(
                    LocalDateTime.of(2024, 1, 1, 9, 0),
                    LocalDateTime.of(2024, 1, 1, 17, 0)
            ),
            new Work(
                    LocalDateTime.of(2024, 1, 2, 9, 0),
                    LocalDateTime.of(2024, 1, 2, 17, 0)
            ),
            new Work(
                    LocalDateTime.of(2024, 1, 3, 9, 0),
                    LocalDateTime.of(2024, 1, 3, 17, 0)
            ),
            new Work(
                    LocalDateTime.of(2024, 1, 4, 9, 0),
                    LocalDateTime.of(2024, 1, 4, 17, 0)
            ),
            new Work(
                    LocalDateTime.of(2024, 1, 5, 9, 0),
                    LocalDateTime.of(2024, 1, 5, 17, 0)
            )
        );

        // when
        Integer wage = time.calculateMonthlyWage(works);

        // then
        assertThat(wage).isEqualTo(538_720);
    }

    @Test
    void 월급_계산_테스트3(){
        // given
        PartTime breakfast = new PartTime("식당(오전)", 2024, 1, 11_000);
        PartTime lunch = new PartTime("식당(오후)", 2024, 1, 15_000);
        PartTime dinner = new PartTime("식당(저녁)", 2024, 1, 11_000);

        List<Work> breakWork = List.of(
                new Work(
                        LocalDateTime.of(2023, 12, 1, 8, 0),
                        LocalDateTime.of(2023, 12, 1, 9, 30)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 7, 8, 0),
                        LocalDateTime.of(2023, 12, 7, 9, 30)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 8, 8, 0),
                        LocalDateTime.of(2023, 12, 8, 9, 30)
                )
        );

        List<Work> lunchWork = List.of(
                new Work(
                        LocalDateTime.of(2023, 12, 2, 11, 30),
                        LocalDateTime.of(2023, 12, 2, 14, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 3, 11, 30),
                        LocalDateTime.of(2023, 12, 3, 14, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 8, 11, 30),
                        LocalDateTime.of(2023, 12, 8, 14, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 9, 11, 30),
                        LocalDateTime.of(2023, 12, 9, 14, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 10, 11, 30),
                        LocalDateTime.of(2023, 12, 10, 14, 0)
                )
        );

        List<Work> dinnerWork = List.of(
                new Work(
                        LocalDateTime.of(2023, 12, 2, 17, 30),
                        LocalDateTime.of(2023, 12, 2, 19, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 3, 17, 30),
                        LocalDateTime.of(2023, 12, 3, 19, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 5, 17, 30),
                        LocalDateTime.of(2023, 12, 5, 19, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 6, 17, 30),
                        LocalDateTime.of(2023, 12, 6, 19, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 7, 17, 30),
                        LocalDateTime.of(2023, 12, 7, 19, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 9, 17, 30),
                        LocalDateTime.of(2023, 12, 9, 19, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 10, 17, 30),
                        LocalDateTime.of(2023, 12, 10, 19, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 11, 16, 00),
                        LocalDateTime.of(2023, 12, 11, 19, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 13, 17, 30),
                        LocalDateTime.of(2023, 12, 13, 19, 0)
                )
        );

        // when
        int breakFastWage = breakfast.calculateMonthlyWage(breakWork);
        int lunchWage = lunch.calculateMonthlyWage(lunchWork);
        int dinnerWage = dinner.calculateMonthlyWage(dinnerWork);
        int totalWage = breakFastWage + lunchWage + dinnerWage;

        // then
        assertThat(totalWage).isEqualTo(402_000);
    }

    @Test
    void 월급_계산_테스트4(){
        // given
        PartTime time = new PartTime("gs편의점", 2023, 2, 9620);
        List<Work> works = List.of(
                new Work(
                        LocalDateTime.of(2023, 12, 3, 16, 0),
                        LocalDateTime.of(2023, 12, 3, 23, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 9, 22, 30),
                        LocalDateTime.of(2023, 12, 10, 0, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 10, 0, 0),
                        LocalDateTime.of(2023, 12, 10, 2, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 10, 16, 0),
                        LocalDateTime.of(2023, 12, 11, 0, 0)
                ),
                new Work(
                        LocalDateTime.of(2023, 12, 11, 0, 0),
                        LocalDateTime.of(2023, 12, 11, 2, 0)
                )
        );

        // when
        Integer wage = time.calculateMonthlyWage(works);

        // then
        assertThat(wage).isEqualTo(238_095);
    }


}