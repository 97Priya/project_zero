package com.mts.services;

import com.mts.dto.AmoutTransferDetail;

import java.util.Scanner;

public interface MoneyTransferService {
    AmoutTransferDetail getDetails(Scanner scanner);

    boolean transfer(AmoutTransferDetail amoutTransferDetail);
}
