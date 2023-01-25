package com.example.podrida.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table
public class MistakesMade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Player player;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Mistake mistake;
    private int handNumber;

    @Override
    public String toString() {
        return "MistakesMade{" +
                "id=" + id +
                ", player=" + player +
                ", mistake=" + mistake +
                ", handNumber=" + handNumber +
                '}';
    }
}
