package com.github.yirelav.quotessolution.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ratings")
@Builder
public final class RatingHistoryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "difference")
    private Integer difference;

    @Column(name = "total")
    private Integer total;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "date")
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quote_id")
    private Quote quote;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant created;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant updated;
}

