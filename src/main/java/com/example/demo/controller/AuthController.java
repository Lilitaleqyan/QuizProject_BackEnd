package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Player;
import com.example.demo.entity.enums.Role;
import com.example.demo.request_response.AuthRequest;
import com.example.demo.request_response.AuthResponse;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AdminService;
import com.example.demo.service.PlayerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private final AdminService adminService;
    private final PlayerService playerService;

    public AuthController(JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder,
                          UserDetailsService userDetailsService,
                          AdminService adminService,
                          PlayerService playerService
    ) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.adminService = adminService;
        this.playerService = playerService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid  @RequestBody Player player) {
        if (playerService.existsByUserNameAndEmail(player.getUserName(), player.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "user already exists"));

        }
        player.setRole(Role.USER);
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        Player currentPlayer = playerService.save(player);

        Map<String, Object> respone = new HashMap<>();

        respone.put("id", currentPlayer.getId());
        respone.put("userName", currentPlayer.getUserName());
        respone.put("email", currentPlayer.getEmail());
        respone.put("password", currentPlayer.getPassword());
        respone.put("score", 0);
        respone.put("role", currentPlayer.getRole());

        return ResponseEntity.ok(respone);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate
                    (new UsernamePasswordAuthenticationToken(
                            authRequest.getUserName(),
                            authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalis username and password");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());
        final String jwt = jwtUtil.generateJwt(authRequest.getUserName());
        if ("admin".equals(authRequest.getUserName())) {
            Admin admin = adminService.findByUserName(authRequest.getUserName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
            return ResponseEntity.ok(new AuthResponse(
                    admin.getId(),
                    jwt,
                    admin.getUserName(),
                    admin.getRole()
            ));
        }
        Player player = playerService.findByUserName(authRequest.getUserName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found"));
        return ResponseEntity.ok(
                new AuthResponse(
                        player.getId(),
                        jwt,
                        player.getUserName(),
                        player.getRole()
                )
        );

    }

    @PostMapping("/log_out")
    public String logOut(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return "Log ot successfully";
    }
}
