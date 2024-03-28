package com.ssg.kms.like.wiki;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.mapping.GetWikiMapping;
import com.ssg.kms.user.User;
import com.ssg.kms.wiki.Wiki;
import com.ssg.kms.wiki.WikiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiLikeService {
    private final WikiLikeRepository wikiLikeRepository;
    private final WikiRepository wikiRepository;
    
    @Transactional
    public WikiLike checkwiki(Long wikiId, Optional<User> user) {
    	
    	Wiki wiki = wikiRepository.findById(wikiId).get();
    	
    	WikiLike wikiLike = wikiLikeRepository.findByWikiAndUser(wiki, user.get()).orElse(null);
    	
    	if(wikiLike == null) {
    		WikiLike newWikiLike = WikiLike.builder()
        			.wiki(wiki)
        			.user(user.get())
        			.build();
    		
    		wikiLikeRepository.save(newWikiLike);
    		wikiLike = newWikiLike;
    	} else {
    		wikiLikeRepository.deleteById(wikiLike.getId());
    	}
    	    	
    	
    	
		return wikiLike;
    }
    
    @Transactional(readOnly = true)
    public int readLike(Long wikiId, Optional<User> user) {
    	return wikiLikeRepository.findAllByWikiId(wikiId).size();
    }
    
    @Transactional(readOnly = true)
    public List<GetWikiMapping> readMyLike(Optional<User> user) {
    	return wikiLikeRepository.findWikiAllByUserId(user.get().getId());
    }
    
    ////////////////////////////
    
	@Transactional(readOnly = true)
    public WikiLike readLikePersonal(Long wikiId, Optional<User> user) {
		Wiki wiki = wikiRepository.findById(wikiId).get();
    	WikiLike wikiLike = wikiLikeRepository.findByWikiAndUser(wiki, user.get()).orElse(null);
		
		if(wikiLike != null) {
    		return wikiLike;
    	}
    	    	
		return null;
    }
}
