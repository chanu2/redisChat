package chattest.project.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor()
@Setter
@Entity
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String profilePath;

    @Builder
    public Member( String name, String email, String profilePath) {
        this.name = name;
        this.email = email;
        this.profilePath = profilePath;
    }



}
