package santamarket.model;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.List;
import java.util.Objects;

public class ReceiptMatcher extends BaseMatcher<Receipt> {
    private final double targetTotalPrice;
    private final List<ReceiptItem> targetReceiptItems;
    private final List<Discount> targetDiscounts;

    private ReceiptMatcher(double targetTotalPrice, List<ReceiptItem> receiptItems, List<Discount> discounts) {
        this.targetTotalPrice = targetTotalPrice;
        this.targetReceiptItems = receiptItems;
        this.targetDiscounts = discounts;
    }

    public static ReceiptMatcher matches(double totalPrice, List<ReceiptItem> receiptItems) {
        return new ReceiptMatcher(totalPrice, receiptItems, List.of());
    }

    public static ReceiptMatcher matches(double totalPrice, List<ReceiptItem> receiptItems, List<Discount> discounts) {
        return new ReceiptMatcher(totalPrice, receiptItems, discounts);
    }

    @Override
    public boolean matches(Object receipt) {
        return Objects.equals(targetTotalPrice, ((Receipt) receipt).getTotalPrice())
                && Objects.equals(targetReceiptItems, ((Receipt) receipt).getItems())
                && Objects.equals(targetDiscounts, ((Receipt) receipt).getDiscounts());
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        Receipt receipt= (Receipt)item;
        description.appendText("Receipt doesn't match total price " + receipt.getTotalPrice()).appendText(" and " + receipt.getItems()).appendText(" and " + receipt.getDiscounts());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Receipt doesn't match total price " + targetTotalPrice).appendText(" and " + targetReceiptItems).appendText(" and " + targetDiscounts);
    }
}