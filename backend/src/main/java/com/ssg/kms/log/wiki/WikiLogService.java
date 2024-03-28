package com.ssg.kms.log.wiki;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.user.User;
import com.ssg.kms.wiki.Wiki;
import com.ssg.kms.wiki.WikiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiLogService {
	private final WikiRepository wikiRepository;
    private final WikiLogRepository wikiLogRepository;
    
    @Transactional
    public WikiLog createWikiLog(Wiki wiki, Optional<User> user) {
    	WikiLog wikiLog = WikiLog.builder()
    			.title(wiki.getTitle())
    			.content(wiki.getContent())
    			.date(new Date())
    			.wiki(wiki)
    			.build();
    	
		return wikiLogRepository.save(wikiLog);
    }
    
    @Transactional(readOnly = true)
    public WikiLog readWikiLog(Long wikiLogId, Optional<User> user) {
		return wikiLogRepository.findById(wikiLogId).get();
    }
    
    @Transactional(readOnly = true)
    public Set<WikiLog> readWikiLogByWikiId(Long wikiId, Optional<User> user) {
		return wikiLogRepository.findAllByWikiId(wikiId);
    }


    @Transactional
    public WikiLog deleteWikiLog(Long wikiLogId, Optional<User> user) {    	
    	WikiLog wikiLog = wikiLogRepository.findById(wikiLogId).get();
    	
    	wikiLog.setWiki(null);
    	wikiLogRepository.deleteById(wikiLogId);
    	return wikiLog;
    }    
}
