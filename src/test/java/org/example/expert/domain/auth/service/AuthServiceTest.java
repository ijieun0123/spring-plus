package org.example.expert.domain.auth.service;

import net.datafaker.Faker;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    UserRepository userRepository;

    Faker faker = new Faker(new Locale("en", "en"));

    @Test
    void 유저_100만_생성(){
        // 한번에 1만개 씩 저장 ( 메모리 부담 줄이기 )
        int batchSize = 10_000;

        List<User> userList = new ArrayList<>();
        Set<String> emailSet = new HashSet<>();

        for(long i=0; i<1_000_000; i++){
            String email = faker.internet().emailAddress();
            String password = faker.hashing().sha512();
            UserRole userRole = UserRole.USER;
            String nickname = faker.funnyName().name();

            // 중복 이메일 방지
            if(!emailSet.contains(email)){
                emailSet.add(email);
                userList.add(new User(email, password, userRole, nickname));
            }

            // 배치 저장 ( 1만 개씩 )
            if(userList.size() >= batchSize){
                userRepository.saveAll(userList);
                userList.clear();
            }
        }

        // 마지막으로 남은 데이터 저장
        if(!userList.isEmpty()){
            userRepository.saveAll(userList);
        }
    }
}