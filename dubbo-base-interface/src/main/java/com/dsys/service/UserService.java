package com.dsys.service;

public interface UserService {
    String register(String nickname,String password,String sex);
    String login(String account,String password);
}
