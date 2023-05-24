package com.levtech;

import com.levtech.exceptions.DiscountPercentageException;
import com.levtech.exceptions.MissingPricingDataException;
import com.levtech.exceptions.RentalDurationException;
import com.levtech.exceptions.UnrecognizedToolCodeException;
import com.levtech.models.PriceData;
import com.levtech.models.RentalAgreement;
import com.levtech.models.Tool;
import com.levtech.util.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    private List<Tool> tools;
    private List<PriceData> pricing;

    protected Main() {
        this.tools = new ArrayList<>();
        this.tools.add(new Tool("CHNS", "Chainsaw", "Stihl"));
        this.tools.add(new Tool("LADW", "Ladder", "Werner"));
        this.tools.add(new Tool("JAKD", "Jackhammer", "DeWalt"));
        this.tools.add(new Tool("JAKR", "Jackhammer", "Ridgid"));
        this.tools.add(new Tool("DRLM", "Power Drill", "Milwaukee"));

        this.pricing = new ArrayList<>();
        this.pricing.add(new PriceData("Ladder", 1.99, true, true, false));
        this.pricing.add(new PriceData("Chainsaw", 1.49, true, false, true));
        this.pricing.add(new PriceData("Jackhammer", 2.99, true, false, false));}

    private int calculateChargeDays(LocalDate checkoutDate, int numOfDays, PriceData pricing) {
        int chargeableDays = 0;
        for(int i = 1; i <= numOfDays; i++) {
            if((Utils.isWeekend(checkoutDate.plusDays(i)) && !pricing.hasWeekendCharge()) ||
                    (Utils.isHoliday(checkoutDate.plusDays(i)) && !pricing.hasHolidayCharge())) {
                continue;
            }
            chargeableDays++;
        }
        return chargeableDays;
    }

    public RentalAgreement checkout(String toolCode, int numOfDays, int discountPercent, String checkoutDate)
            throws RentalDurationException, DiscountPercentageException, UnrecognizedToolCodeException, MissingPricingDataException {
        if (numOfDays < 1) {
            throw new RentalDurationException("Rental duration must be at least one or more days.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new DiscountPercentageException("Discount percentage must between 0 and 100, inclusively.");
        }

        LocalDate formattedCheckoutDate = LocalDate.parse(checkoutDate, Utils.getDateFormat());
        Optional<Tool> requestedTool = this.tools.stream().filter(t->t.getCode().equalsIgnoreCase(toolCode)).findFirst();
        if (requestedTool.isEmpty()) {
            throw new UnrecognizedToolCodeException("The tool code provide does not exist in our system");
        }
        Optional<PriceData> applicablePricing = this.pricing.stream().filter(p->p.getToolType().equals(requestedTool.get().getType())).findFirst();
        if (applicablePricing.isEmpty()) {
            throw new MissingPricingDataException("There does not appear to be any applicable pricing data for the given tool code");
        }
        int chargeDays = calculateChargeDays(formattedCheckoutDate, numOfDays, applicablePricing.get());
        return new RentalAgreement(
                toolCode,
                requestedTool.get().getType(),
                requestedTool.get().getBrand(),
                numOfDays,
                formattedCheckoutDate,
                applicablePricing.get().getDailyCharge(),
                chargeDays,
                discountPercent);
    }

    public static void main(String[] args) {
        Main app = new Main();

        try {
            RentalAgreement agreement = app.checkout("CHNS", 4, 15, "5/22/23");
            System.out.println(agreement);

            agreement = app.checkout("JAKR", 2, 25, "5/18/23");
            System.out.println(agreement);
        } catch (Exception e) {
        }
    }
}
