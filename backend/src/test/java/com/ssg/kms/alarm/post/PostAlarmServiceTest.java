package com.ssg.kms.alarm.post;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static com.ssg.kms.alarm.post.PostAlarmService.isNotSameId;

class PostAlarmServiceTest {

  @Test
  void isNotSameId127BelowTest() {
    // 자기가 작성하지 않은 글에 대해서만 알람을 받고 싶은 경우
    // 아이디를 비교하는 로직에 대한 단위 테스트
    assertFalse(isNotSameId(-128L, -128L));
    assertFalse(isNotSameId(-1L, -1L));
    assertFalse(isNotSameId(0L, 0L));
    assertFalse(isNotSameId(1L, 1L));
    assertFalse(isNotSameId(127L, 127L));
  }

  @Test
  void isNotSameId128HigherTest() {
    // 자바에서는 -128에서 127까지의 범위의 경우 상수 풀로 캐싱 유지
    // 상수풀안에서는 == 을 통해 비교하더라도 정상적으로 동작
    // 다만 더 큰 값으로 올라가는 경우 예상처럼 동작 하지 않음
    assertFalse(isNotSameId(-129L, -129L));
    assertFalse(isNotSameId(128L, 128L));
  }
}