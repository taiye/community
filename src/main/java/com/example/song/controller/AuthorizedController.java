package com.example.song.controller;

import com.example.song.dataTransferO.AccessTokenDto;
import com.example.song.dataTransferO.GithubUser;
import com.example.song.mapper.UserMapper;
import com.example.song.model.User;
import com.example.song.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizedController {//Authorize是github的一个接口用来拿accessToken，accessToken再去拿user
    @Autowired
    private GithubProvider githubProvider;
    @Autowired(required = false)
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse httpServletResponse
                           ){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);
        String accessToken=githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser=githubProvider.getGithubUser(accessToken);
        //System.out.println(githubUser.getName());
        if(githubUser!=null){
            User user=new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);
            httpServletResponse.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            return "redirect:/";
        }

    }
}
