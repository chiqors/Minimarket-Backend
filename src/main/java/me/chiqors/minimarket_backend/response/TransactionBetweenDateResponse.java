package me.chiqors.minimarket_backend.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionBetweenDateResponse {
    @JsonProperty("data_pagination")
    private Object dataPagination;

    @JsonProperty("total_price")
    private Double totalPrice;
}
