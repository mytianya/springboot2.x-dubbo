package com.dsys.service;

public interface UserService {
    String register(String nickname,String password);
    String login(String account,String password);
}
