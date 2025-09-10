package ProfanityService.config;

import ProfanityService.entity.BannedWord;
import ProfanityService.service.BannedWordService_I;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    ApplicationRunner init(BannedWordService_I bannedWordService) {
        return args -> {
            String haddocksBedste = "Abemås, agurketud, analfabet, asfaltcowboy, bacille, bandit, barnerøver, basilisk, bedrager, billige bavian, bisse, bovlamme bladanblander, brandstifter, brutale bilbølle, bums, burgøjser, bæst, bøddel, bøhtosse, bølle, bøllefrø, børste, cykeltyv, daddelplukker, desertør, dovendyr, drukkenbolt, egoist, facist, fedtblære, fedtemikkel, fedthalefår, fladpande, flommefede fjollerik, forbryder, forlismand, forlorne tæppehandler, fortidsuhyre, frysefrederik, fyldebøtte, gangster, gespenst, grimrian, grobrian, haleneger, hallunk, hulepindsvin, hærværksmand, høvl, igle, ignorant, individ, interplanetariske slørhale, jubeltorsk, justitsmorder, kakerlak, kannibal, karnevalssørøver, klaptorsk, kleptoman, klodrian, klodsmajor, knoldvækst, kryb, krybskytte, krudtugle, kvajpande, kvælstofbacille, kæltring, køter, laban, lakaj, landevejsrøver, landkrabbe, landsforræder, laskefede lommetyv, lejesvend, lemmedasker, lomme-Moussolini, luksusdyr, lumskebuks, lurendrejer, lusepuster, luskebuks, lydpottelus, lystmorder, læderjakke, løjser, makrelslugere, marxist, massemorder, menneskefjende, menneskeæder, misdæder, modbydelige mide, mordbrænder, morder, motorbølle, møgdyr, pirat, pladderabe, platfodede moskusokse, plebejer, prøjser, pungdyr, pyroman, racist, rottefjæs, sadist, samfundssnylter, sandalslæber, sandloppe, sandmide, sinke, sjofelist, sjover, sjuft, skadedyr, skallesmækker, skamstøtte, skunk, skurk, skvatdragon, skvatmelon, skægabe, slagsbror, slambert, slapsvans, sleske spytslikker, slubbert, slyngel, smugler, smørtyv, snigmorder, snigskytte, snoabe, snog, snydetamp, snylter, sortbørsgrosserer, spritbilist, spritter, spruttyv, sprællemand, spyflue, starut, stikker, stymper, subjekt, superskurk, sut, svamp, svin, svindler, svirebror, svumpukkel, søndagsrytter, søpindsvinefjæs, sørøver, tale-delirist, tamp, tangloppe, terrorist, torskepande, trafikbisse, trompetsnegl, tyran, tyveknægt, tøffelhelt, tøsedreng, udbytter, undermåler, vagabond, vampyr, vandrotte, vandal, væggetøjsbefængte varulv, varyl, vatnisse, voldsmand, æselmassør, øgle";
            Set<String> existingWords = bannedWordService.findAllLowercaseBannedWords();
            List<String> badwordsAsString = new ArrayList<>(List.of(haddocksBedste.split(", ")));
            Set<String> seen = new HashSet<>();
            List<BannedWord> toSave = new ArrayList<>();
            for(String badword : badwordsAsString) {
                String normalized = badword.toLowerCase();
                if(normalized.isEmpty()) continue;
                if(seen.add(normalized) && !existingWords.contains(normalized)) {
                    toSave.add(new BannedWord(normalized));
                }
            }
            if(!toSave.isEmpty()) {
                bannedWordService.saveAll(toSave);
            }
        };
    }
}
