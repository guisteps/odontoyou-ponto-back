package com.odontoyou.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.odontoyou.model.Users;
import com.odontoyou.repo.UsersRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UsersRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		Optional<Users> user = repo.findById(cpf);
		User userSpring = new User(user.get().getCpf(), user.get().getSenha(), new ArrayList<>());
		return userSpring;
	}

}
