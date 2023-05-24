package com.levtech;

import com.levtech.exceptions.DiscountPercentageException;
import com.levtech.exceptions.MissingPricingDataException;
import com.levtech.exceptions.RentalDurationException;
import com.levtech.exceptions.UnrecognizedToolCodeException;
import com.levtech.models.RentalAgreement;
import com.levtech.util.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMain {

    private Main app;

    @BeforeEach
    public void setUp() {
        this.app = new Main();
    }

    @Test
    public void testUnrecognizedToolCode() throws DiscountPercentageException, RentalDurationException {
        assertThrows(UnrecognizedToolCodeException.class, () -> {
            this.app.checkout("APLE", 2, 0, "05/22/23");
        });
    }

    @Test
    public void testMissingPricingData() throws DiscountPercentageException, RentalDurationException {
        assertThrows(MissingPricingDataException.class, () -> {
            this.app.checkout("DRLM", 2, 0, "05/22/23");
        });
    }

    @Test
    public void testDiscountPercentOutOfRange() {
        assertThrows(DiscountPercentageException.class, () -> {
            this.app.checkout("JAKR", 5, -1, "9/3/15");
        });
        assertThrows(DiscountPercentageException.class, () -> {
            this.app.checkout("JAKR", 5, 101, "9/3/15");
        });
    }

    @Test
    public void testRentalDurationOutOfRange() {
        assertThrows(RentalDurationException.class, () -> {
            this.app.checkout("JAKR", 0, 0, "9/3/15");
        });
    }

    @Test
    public void testFourthOfJulySaturdayObservance()
            throws DiscountPercentageException, RentalDurationException, UnrecognizedToolCodeException, MissingPricingDataException {
        RentalAgreement agreement = this.app.checkout("LADW", 3, 10, "7/2/20");
        assertEquals("LADW", agreement.getToolCode());
        assertEquals("Werner", agreement.getToolBrand());
        assertEquals("Ladder", agreement.getToolType());
        assertEquals(LocalDate.parse("7/2/20", Utils.getDateFormat()), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("7/5/20", Utils.getDateFormat()), agreement.getDueDate());
        assertEquals(3, agreement.getDuration());
        assertEquals(2, agreement.getChargeDays());
        assertEquals(1.99, agreement.getDailyCharge());
        assertEquals(10, agreement.getDiscountPercent());
        assertEquals(3.98, agreement.getPreDiscountCharge());
        assertEquals(0.40, agreement.getDiscountAmount());
        assertEquals(3.58, agreement.getFinalCharge());

        agreement = this.app.checkout("CHNS", 5, 25, "7/2/15");
        assertEquals("CHNS", agreement.getToolCode());
        assertEquals("Stihl", agreement.getToolBrand());
        assertEquals("Chainsaw", agreement.getToolType());
        assertEquals(LocalDate.parse("7/2/15", Utils.getDateFormat()), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("7/7/15", Utils.getDateFormat()), agreement.getDueDate());
        assertEquals(5, agreement.getDuration());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(1.49, agreement.getDailyCharge());
        assertEquals(25, agreement.getDiscountPercent());
        assertEquals(4.47, agreement.getPreDiscountCharge());
        assertEquals(1.12, agreement.getDiscountAmount());
        assertEquals(3.35, agreement.getFinalCharge());

        agreement = this.app.checkout("JAKR", 9, 0, "7/2/15");
        assertEquals("JAKR", agreement.getToolCode());
        assertEquals("Ridgid", agreement.getToolBrand());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals(LocalDate.parse("7/2/15", Utils.getDateFormat()), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("7/11/15", Utils.getDateFormat()), agreement.getDueDate());
        assertEquals(9, agreement.getDuration());
        assertEquals(5, agreement.getChargeDays());
        assertEquals(2.99, agreement.getDailyCharge());
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(14.950000000000001, agreement.getPreDiscountCharge()); //TODO why not truncate 0.000000000000001
        assertEquals(0.00, agreement.getDiscountAmount());
        assertEquals(14.95, agreement.getFinalCharge());

        agreement = this.app.checkout("JAKR", 4, 50, "7/2/20");
        assertEquals("JAKR", agreement.getToolCode());
        assertEquals("Ridgid", agreement.getToolBrand());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals(LocalDate.parse("7/2/20", Utils.getDateFormat()), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("7/6/20", Utils.getDateFormat()), agreement.getDueDate());
        assertEquals(4, agreement.getDuration());
        assertEquals(1, agreement.getChargeDays());
        assertEquals(2.99, agreement.getDailyCharge());
        assertEquals(50, agreement.getDiscountPercent());
        assertEquals(2.99, agreement.getPreDiscountCharge());
        assertEquals(1.50, agreement.getDiscountAmount());
        assertEquals(1.49, agreement.getFinalCharge());
    }

    @Test
    public void testFourthOfJulySundayObservance()
            throws DiscountPercentageException, RentalDurationException, UnrecognizedToolCodeException, MissingPricingDataException {
        RentalAgreement agreement = this.app.checkout("JAKD", 6, 30, "7/1/10");
        assertEquals("JAKD", agreement.getToolCode());
        assertEquals("DeWalt", agreement.getToolBrand());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals(LocalDate.parse("7/1/10", Utils.getDateFormat()), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("7/7/10", Utils.getDateFormat()), agreement.getDueDate());
        assertEquals(6, agreement.getDuration());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(2.99, agreement.getDailyCharge());
        assertEquals(30, agreement.getDiscountPercent());
        assertEquals(8.97, agreement.getPreDiscountCharge());
        assertEquals(2.69, agreement.getDiscountAmount());
        assertEquals(6.28, agreement.getFinalCharge());
    }

    @Test
    public void testLaborDayObservance()
            throws DiscountPercentageException, RentalDurationException, UnrecognizedToolCodeException, MissingPricingDataException {
        RentalAgreement agreement = this.app.checkout("JAKD", 6, 0, "9/3/15");
        assertEquals("JAKD", agreement.getToolCode());
        assertEquals("DeWalt", agreement.getToolBrand());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals(LocalDate.parse("9/3/15", Utils.getDateFormat()), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("9/9/15", Utils.getDateFormat()), agreement.getDueDate());
        assertEquals(6, agreement.getDuration());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(2.99, agreement.getDailyCharge());
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(8.97, agreement.getPreDiscountCharge());
        assertEquals(0.00, agreement.getDiscountAmount());
        assertEquals(8.97, agreement.getFinalCharge());

        agreement = this.app.checkout("LADW", 4, 40, "9/2/22");
        assertEquals("LADW", agreement.getToolCode());
        assertEquals("Werner", agreement.getToolBrand());
        assertEquals("Ladder", agreement.getToolType());
        assertEquals(LocalDate.parse("9/2/22", Utils.getDateFormat()), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("9/6/22", Utils.getDateFormat()), agreement.getDueDate());
        assertEquals(4, agreement.getDuration());
        assertEquals(3, agreement.getChargeDays());
        assertEquals(1.99, agreement.getDailyCharge());
        assertEquals(40, agreement.getDiscountPercent());
        assertEquals(5.97, agreement.getPreDiscountCharge());
        assertEquals(2.39, agreement.getDiscountAmount());
        assertEquals(3.58, agreement.getFinalCharge());

        agreement = this.app.checkout("CHNS", 4, 0, "9/2/22");
        assertEquals("CHNS", agreement.getToolCode());
        assertEquals("Stihl", agreement.getToolBrand());
        assertEquals("Chainsaw", agreement.getToolType());
        assertEquals(LocalDate.parse("9/2/22", Utils.getDateFormat()), agreement.getCheckoutDate());
        assertEquals(LocalDate.parse("9/6/22", Utils.getDateFormat()), agreement.getDueDate());
        assertEquals(4, agreement.getDuration());
        assertEquals(2, agreement.getChargeDays());
        assertEquals(1.49, agreement.getDailyCharge());
        assertEquals(0, agreement.getDiscountPercent());
        assertEquals(2.98, agreement.getPreDiscountCharge());
        assertEquals(0, agreement.getDiscountAmount());
        assertEquals(2.98, agreement.getFinalCharge());
    }
}
