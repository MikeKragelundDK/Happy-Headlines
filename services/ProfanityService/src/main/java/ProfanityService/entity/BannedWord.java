package ProfanityService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "banned_words", schema = "banned_words")
public class BannedWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "word", nullable = false)
    private String bannedWord;

    public BannedWord(String bannedWord) {
        this.bannedWord = bannedWord;
    }

    protected BannedWord() {

    }
}
