package com.example.demo.service;

import com.example.demo.entity.Player;
import com.example.demo.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public boolean existsByUserNameAndEmail(String username, String email) {
        return playerRepository.existsByUserNameAndEmail(username, email);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> findByUserName(String userName) {
        return playerRepository.findByUserName(userName);
    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }
}
