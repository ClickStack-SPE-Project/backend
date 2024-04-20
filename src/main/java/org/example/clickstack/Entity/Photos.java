package org.example.clickstack.Entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Table(name="photos")
public class Photos {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false,unique = true)
    private String name;
    private String link;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    public Album album;
}
