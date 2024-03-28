package com.ssg.kms.alarm.wiki;

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
public class TableWikiAlarmDTO {

	private String name;
    private String tableName;
    private String wikiTitle;
}
