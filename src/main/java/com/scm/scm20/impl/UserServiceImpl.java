package com.scm.scm20.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.User;
import com.scm.scm20.services.EmaiService;
import com.scm.scm20.services.UserService;
import com.scm.scm20.repositories.UserRepo;
import com.scm.scm20.helper.AppConstants;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.helper.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmaiService emailService;

    @Autowired
    private Helper helper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        // user id : have to generate dynamically\
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);

        // password encode
        // user.setPassword(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // set the user role  
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        logger.info(user.getProvider().toString());
        
        
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser =  userRepo.save(user);
        
        String emailLink = helper.getLinkForEmailVerificatiton(emailToken);
        
        emailService.sendEmail(savedUser.getEmail(),"Verify Account Link : Connectify ",emailLink);
        return savedUser;
    }   

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not found"));
        userRepo.delete(user2);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public Optional<User> updatUser(User user) {
        User user2 = userRepo.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user not found"));
        // updating user2 from user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setAbout(user.getAbout());
        user2.setPassword(user.getPassword());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.getEnabled());
        user2.setEmailVerified(user.getEmailVerified());
        user2.setPhoneVerified(user.getPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        // save user to the database
        User save = userRepo.save(user2);

        return Optional.ofNullable(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

}
