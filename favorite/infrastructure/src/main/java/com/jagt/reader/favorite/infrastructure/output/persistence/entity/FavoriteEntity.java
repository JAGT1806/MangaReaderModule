package com.jagt.reader.favorite.infrastructure.output.persistence.entity;

import com.jagt.reader.user.infrastructure.output.persistence.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favorites", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "manga_id"})
})
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String mangaId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private LocalDateTime createdAt;
}
