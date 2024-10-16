package org.mydemo.service;

import org.mydemo.dto.UserCreateDTO;
import org.mydemo.dto.UserEmailPwdDTO;
import org.mydemo.dto.UserIdentityDTO;
import org.mydemo.dto.UserUsernamePwdDTO;
import org.mydemo.entity.UserEntity;
import org.mydemo.mapper.UserMapper;
import org.mydemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    @Override
    public UserEntity getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserEntity> getByAge(int age) {
        return userRepository.findByAge(age);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserIdentityDTO getIdentityInfoByUsername(UserUsernamePwdDTO userUsernamePwdDTO) {
        UserEntity userEntity = userRepository.findByUsernameAndPassword(userUsernamePwdDTO.getDtoUsername(), userUsernamePwdDTO.getDtoPassword());
        return userMapper.toUserIdentityDTO(userEntity);
    }

    public UserIdentityDTO getIdentityInfoByEmail(UserEmailPwdDTO userEmailPwdDTO) {
        UserEntity userEntity = userRepository.findByEmailAndPassword(userEmailPwdDTO.getDtoEmail(), userEmailPwdDTO.getDtoPassword());
        return userMapper.toUserIdentityDTO(userEntity);
    }

    @Override
    public UserIdentityDTO register(UserCreateDTO userCreateDTO) {
        UserEntity userEntity = userMapper.toUserEntity(userCreateDTO);
        System.out.println(userEntity.toString());
        userRepository.save(userEntity);
        return userMapper.toUserIdentityDTO(userEntity);
    }
}
