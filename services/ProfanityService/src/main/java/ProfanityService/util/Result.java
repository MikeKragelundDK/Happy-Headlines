package ProfanityService.util;

import java.util.List;

public record Result(boolean profane, List<String> hits) {
}
