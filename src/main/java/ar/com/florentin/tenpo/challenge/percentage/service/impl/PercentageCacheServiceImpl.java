package ar.com.florentin.tenpo.challenge.percentage.service.impl;

import ar.com.florentin.tenpo.challenge.percentage.entity.PercentageCache;
import ar.com.florentin.tenpo.challenge.percentage.repository.PercentageCacheRepository;
import ar.com.florentin.tenpo.challenge.percentage.service.PercentageCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static ar.com.florentin.tenpo.challenge.util.Constants.PERCENTAGE_CACHE_KEY;

@Slf4j
@Service
public class PercentageCacheServiceImpl implements PercentageCacheService {
    private final PercentageCacheRepository percentageCacheRepository;

    public PercentageCacheServiceImpl(PercentageCacheRepository percentageCacheRepository) {
        this.percentageCacheRepository = percentageCacheRepository;
    }

    @Override
    public Mono<BigDecimal> get() {
        log.info("Searching percentage in cache");
        return percentageCacheRepository.get(PERCENTAGE_CACHE_KEY).map(PercentageCache::getLastValue);
    }

    @Override
    public Mono<BigDecimal> save(BigDecimal value) {
        log.info("Saving percentage in cache");
        final PercentageCache entity = PercentageCache.builder().lastValue(value).build();
        return this.percentageCacheRepository.save(entity).map(PercentageCache::getLastValue);
    }
}
