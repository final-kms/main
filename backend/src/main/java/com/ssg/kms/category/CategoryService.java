package com.ssg.kms.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssg.kms.mapping.GetPostMapping;
import com.ssg.kms.user.User;
import com.ssg.kms.wiki.Wiki;
import com.ssg.kms.wiki.WikiRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final WikiRepository wikiRepository;
    
    
    @Transactional(readOnly = true)
    public List<Wiki> readWikiByCategory(String categoryName, Optional<User> user) {
    	
    	return wikiRepository.findAllByCategoryName(categoryName);
    }
    
}
