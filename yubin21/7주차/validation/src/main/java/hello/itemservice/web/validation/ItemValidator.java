package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz); // 해당 문장을 통해 자식 클래스까지 커버할 수 있다. item == clazz 와 item == subItem

    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;
        // 검증 로직
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required"); // 단순한 기능은 이렇게 변경 가능.

        if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
    }
}
