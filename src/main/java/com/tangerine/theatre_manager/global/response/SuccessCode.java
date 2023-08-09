package com.tangerine.theatre_manager.global.response;

public enum SuccessCode {
    /**
     * Performance
     */
    PERFORMANCE_SAVE_SUCCESS("공연이 성공적으로 추가되었습니다."),
    PERFORMANCE_UPDATE_SUCCESS("공연이 성공적으로 수정되었습니다."),
    PERFORMANCE_DELETE_SUCCESS("공연이 성공적으로 삭제되었습니다."),

    /**
     * Ticket Order
     */
    TICKET_ORDER_SAVE_SUCCESS("티켓 주문이 성공적으로 접수되었습니다."),
    TICKET_ORDER_DELETE_SUCCESS("티켓 주문이 성공적으로 취소되었습니다.");

    private final String message;

    SuccessCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
