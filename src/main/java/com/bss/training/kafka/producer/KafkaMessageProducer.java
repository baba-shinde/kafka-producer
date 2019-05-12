package com.bss.training.kafka.producer;

import com.bss.training.kafka.dto.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

@Service
public class KafkaMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageProducer.class);
    @Autowired
    private KafkaTemplate<String, Movie> kafkaTemplate;
    private static final String TOPIC = "movies_data";
    private static final String FILE_PATH = "G:\\apache-flink\\data-set\\ml-latest-small\\movies.csv";

    /*@Override
    public void afterPropertiesSet() throws Exception {
        this.publishMessages();
    }*/

    public String publishMessages() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(FILE_PATH)));
        //Ignore first line
        String line = bufferedReader.readLine();
        while((line = bufferedReader.readLine()) != null) {
            Movie movie = getMovieFromLine(line);
            if (movie != null) {
                kafkaTemplate.send(TOPIC, movie);
                LOGGER.info("Published : {}", movie);
                Thread.sleep(2000);
            }
        }

        return "Published successfully";
    }

    private Movie getMovieFromLine(String line) {
        Movie movie = null;
        try {
            movie = new Movie();
            String[] split = line.split(",");
            movie.setMovieId(Integer.parseInt(split[0]));
            movie.setTitle(split[1]);
            movie.setGenres(Arrays.asList(split[2].split("\\|")));

        } catch (RuntimeException rte) {
            //Do not do anything
        }
        return movie;
    }

}