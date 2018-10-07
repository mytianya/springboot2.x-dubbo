package com.dsys.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.dsys.dao.CardAreaDao;
import com.dsys.dao.UserAdminDao;
import com.dsys.exception.CommonException;
import com.dsys.service.TestService;
import com.dsys.utils.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service(
        version = "${test.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class TestServiceImpl implements TestService {
    @Autowired
    private CardAreaDao cardAreaDao;
    @Override
    public String test(String type) {
        Map<String,Object> result= cardAreaDao.findAreaByCardNo("340826");
        return JSONUtils.packageResult(true,result,"");
    }
}
