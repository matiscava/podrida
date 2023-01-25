package com.example.podrida.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Hand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int predict;
    private int take;
    private int points;
    private int handNumber;
    @ManyToOne
    @JoinColumn
    private Player player;

    @Override
    public String toString() {
        return "Hand{" +
                "id=" + id +
                ", predict=" + predict +
                ", take=" + take +
                ", points=" + points +
                ", handNumber=" + handNumber +
                ", player=" + player.getId() +
                '}';
    }
}
