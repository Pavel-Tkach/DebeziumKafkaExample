package org.example.debeziumapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.debeziumapp.entity.enums.PackageStatus;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PackageStatus status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private List<Change> changes;
}
