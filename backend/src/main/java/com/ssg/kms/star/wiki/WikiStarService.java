package com.ssg.kms.star.wiki;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.user.User;
import com.ssg.kms.wiki.Wiki;
import com.ssg.kms.wiki.WikiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiStarService {
    private final WikiStarRepository wikiStarRepository;
    private final WikiRepository wikiRepository;
    
    @Transactional
    public WikiStar checkWiki(Long wikiId, Optional<User> user) {
    	
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	
    	WikiStar wikiStar = wikiStarRepository.findByWikiAndUser(wiki, user.get()).orElse(null);
    	
    	if(wikiStar == null) {
    		WikiStar newWikiStar = WikiStar.builder()
        			.wiki(wiki)
        			.user(user.get())
        			.build();
    		
    		wikiStarRepository.save(newWikiStar);
    		wikiStar = newWikiStar;
    	} else {
    		wikiStarRepository.deleteById(wikiStar.getId());
    	}
    	    	
    	
    	
		return wikiStar;
    }
    
    @Transactional(readOnly = true)
    public int readStar(Long wikiId, Optional<User> user) {
    	return wikiStarRepository.findAllByWikiId(wikiId).size();
    }
}
