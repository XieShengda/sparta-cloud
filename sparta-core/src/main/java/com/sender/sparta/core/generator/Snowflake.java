package com.sender.sparta.core.generator;

import com.sender.sparta.core.config.properties.MetaProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Log4j2
@Component
@EnableConfigurationProperties(MetaProperties.class)
public class Snowflake {
    private final MetaProperties metaProperties;
    private long lastEpoch;
    private long offset;

    public Snowflake(MetaProperties metaProperties) {
        this.metaProperties = metaProperties;
    }

    public long nextId() {
        return nextId(Instant.now().getEpochSecond());
    }

    private synchronized long nextId(long epochSecond) {
        if (epochSecond < lastEpoch) {
            // warning: clock is turn back:
            log.warn("clock is back: " + epochSecond + " from previous:" + lastEpoch);
            epochSecond = lastEpoch;
        }
        if (lastEpoch != epochSecond) {
            lastEpoch = epochSecond;
            reset();
        }
        offset++;
        long next = offset & metaProperties.getMaxNext();
        if (next == 0) {
            log.warn("maximum id reached in 1 second in epoch: " + epochSecond);
            return nextId(epochSecond + 1);
        }
        return generateId(epochSecond, next);
    }

    private void reset() {
        offset = 0;
    }

    private long generateId(long epochSecond, long next) {
        return epochSecond << 21 | next << 5 | metaProperties.getShardId();
    }

}
