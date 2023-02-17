package hello.itemservice.domain.item;

import lombok.Data;

@Data // @Data는 주의할 필요가 있다. 반드시 상세 내용을 알고 써야하며, 대부분은 @Getter @Setter 를 쓰는 것이 안전하다.
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }


}
