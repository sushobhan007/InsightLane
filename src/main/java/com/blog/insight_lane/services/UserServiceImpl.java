package com.blog.insight_lane.services;

import com.blog.insight_lane.entities.User;
import com.blog.insight_lane.exceptions.DuplicateResourceException;
import com.blog.insight_lane.exceptions.ResourceNotFoundException;
import com.blog.insight_lane.payloads.UserDto;
import com.blog.insight_lane.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        String email = userDto.getEmail();
        this.userRepository
                .findByEmail(email)
                .ifPresent(existingUser -> {
                    System.out.println("User is already present with the email: " + email);
                    throw new DuplicateResourceException("User", " Email ", email);
                });
        User savedUser = this.userRepository.save(getUserFromUserDto(userDto));

        UserDto savedUserDto = this.getUserDtoFromUser(savedUser);
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
        return this.getUserDtoFromUser(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        return this.getUserDtoFromUser(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> userList = this.userRepository.findAll();
        return userList.stream().map(user -> this.getUserDtoFromUser(user)).toList();
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        this.userRepository.delete(user);
        System.out.println("User is deleted for the userId: %s" + userId);
    }

    private User getUserFromUserDto(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        return user;
    }

    private UserDto getUserDtoFromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());

        return userDto;
    }
}
