package com.example.eventhall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @SequenceGenerator(
            name = "img_seq",
            sequenceName = "img_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "img_seq"
    )
    private Long id;
    private String imgName;
    private String imgPath;

    @ManyToOne(cascade = CascadeType.ALL)
    private Hall hall;
}
