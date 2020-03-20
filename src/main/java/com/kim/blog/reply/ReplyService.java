package com.kim.blog.reply;

import com.kim.blog.account.Account;
import com.kim.blog.comment.Comment;
import com.kim.blog.comment.repository.CommentRepository;
import com.kim.blog.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final CommentRepository commentRepository;

    public void postReply(Post post, Account account, String contents) {
        commentRepository.save(Comment.builder()
                .commentContents(contents)
                .account(account)
                .post(post)
                .build());
    }
}
