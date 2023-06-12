package me.chiqors.minimarket_backend.dto.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor
public class CustomerTransactionSummaryDTO {
    private String name;
    private String customerCode;
    private String phoneNumber;
    private String address;
    private String gender;
    private Date birthDate;
    private Date createdAt;
    private Date updatedAt;
    private double totalTransactionPrice;
    private int totalPurchased;
}
