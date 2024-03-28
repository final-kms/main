package com.ssg.kms.alarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.alarm.post.PostAlarm;
import com.ssg.kms.alarm.post.PostAlarmRepository;
import com.ssg.kms.alarm.post.TablePostAlarmDTO;
import com.ssg.kms.alarm.reply.ReplyAlarm;
import com.ssg.kms.alarm.reply.ReplyAlarmDTO;
import com.ssg.kms.alarm.reply.ReplyAlarmRepository;
import com.ssg.kms.alarm.wiki.TableWikiAlarmDTO;
import com.ssg.kms.alarm.wiki.WikiAlarm;
import com.ssg.kms.alarm.wiki.WikiAlarmRepository;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
	
	private final PostAlarmRepository postAlarmRepository;
	private final WikiAlarmRepository wikiAlarmRepository;
	private final ReplyAlarmRepository replyAlarmRepository;
	    
    
    @Transactional(readOnly = true)
    public List<TablePostAlarmDTO> readPostAlarm(Optional<User> user) {
    	List<PostAlarm> postAlarms = postAlarmRepository.findAllByUserId(user.get().getId());
    	List<TablePostAlarmDTO> postAlarmDTOs = new ArrayList<>();
  
    	for(PostAlarm postAlarm : postAlarms) {
    		TablePostAlarmDTO postAlarmDTO = TablePostAlarmDTO.builder()
    				.name("post")
    				.tableName(postAlarm.getPost().getTable().getName())
    				.postTitle(postAlarm.getPost().getTitle())
    				.build();
    		
    		postAlarmDTOs.add(postAlarmDTO);
    	}
		return postAlarmDTOs;
    }

    @Transactional(readOnly = true)
    public List<TableWikiAlarmDTO> readWikiAlarm(Optional<User> user) {
    	List<WikiAlarm> wikiAlarms = wikiAlarmRepository.findAllByUserId(user.get().getId());
    	List<TableWikiAlarmDTO> wikiAlarmDTOs = new ArrayList<>();
    	  
    	for(WikiAlarm wikiAlarm : wikiAlarms) {
    		TableWikiAlarmDTO wikiAlarmDTO = TableWikiAlarmDTO.builder()
    				.name("wiki")
    				.tableName(wikiAlarm.getWiki().getTable().getName())
    				.wikiTitle(wikiAlarm.getWiki().getTitle())
    				.build();
    		
    		wikiAlarmDTOs.add(wikiAlarmDTO);
    	}
		return wikiAlarmDTOs;
    }

    @Transactional(readOnly = true)
    public List<ReplyAlarmDTO> readReplyAlarm(Optional<User> user) {
    	List<ReplyAlarm> replyAlarms = replyAlarmRepository.findAllByUserId(user.get().getId());
    	List<ReplyAlarmDTO> replyAlarmDTOs = new ArrayList<>();
    	  
    	for(ReplyAlarm replyAlarm : replyAlarms) {
    		ReplyAlarmDTO replyAlarmDTO = ReplyAlarmDTO.builder()
    				.name("reply")
    				.content(replyAlarm.getReply().getContent())
    				.postTitle(replyAlarm.getReply().getPost().getTitle())
    				.build();
    		
    		replyAlarmDTOs.add(replyAlarmDTO);
    	}
		return replyAlarmDTOs;
    }

}
