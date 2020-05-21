package com.sender.sparta;

import com.sender.sparta.config.properties.MetaProperties;
import com.sender.sparta.generator.Snowflake;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
@EnableConfigurationProperties(MetaProperties.class)
public class SnowflakeTest {

    @Autowired
    private Snowflake snowflake;
    @Autowired
    private MetaProperties metaProperties;

    @Test
    void idGenerate() {
        String id = Long.toBinaryString(snowflake.nextId());
        log.info("id: {}", id);
        log.info("max-next: {}", Long.toBinaryString(metaProperties.getMaxNext()));
        Assertions.assertEquals(0xFFFF, metaProperties.getMaxNext());
        Assertions.assertEquals(0, metaProperties.getShardId());
        Assertions.assertEquals(52, id.length());

    }
}
