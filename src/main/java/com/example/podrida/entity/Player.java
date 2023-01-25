package com.example.podrida.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private int playerOrder=-1;
    @OneToMany(mappedBy = "player", cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    private Set<Hand> playerHands = new HashSet<>();

    @ManyToOne
    @JoinColumn
    private Game game;
    @OneToMany(mappedBy = "player",fetch = FetchType.EAGER)
    private Set<MistakesMade> mistakesMadeList = new HashSet<>();

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", playerOrder=" + playerOrder +
                ", playerHands=" + playerHands.size() +
                ", game=" + game.getId() +
                '}';
    }
}
