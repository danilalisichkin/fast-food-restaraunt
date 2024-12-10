package com.fastfoodrestaraunt.backend.validator;

import com.fastfoodrestaraunt.backend.core.enums.Status;
import com.fastfoodrestaraunt.backend.entity.Order;
import com.fastfoodrestaraunt.backend.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void validateOrderStatusChanging(Order order, Status newStatus) {
        int currentStatusOrdinal = order.getStatus().ordinal();
        int newStatusOrdinal = newStatus.ordinal();

        if (currentStatusOrdinal == Status.DELIVERED.ordinal()) {
            throw new BadRequestException("can not change order status after it is delivered");
        } else if (currentStatusOrdinal == Status.CANCELLED.ordinal()) {
            throw new BadRequestException("can not change order status after it is cancelled");
        }

        if (newStatusOrdinal == Status.CANCELLED.ordinal()) {
            return;
        }

        if (!ifChangeStatusToNextStage(currentStatusOrdinal, newStatusOrdinal)) {
            throw new BadRequestException(
                    String.format("can not change order status from %s to %s",order.getStatus(), newStatus));
        }
    }

    private boolean ifChangeStatusToNextStage(int currentStatus, int newStatus) {
        return currentStatus + 1 == newStatus;
    }
}
