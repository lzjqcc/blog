package com.lzj.security;

import com.lzj.dao.dto.AccountDto;
import com.lzj.domain.Account;
import com.lzj.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

public class CustomUserDetailsService implements UserDetailsService {
        @Autowired
        private AccountService accountService;
        public Account findAccount(String username) {
            AccountDto dto = new AccountDto();
            dto.setEmail(username);
            return  accountService.findByDto(dto);

        }
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Account account = findAccount(username);
            AccountDetails accountDetails = new AccountDetails();
            accountDetails.setAccount(account);
            //AccountDetails accountDetails = null;
            return accountDetails;
        }
}
