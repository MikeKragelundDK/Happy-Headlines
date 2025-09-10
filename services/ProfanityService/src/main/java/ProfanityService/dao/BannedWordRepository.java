package ProfanityService.dao;

import ProfanityService.entity.BannedWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;

public interface BannedWordRepository extends JpaRepository<BannedWord, Long> {
    @Query("select lower(b.bannedWord) from BannedWord b")
    Set<String> findAllLowercaseBannedWords();


}
