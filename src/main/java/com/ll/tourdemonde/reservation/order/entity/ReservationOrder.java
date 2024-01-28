package com.ll.tourdemonde.reservation.order.entity;

        import jakarta.persistence.Entity;
        import jakarta.persistence.GeneratedValue;
        import jakarta.persistence.Id;
        import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class ReservationOrder {
    @Id
    @GeneratedValue
    private Long id;

    private String buyerName;

    private boolean paid;

}