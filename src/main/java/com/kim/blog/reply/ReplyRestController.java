package com.kim.blog.reply;

import com.kim.blog.account.Account;
import com.kim.blog.exception.AuthorizationException;
import com.kim.blog.exception.EntityNotFoundException;
import com.kim.blog.notification.NotificationInterface;
import com.kim.blog.post.Post;
import com.kim.blog.post.repository.PostRepository;
import com.kim.blog.reply.request.SaveReplyRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ReplyRestController {

    private final ReplyService replyService;

    private final PostRepository postRepository;

    private final NotificationInterface notificationService;

    @PostMapping("/posts/{postId}/reply")
    @ResponseStatus(HttpStatus.CREATED)
    public void postReply(@RequestBody @Valid SaveReplyRequest saveReplyRequest,@PathVariable("postId")Long postId){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!(principal instanceof Account))
            throw new AuthorizationException("권한이 없는 유저");
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        replyService.postReply(post,(Account)principal,saveReplyRequest.getContents());
        notificationService.notification("kimchanghyeon1201@gmail.com",post.getTitle()+"에 댓글이 달렸습니다",saveReplyRequest.getContents());
    }

}
