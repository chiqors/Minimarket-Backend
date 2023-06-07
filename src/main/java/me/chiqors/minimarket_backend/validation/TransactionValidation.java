package me.chiqors.minimarket_backend.validation;

import me.chiqors.minimarket_backend.dto.TransactionDTO;
import me.chiqors.minimarket_backend.dto.TransactionDetailDTO;
import me.chiqors.minimarket_backend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionValidation {
    @Autowired
    private TransactionService transactionService;

    public List<String> createTransactionValidation(TransactionDTO transactionDTO) {
        List<String> errors = new ArrayList<>();

        if (transactionDTO.getCustomer() == null) {
            errors.add("Customer is required");
        } else {
            if (transactionDTO.getCustomer().getCustomerCode() == null) {
                errors.add("Customer code is required");
            } else {
                // Check if customer exists
                if (!transactionService.isCustomerExists(transactionDTO.getCustomer().getCustomerCode())) {
                    errors.add("Customer with code " + transactionDTO.getCustomer().getCustomerCode() + " is not found");
                }
            }
        }

        if (transactionDTO.getEmployee() == null) {
            errors.add("Employee is required");
        } else {
            if (transactionDTO.getEmployee().getEmployeeCode() == null) {
                errors.add("Employee code is required");
            } else {
                // Check if employee exists
                if (!transactionService.isEmployeeExists(transactionDTO.getEmployee().getEmployeeCode())) {
                    errors.add("Employee with code " + transactionDTO.getEmployee().getEmployeeCode() + " is not found");
                }
            }
        }

        if (transactionDTO.getTransactionDetails() == null) {
            errors.add("Transaction details is required");
        } else {
            if (transactionDTO.getTransactionDetails().size() == 0) {
                errors.add("Transaction details is required");
            } else {
                for (TransactionDetailDTO transactionDetailDTO : transactionDTO.getTransactionDetails()) {
                    if (transactionDetailDTO.getProductSku() == null) {
                        errors.add("Product SKU Code is required");
                    } else {
                        // Check if product exists
                        if (!transactionService.isProductExists(transactionDetailDTO.getProductSku())) {
                            errors.add("Product with SKU Code " + transactionDetailDTO.getProductSku() + " is not found");
                        }
                    }

                    if (transactionDetailDTO.getQuantity() == null) {
                        errors.add("Quantity is required");
                    } else {
                        if (transactionDetailDTO.getQuantity() <= 0) {
                            errors.add("Quantity must be greater than 0");
                        } else {
                            // Check if product quantity is enough
                            if (!transactionService.isProductQuantityEnough(transactionDetailDTO.getProductSku(), transactionDetailDTO.getQuantity())) {
                                errors.add("Product with SKU Code " + transactionDetailDTO.getProductSku() + " quantity is not enough");
                            }
                        }
                    }
                }
            }
        }

        return errors;
    }
}
