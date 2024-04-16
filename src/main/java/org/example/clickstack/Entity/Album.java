package org.example.clickstack.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Table(name="album")
public class Album {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false,unique = true)
    private String name;
    private String Description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "album")
    private List<Photos> photos;

}
