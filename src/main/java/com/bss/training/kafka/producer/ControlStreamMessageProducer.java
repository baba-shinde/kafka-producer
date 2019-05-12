package com.bss.training.kafka.producer;

import com.bss.training.kafka.dto.GenereConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@ManagedResource(objectName="bean:name=controlStreamMessageProducer", description="Bean for controlling the behavior of Stream")
public class ControlStreamMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlStreamMessageProducer.class);
    @Autowired
    private KafkaTemplate<String, GenereConfig> kafkaTemplate;
    private static final String TOPIC = "control_topic";

    @ManagedOperation(description = "operation")
    public String getName() {
        return "dljk";
    }

    @ManagedOperation(description = "To publish message over control topic")
    public String publishMessages(String input) throws Exception {
        GenereConfig genereConfig = getGenereConfig(input);
        kafkaTemplate.send(TOPIC, genereConfig);
        LOGGER.info("Published Control message: {}", genereConfig);

        return "Published successfully";
    }

    private GenereConfig getGenereConfig(String input) {
        String[] array = input.split("=");
        GenereConfig genereConfig = new GenereConfig();
        genereConfig.setAllowed(Boolean.parseBoolean(array[1]));
        genereConfig.setGenere(array[0]);

        return genereConfig;
    }
}