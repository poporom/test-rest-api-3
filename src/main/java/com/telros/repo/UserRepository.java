package com.telros.repo;

import com.telros.entity.User;
import com.telros.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername( String username );

//    User findByImageId(Long imageId);

//    User findByUserInfoId(Long userInfoId);
//
//    User findByUserInfo(UserInfo userInfo);
}
