package ArticleService.cache;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {

   private RedisCacheConfiguration baseConfig(Duration ttl){
       return RedisCacheConfiguration.defaultCacheConfig()
               .entryTtl(ttl)
               .disableCachingNullValues()
               .serializeValuesWith(
                       RedisSerializationContext.SerializationPair.fromSerializer(
                               new GenericJackson2JsonRedisSerializer()
                       )
               )
               .computePrefixWith(cacheName -> "article-service:".concat(cacheName).concat(":"));
   }


   @Bean
   public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory){
       Map<String, RedisCacheConfiguration> caches = new HashMap<>();
       // I'll let the id's themself live longer.
       caches.put("articlesById", baseConfig(Duration.ofDays(3)));
       // Latest articles can refresh a bit quicker
       caches.put("latestArticles", baseConfig(Duration.ofHours(1)));
       // 5 mintes on top5 - it might change fast.. idk?
       caches.put("top5Articles", baseConfig(Duration.ofMinutes(5)));

       return RedisCacheManager.builder(connectionFactory)
               .cacheDefaults(baseConfig(Duration.ofMinutes(10)))
               .withInitialCacheConfigurations(caches)
               .build();
   }
}
