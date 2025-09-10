package ProfanityService.service;

import ProfanityService.entity.BannedWord;
import ProfanityService.util.Result;

import java.util.List;
import java.util.Set;

public interface BannedWordService_I {
    void reload();
    Result check(String text);
    void addBannedWord(BannedWord bannedWord);
    Set<String> findAllLowercaseBannedWords();
    void saveAll(List<BannedWord> bannedWords);
}
