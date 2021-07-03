package ru.kurbanmagomedov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kurbanmagomedov.models.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

//    void saveUser(User user);
//
//    User getUserById(Long id);
//
//    void setUser(User user);
//
//    void removeUser(Long id);
//
//    List<User> getAllUsers();
//
//    User loadUserByUsername(String s) throws UsernameNotFoundException;
}
