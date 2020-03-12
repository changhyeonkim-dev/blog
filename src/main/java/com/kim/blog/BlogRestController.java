package com.kim.blog;

import com.kim.blog.account.Account;
import com.kim.blog.post.Post;
import com.kim.blog.category.repository.CategoryRepository;
import com.kim.blog.post.repository.PostRepository;
import com.kim.blog.post.request.SavePostRequest;
import com.kim.blog.post.response.PostResponse;
import com.kim.blog.post.service.PostService;
import com.kim.blog.util.FileUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class BlogRestController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final FileUploader fileUploader;


    @GetMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse postDetails(@PathVariable Long id) {
        return postRepository.getOneById(id).orElseThrow(IllegalArgumentException::new);
    }

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getPosts(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        return postRepository.getAllBy(PageRequest.of(page, size));
    }

    @GetMapping("/posts/categories/{categoryName}")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getPostsInCategory(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                  @RequestParam(name = "size", required = false, defaultValue = "5") int size,
                                                  @PathVariable("categoryName") String categoryName){

        return postRepository.getAllByCategoryCategoryName(categoryName,PageRequest.of(page,size));

    }

    @GetMapping("/posts/popular-list")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> getPopularList() {
        return postRepository.getTop3ByOrderByViewsCountDesc();
    }


    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public void posts(@RequestBody SavePostRequest savePostRequest) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = Post.builder()
                .title(savePostRequest.getTitle())
                .account(account)
                .contents(savePostRequest.getContents())
                .previewImagePath(savePostRequest.getPreviewImagePath())
                .category(categoryRepository.findByCategoryName(savePostRequest.getCategory()).orElseThrow(IllegalArgumentException::new))
                .build();
        postService.savePost(post);
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return fileUploader.uploadMultipartToFile(multipartFile);
    }

}
