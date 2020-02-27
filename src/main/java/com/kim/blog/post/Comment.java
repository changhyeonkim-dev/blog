package com.kim.blog.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kim.blog.BaseAuditor;
import com.kim.blog.account.Account;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseAuditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false)
    @Length(min = 2, max = 500)
    private String commentContents;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    private int up;

    private int down;


}
