package org.frank.spring.security.authority.service;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloService {

    /**
     * filterObject refers to the current object in the collection. When a map is used, 
     * it refers to the current Map.Entry object, which lets you use filterObject.key or filterObject.value in the expression. 
     * 
     * */
    @PostFilter("filterObject.lastIndexOf('2')!=-1")
    public List<String> getAllUser() {
        List<String> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add("javaboy:" + i);
        }
        return users;
    }
}
