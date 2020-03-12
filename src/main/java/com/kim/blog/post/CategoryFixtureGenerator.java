package com.kim.blog.post;

import com.kim.blog.category.Category;
import com.kim.blog.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryFixtureGenerator {
    private final CategoryRepository categoryRepository;

    public void generateCategories(){
        String category1 = "develop";
        String category2 = "picture";
        if(!categoryRepository.findByCategoryName(category1).isPresent()){
            Category develop = Category.builder().categoryName(category1).build();
            categoryRepository.save(develop);
        }
        if(!categoryRepository.findByCategoryName(category2).isPresent()){
            Category picture = Category.builder().categoryName(category2).build();
            categoryRepository.save(picture);
        }
    }



}
