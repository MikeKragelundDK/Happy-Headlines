package ProfanityService.service;

import ProfanityService.dao.BannedWordRepository;
import ProfanityService.entity.BannedWord;
import ProfanityService.util.Result;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class BannedWordServiceImpl implements BannedWordService_I {
    private final BannedWordRepository bannedWordRepository;
    private volatile Set<String> cache = Set.of();

    public BannedWordServiceImpl(BannedWordRepository bannedWordRepository) {
        this.bannedWordRepository = bannedWordRepository;
    }

    @PostConstruct
    public void load(){
        cache = new HashSet<>(bannedWordRepository.findAllLowercaseBannedWords());
    }

    // Auto reloads the cache db every 5 mins. - reducing db calls.
    @Scheduled(fixedDelayString = "PT5M")
    public void reload(){ load();}


    // Might be nice to expand with a profanity score later, so i keep the hits in a record..
    public Result check (String text){
        if(text == null){
            return new Result(false, List.of());
        }
        String[] tokens = text.split(" ");
        List<String> hits = new ArrayList<>();

        for(String t : tokens){
            if(!t.isBlank() && cache.contains((String)t)){
                hits.add(t);
            }
        }
        boolean isProfane = !hits.isEmpty();
        return new Result(isProfane, hits);
    }

    @Override
    public void addBannedWord(BannedWord bannedWord) {
        bannedWordRepository.save(bannedWord);
    }

    @Override
    public Set<String> findAllLowercaseBannedWords() {
        return bannedWordRepository.findAllLowercaseBannedWords();
    }

    @Override
    public void saveAll(List<BannedWord> bannedWords) {
        bannedWordRepository.saveAll(bannedWords);
    }


}
