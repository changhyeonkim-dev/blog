package com.kim.blog.post;

import com.kim.blog.BaseAuditor;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseAuditor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false)
    @Length(min = 2, max = 50)
    private String categoryName;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "category")
    private List<Post> post = new ArrayList<>();

}
