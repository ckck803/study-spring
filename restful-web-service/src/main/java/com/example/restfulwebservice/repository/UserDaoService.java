package com.example.restfulwebservice.repository;

import com.example.restfulwebservice.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class UserDaoService {
    // 관계형 데이터베이스를 사용하지 않고 Memory 데이터를 사용
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static{
        users.add(new User(1, "Kenneth", new Date()));
        users.add(new User(2, "Alice", new Date()));
        users.add(new User(3, "Elena", new Date()));
    }

    // 전체 사용자 조회
    public List<User> findAll(){
        return users;
    }

    // 개별 사용자 조회
    public User save(User user){
        if(user.getId() == null){
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    // id값을 이용한 사용자 조회
    public User findOne(int id){
        for (User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }

    // id값을 이용해 해당 사용자를 삭제한다.
    public User deleteById(int id){
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext()){
            User user = iterator.next();

            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }

        return null;
    }
}
