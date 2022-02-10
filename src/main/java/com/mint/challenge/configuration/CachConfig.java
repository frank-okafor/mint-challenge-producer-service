package com.mint.challenge.configuration;

import java.util.concurrent.TimeUnit;

import org.modelmapper.ModelMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CachConfig {

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("productCache", "orderCache");
		cacheManager.setCaffeine(caffeineCachBuilder());
		return cacheManager;
	}

	public Caffeine<Object, Object> caffeineCachBuilder() {
		return Caffeine.newBuilder().initialCapacity(100).maximumSize(500).expireAfterAccess(10, TimeUnit.MINUTES)
				.weakKeys().recordStats();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
