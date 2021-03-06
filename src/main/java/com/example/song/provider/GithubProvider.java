package com.example.song.provider;

import com.alibaba.fastjson.JSON;
import com.example.song.DTO.AccessTokenDto;
import com.example.song.DTO.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GithubProvider {
   public String getAccessToken(AccessTokenDto accessTokenDto){
       MediaType mediaType = MediaType.get("application/json; charset=utf-8");
       OkHttpClient client = new OkHttpClient();
       RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDto),mediaType);
       Request request = new Request.Builder()
               .url("https://github.com/login/oauth/access_token")
               .post(body)
               .build();
       try (Response response = client.newCall(request).execute()) {
           String string = response.body().string();
           String token= string.split("&")[0].split("=")[1];//cuz access_token=#####&scope=...
           return token;
       } catch (Exception e) {
           e.printStackTrace();
       }
       return  null;
   }

   public GithubUser getGithubUser(String accessToken){
       OkHttpClient client = new OkHttpClient();
       Request request = new Request.Builder()
               .url("https://api.github.com/user?access_token="+accessToken)
               .build();
       try {
           Response response = client.newCall(request).execute();
           String string=response.body().string();
           GithubUser githubUser= JSON.parseObject(string,GithubUser.class);
            return githubUser;
       } catch (IOException e) {
       }

       return null;
   }
}
