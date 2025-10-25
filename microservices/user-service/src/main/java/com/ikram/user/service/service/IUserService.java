package com.ikram.user.service.service;

import com.ikram.user.service.exception.UserException;
import com.ikram.user.service.model.User;
import com.ikram.user.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IUserService implements UserService{

    private final UserRepository userRepository;
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("User not found with id "+id);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUserById(Long id) throws UserException{
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()){
            userRepository.deleteById(id);
            return "User deleted!";
        }
        else {
            throw new UserException("User not found with id "+id);
        }
    }

    @Override
    public User updateUser(User user, Long id) throws UserException {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()){
            User existingUser = opt.get();
            existingUser.setFullName(user.getFullName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhone(user.getPhone());
            existingUser.setRole(user.getRole());
            existingUser.setCreatedAt(user.getCreatedAt());
            existingUser.setUpdatedAt(user.getUpdatedAt());
            existingUser.setUsername(user.getUsername());
            return userRepository.save(existingUser);
        }
        throw new UserException("User not found with id "+id);
    }

}
