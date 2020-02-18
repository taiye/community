package com.example.song.controller;

import com.example.song.dataTransferO.AccessTokenDto;
import com.example.song.dataTransferO.GithubUser;
import com.example.song.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizedController {
    @Autowired
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state
                           ){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("64ae9094e04fac20216a");
        accessTokenDto.setClient_secret("64b8b573d6f7cdcc2ef1db4e40463e96627f163a");
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri("http://localhost:8080/callback");
        accessTokenDto.setState(state);
        String accessToken=githubProvider.getAccessToken(accessTokenDto);
        GithubUser githubUser=githubProvider.getGithubUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
