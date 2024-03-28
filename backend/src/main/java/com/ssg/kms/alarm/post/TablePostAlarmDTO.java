package com.ssg.kms.alarm.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TablePostAlarmDTO {

	private String name;
    private String tableName;
    private String postTitle;
}
