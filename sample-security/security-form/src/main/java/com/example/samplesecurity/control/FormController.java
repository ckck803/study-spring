package com.example.samplesecurity.control;

import com.example.samplesecurity.control.dto.AccountDto;
import com.example.samplesecurity.domain.Account;
import com.example.samplesecurity.domain.enums.Role;
import com.example.samplesecurity.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FormController {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public void logout(){
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("signupDto") AccountDto accountDto, Model model){
        return "signup";
    }

    @PostMapping("/signup")
    public String createNewAccount(@ModelAttribute("signupDto") @Validated AccountDto accountDto, Model model){
        Account account = Account.builder()
                .username(accountDto.getUsername())
                .email(accountDto.getEmail())
                .password(passwordEncoder.encode(accountDto.getPassword()))
                .role(Role.USER)
                .build();
        customUserDetailsService.saveAccount(account);

        return "redirect:";
    }
}
