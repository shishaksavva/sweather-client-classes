package com.ultra.sweather.datahttp;

import lombok.Data;
import lombok.ToString;

import java.util.List;

public interface ResponseData {
    @Data
    @ToString
    class ResponseCitiesData {
        private String documentation;
        private int total_results;
        private String thanks;

        @Data
        @ToString
        public class License {
            private String name;
            private String url;
        }
        private List<License> licenses;
    }
}
