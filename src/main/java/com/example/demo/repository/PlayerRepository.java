package com.example.demo.repository;

import com.example.demo.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByUserName(String userName);

    boolean existsByUserNameAndEmail(String userName, String email);

    @Query("select p from Player p left join fetch p.quizScoreResultList")
    List<Player> findAllWithQuizScoreResultList();


    @Query("select  p from Player  p left join fetch p.quizScoreResultList where  p.id = :id")
    Optional<Player> findByIdWithResults(@Param("id")Long id);

    boolean existsPlayerByUserName(String userName);

    boolean existsPlayerByEmail(String email);
}
