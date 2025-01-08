package com.blog.insight_lane.services.impl;

import com.blog.insight_lane.entities.User;
import com.blog.insight_lane.exceptions.DuplicateResourceException;
import com.blog.insight_lane.exceptions.ResourceNotFoundException;
import com.blog.insight_lane.payloads.UserDto;
import com.blog.insight_lane.repositories.UserRepository;
import com.blog.insight_lane.services.UserService;
import com.blog.insight_lane.utils.ModelMapperUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapperUtility modelMapperUtility;

    @Override
    public UserDto createUser(UserDto userDto) {
        String email = userDto.getEmail();
        this.userRepository
                .findByEmail(email)
                .ifPresent(existingUser -> {
                    System.out.println("User is already present with the email: " + email);
                    throw new DuplicateResourceException("User", " Email ", email);
                });
        User savedUser = this.userRepository.save(modelMapperUtility.toUser(userDto));

        UserDto savedUserDto = modelMapperUtility.toUserDto(savedUser);
        System.out.println(String.format("New user is created. %nName: %s, " +
                "%nEmail: %s", savedUserDto.getId(), savedUserDto.getEmail()));
        return savedUserDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepository.save(user);

        System.out.println("User details are updated for the userId: %s" + userId);
        return modelMapperUtility.toUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        return modelMapperUtility.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = this.userRepository.findAll();
        return userList.stream().map(user -> modelMapperUtility.toUserDto(user)).toList();
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        this.userRepository.delete(user);
        System.out.println("User is deleted for the userId: %s" + userId);
    }
}
