package me.chiqors.minimarket_backend.controller;

import me.chiqors.minimarket_backend.dto.TransactionDTO;
import me.chiqors.minimarket_backend.service.TransactionService;
import me.chiqors.minimarket_backend.util.JSONResponse;
import me.chiqors.minimarket_backend.validation.TransactionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.FOUND.value(), "Transactions found", transactions, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.FOUND);
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
    @GetMapping("/transactions/{transactionCode}")
    public ResponseEntity<JSONResponse> getTransactionByTransactionCode(@PathVariable String transactionCode) {
        try {
            TransactionDTO transaction = transactionService.getTransactionByTransactionCode(transactionCode);
            if (transaction != null) {
                JSONResponse jsonResponse = new JSONResponse(HttpStatus.FOUND.value(), "Transaction found", transaction, null);
                return new ResponseEntity<>(jsonResponse, HttpStatus.FOUND);
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
