package com.n26.transactions.resources;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class StatisticsResponseResource {

    private String sum;
    private String avg;
    private String max;
    private String min;
    private Long count;

}
