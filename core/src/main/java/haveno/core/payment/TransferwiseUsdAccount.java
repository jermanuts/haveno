/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package haveno.core.payment;

import haveno.core.api.model.PaymentAccountFormField;
import haveno.core.locale.TraditionalCurrency;
import haveno.core.locale.Res;
import haveno.core.locale.TradeCurrency;
import haveno.core.payment.payload.PaymentAccountPayload;
import haveno.core.payment.payload.PaymentMethod;
import haveno.core.payment.payload.TransferwiseUsdAccountPayload;
import lombok.EqualsAndHashCode;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public final class TransferwiseUsdAccount extends CountryBasedPaymentAccount {

    public static final List<TradeCurrency> SUPPORTED_CURRENCIES = List.of(new TraditionalCurrency("USD"));

    private static final List<PaymentAccountFormField.FieldId> INPUT_FIELD_IDS = List.of(
            PaymentAccountFormField.FieldId.EMAIL,
            PaymentAccountFormField.FieldId.HOLDER_NAME,
            PaymentAccountFormField.FieldId.HOLDER_ADDRESS,
            PaymentAccountFormField.FieldId.ACCOUNT_NAME,
            PaymentAccountFormField.FieldId.COUNTRY,
            PaymentAccountFormField.FieldId.SALT
    );

    public TransferwiseUsdAccount() {
        super(PaymentMethod.TRANSFERWISE_USD);
        // this payment method is currently restricted to United States/USD
        setSingleTradeCurrency(SUPPORTED_CURRENCIES.get(0));
    }

    @Override
    protected PaymentAccountPayload createPayload() {
        return new TransferwiseUsdAccountPayload(paymentMethod.getId(), id);
    }

    public void setEmail(String email) {
        ((TransferwiseUsdAccountPayload) paymentAccountPayload).setEmail(email);
    }

    public String getEmail() {
        return ((TransferwiseUsdAccountPayload) paymentAccountPayload).getEmail();
    }

    public void setHolderName(String accountId) {
        ((TransferwiseUsdAccountPayload) paymentAccountPayload).setHolderName(accountId);
    }

    public String getHolderName() {
        return ((TransferwiseUsdAccountPayload) paymentAccountPayload).getHolderName();
    }

    public void setBeneficiaryAddress(String address) {
        ((TransferwiseUsdAccountPayload) paymentAccountPayload).setHolderAddress(address);
    }

    public String getBeneficiaryAddress() {
        return ((TransferwiseUsdAccountPayload) paymentAccountPayload).getHolderAddress();
    }

    @Override
    public String getMessageForBuyer() {
        return "payment.transferwiseUsd.info.buyer";
    }

    @Override
    public String getMessageForSeller() {
        return "payment.transferwiseUsd.info.seller";
    }

    @Override
    public String getMessageForAccountCreation() {
        return "payment.transferwiseUsd.info.account";
    }

    @Override
    public @NotNull List<TradeCurrency> getSupportedCurrencies() {
        return SUPPORTED_CURRENCIES;
    }

    @Override
    public @NotNull List<PaymentAccountFormField.FieldId> getInputFieldIds() {
        return INPUT_FIELD_IDS;
    }

    @Override
    protected PaymentAccountFormField getEmptyFormField(PaymentAccountFormField.FieldId fieldId) {
        var field = super.getEmptyFormField(fieldId);
        if (field.getId() == PaymentAccountFormField.FieldId.HOLDER_ADDRESS) field.setLabel(field.getLabel() + " " + Res.get("payment.transferwiseUsd.address"));
        return field;
    }
}
