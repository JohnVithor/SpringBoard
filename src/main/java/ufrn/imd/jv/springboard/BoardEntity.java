package ufrn.imd.jv.springboard;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_gen")
    @SequenceGenerator(name = "board_gen", sequenceName = "board_seq", allocationSize = 1)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotBlank
    @Column(unique = true)
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Long userId;

    public BoardEntity() {
    }

    public BoardEntity(Long id, String name, String description, Long userId) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long ownerId) {
        this.userId = ownerId;
    }
}
