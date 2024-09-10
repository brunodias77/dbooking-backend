package com.brunodias.dbooking.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room extends EntityBase {

    private BigDecimal roomPrice;

    private int numberOfGuest;

    private int doubleBed = 0;

    private int singleBed = 0;

    private boolean isBooked = false;

    private String location;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "room_photos", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "photo")
    private List<String> photos = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BookedRoom> bookings;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "room_ratings", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "rating")
    private List<Integer> ratings = new ArrayList<>();

    public void addBooking(BookedRoom booking) {
        if (bookings == null) {
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingCode);
    }

    public boolean getIsBooked() {
        return isBooked;
    }

    public void addPhoto(String photo) {
        this.photos.add(photo);
    }

    public void addRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("A classificação deve estar entre 1 e 5");
        }
        this.ratings.add(rating);
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

}
