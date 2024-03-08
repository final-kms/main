package com.ssg.kms.wiki;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.like.wiki.WikiLikeRepository;
import com.ssg.kms.log.WikiLogRepository;
import com.ssg.kms.log.WikiLogService;
import com.ssg.kms.star.wiki.WikiStarRepository;
import com.ssg.kms.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiService {
    private final WikiRepository wikiRepository;
    private final WikiLikeRepository wikiLikeRepository;
    private final WikiStarRepository wikiStarRepository;
    private final WikiLogRepository wikiLogRepository;
    
    private final WikiLogService wikiLogService;
    
    @Transactional
    public Wiki createWiki(WikiDTO wikiDto, Optional<User> user) {
    	Wiki wiki = Wiki.builder()
    			.title(wikiDto.getTitle())
    			.content(wikiDto.getContent())
    			.tag(wikiDto.getTag())
    			.date(new Date())
    			.user(user.get())
    			.build();
    	
    	wikiRepository.save(wiki);
    	wikiLogService.createWikiLog(wiki, user);
    	
		return wiki;
    }

    @Transactional(readOnly = true)
    public List<Wiki> readWikiAll(Optional<User> user) {
		return wikiRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Wiki readWiki(Long wikiId, Optional<User> user) {
		return wikiRepository.findById(wikiId).get();
    }
    
    @Transactional
    public Wiki updateWiki(Long wikiId, WikiDTO wikiDto, Optional<User> user) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	
    	wiki.setTitle(wikiDto.getTitle());
    	wiki.setContent(wikiDto.getContent());
    	wiki.setTag(wikiDto.getTag());
    	wiki.setDate(new Date());
    	
    	wikiRepository.save(wiki);
    	wikiLogService.createWikiLog(wiki, user);
    	
		return wiki;
    }

    @Transactional
    public Wiki deleteWiki(Long wikiId, Optional<User> user) {
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	
    	wikiLikeRepository.deleteAllByWikiId(wiki.getId());
    	wikiStarRepository.deleteAllByWikiId(wiki.getId());
    	wikiLogRepository.deleteAllByWikiId(wiki.getId());

    	wiki.setUser(null);
    	wikiRepository.deleteById(wikiId);
    	wiki.setUser(user.get());
    	return wiki;
    }
}
