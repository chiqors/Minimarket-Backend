package me.chiqors.minimarket_backend.controller;

import me.chiqors.minimarket_backend.dto.TransactionDTO;
import me.chiqors.minimarket_backend.dto.custom.MostPurchasedProductDTO;
import me.chiqors.minimarket_backend.dto.custom.CustomerPurchasedDTO;

import me.chiqors.minimarket_backend.dto.custom.TransactionBetweenDateDTO;
import me.chiqors.minimarket_backend.response.TransactionBetweenDateResponse;
import me.chiqors.minimarket_backend.service.TransactionService;
import me.chiqors.minimarket_backend.util.JSONResponse;
import me.chiqors.minimarket_backend.validation.TransactionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("${api.prefix}")
public class TransactionController {
    @Autowired
    private TransactionValidation transactionValidation;
    @Autowired
    private TransactionService transactionService;

    /**
     * Get all transactions with pagination
     *
     * @param page (optional) page number
     * @param size (optional) number of items per page
     * @return ResponseEntity with status code and JSONResponse
     */
    @GetMapping("/transactions")
    public ResponseEntity<JSONResponse> getAllTransactions(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                           @RequestParam(required = false, defaultValue = "3") Integer size) {
        try {
            Page<TransactionDTO> transactions = transactionService.getAllTransaction(page, size);
            if (transactions.getTotalElements() > 0) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Transactions found", transactions, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Transactions not found", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get transaction by transactionCode
     *
     * @param transactionCode transaction code
     * @return ResponseEntity with status code and JSONResponse
     */
    @GetMapping("/transactions/view/{transactionCode}")
    public ResponseEntity<JSONResponse> getTransactionByTransactionCode(@PathVariable String transactionCode) {
        try {
            TransactionDTO transaction = transactionService.getTransactionByTransactionCode(transactionCode);
            if (transaction != null) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Transaction found", transaction, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Transaction not found", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get a list of transaction and the total of transaction in between date
     *
     * @param start_date (optional) start date
     * @param end_date  (optional) end date
     * @param page (optional) page number
     * @param size (optional) number of items per page
     * @return ResponseEntity with status code and JSONResponse
     */
    @GetMapping("/transactions/date")
    public ResponseEntity<JSONResponse> getTransactionByDate(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start_date, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end_date, @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "3") Integer size) {
        try {
            Page<TransactionBetweenDateDTO> transactions = transactionService.getTransactionByDate(start_date, end_date, page, size);
            Double sum = transactionService.getTransactionSum(start_date, end_date);
            TransactionBetweenDateResponse response = new TransactionBetweenDateResponse(transactions, sum);
            if (transactions.getTotalElements() > 0) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Transactions found", response, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Transactions not found", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the three most bought product
     *
     * @return List of MostPurchaseProductDTO
     */
    @GetMapping("/transactions/most-purchase-product")
    public ResponseEntity<JSONResponse> getMostPurchaseProduct() {
        try {
            List<MostPurchasedProductDTO> mostPurchaseProducts = transactionService.getMostPurchasedProduct();
            if (mostPurchaseProducts.size() > 0) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Top 3 Most purchase products found", mostPurchaseProducts, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Most purchase products not found", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the list of customer who purchased items from transactions in between date
     *
     * @param start_date Start date of transaction
     * @param end_date End date of transaction
     * @return List of CustomerPurchasedDTO
     */
    @GetMapping("/transactions/customer-purchased")
    public ResponseEntity<JSONResponse> getCustomerPurchased(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start_date, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end_date, @RequestParam(required = false, defaultValue = "1") Integer page, @RequestParam(required = false, defaultValue = "3") Integer size) {
        try {
            Page<CustomerPurchasedDTO> customerPurchased = transactionService.getCustomerPurchasedBetweenDate(start_date, end_date, page, size);
            if (customerPurchased.getTotalElements() > 0) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Customer purchased found", customerPurchased, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Customer purchased not found", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the list of product that often purchased by customer
     * Desc: User input product skuCode, then the system will return the list of product that often purchased with the input product by customer
     *
     * @param skuCode Product skuCode
     * @return List of MostPurchasedProductDTO
     */
    @GetMapping("/transactions/most-purchased-with/{skuCode}")
    public ResponseEntity<JSONResponse> getMostPurchasedWith(@PathVariable String skuCode) {
        try {
            List<MostPurchasedProductDTO> mostPurchasedWith = transactionService.getProductOftenPurchased(skuCode);
            if (mostPurchasedWith.size() > 0) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.OK.value(), "Most purchased with found", mostPurchasedWith, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
            } else {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.NOT_FOUND.value(), "Most purchased with not found", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
            return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create transaction
     *
     * @param transactionDTO transaction data
     * @return ResponseEntity with status code and JSONResponse
     */
    @PostMapping("/transactions")
    public ResponseEntity<JSONResponse> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        List<String> errors = transactionValidation.createTransactionValidation(transactionDTO);
        if (errors.isEmpty()) {
            try {
                TransactionDTO transaction = transactionService.createTransaction(transactionDTO);
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.CREATED.value(), "Transaction created", transaction, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            JSONResponse jsonResponse = new JSONResponse(HttpStatus.BAD_REQUEST.value(), "Bad request", null, errors);
            return new ResponseEntity<>(jsonResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
