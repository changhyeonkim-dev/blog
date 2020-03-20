package com.kim.blog;

import com.kim.blog.account.Account;
import com.kim.blog.account.repository.AccountRepository;
import com.kim.blog.account.request.SaveAccountRequest;
import com.kim.blog.config.JwtTokenProvider;
import com.kim.blog.post.repository.PostRepository;
import com.kim.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BlogController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository accountRepository;
    private final PostService postService;
    private final PostRepository postRepository;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/posts/{id}")
    public String postDetail(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response){
        String cookieName = "readPostId" + id;
        Cookie[] cookies = request.getCookies();
        long count =0L;
        if(cookies!=null){
            count = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(cookieName)).count();
        }
        if (count == 0L) {
            Cookie cookie = new Cookie(cookieName, String.valueOf(true));
            cookie.setMaxAge(60*60);//1시간
            response.addCookie(cookie);
            postService.increaseViews(postRepository.findById(id).orElseThrow(EntityNotFoundException::new));
        }
        model.addAttribute("postId",id);
        return "detail";
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestParam String username, HttpServletResponse response) {
        Account account = accountRepository.findByUserId(username).orElseThrow(EntityNotFoundException::new);
        String token = jwtTokenProvider.createToken(String.valueOf(account.getUserId()),
                account.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
        Cookie setCookie = new Cookie("X-AUTH-TOKEN", token); // 쿠키 이름을 name으로 생성
        setCookie.setMaxAge(60*60*24); // 기간을 하루로 지정
        response.addCookie(setCookie);
        return "redirect:/";
    }

    @GetMapping("/detail")
    public String detail() {
        return "detail";
    }

    @GetMapping("/write-form")
    public String writeForm() {
        return "writeForm";
    }

    @GetMapping("/map/masks")
    public String masks(){
        return "masks";
    }

    @GetMapping("/join")
    public String join(Model model){
        model.addAttribute("saveAccountRequest",new SaveAccountRequest());
        return "join";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
