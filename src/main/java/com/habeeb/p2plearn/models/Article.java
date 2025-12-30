package com.habeeb.p2plearn.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Entity
@NoArgsConstructor
@Table(name = "articles")
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @Column(unique = true,nullable = false)
    private String slug;
    @Column( name = "cover_img_url")
    private String coverImgUrl;
    @Enumerated(EnumType.STRING)
    private ArticleCategory category;
    @Transient
    private Long likes =0L;
    @Transient
    private Long dislikes=0L;
    @Transient
    private Long views=0L;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime updatedAt;
    @NotNull
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String bodyMarkdown;
    @Column(columnDefinition = "TEXT")
    private String bodyHtml;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "article")
    private List<Comment> comments;

    private String toSlug(String title){
        String noSpace = title.trim().replaceAll(" ","-");
        String normalized = Normalizer.normalize(noSpace,Normalizer.Form.NFD);
        return normalized.replaceAll("[^\\w-]","").toLowerCase(Locale.ROOT) + System.currentTimeMillis();
    }

    public void setTitle(String title){
        this.title = title;
        this.slug = toSlug(title);
    }
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.slug == null || this.slug.isEmpty()) {
            this.slug = toSlug(this.title);
        }
        if (this.likes == null) {
            this.likes = 0L;
        }
        if (this.dislikes == null) {
            this.dislikes = 0L;
        }
        if (this.views == null) {
            this.views = 0L;
        }
    }


    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
