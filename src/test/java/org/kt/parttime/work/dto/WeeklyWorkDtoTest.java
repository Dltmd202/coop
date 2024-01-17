package org.kt.parttime.work.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class WeeklyWorkDtoTest {
    @Test
    void t1(){
        Assertions.assertThat(((int) Math.max(1, Math.floor((double) 6 * 0.75)))).isEqualTo(4);
        Assertions.assertThat(((int) Math.max(1, Math.floor((double) 5 * 0.75)))).isEqualTo(3);
        Assertions.assertThat(((int) Math.max(1, Math.floor((double) 4 * 0.75)))).isEqualTo(3);
        Assertions.assertThat(((int) Math.max(1, Math.floor((double) 3 * 0.75)))).isEqualTo(2);
        Assertions.assertThat(((int) Math.max(1, Math.floor((double) 2 * 0.75)))).isEqualTo(1);
        Assertions.assertThat(((int) Math.max(1, Math.floor((double) 1 * 0.75)))).isEqualTo(1);
    }
}