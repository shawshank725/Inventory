package bro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;

    @NotEmpty(message = "Name can't be null.")
    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    private float price;

    @Column(name = "quantity")
    private int quantity;

    @NotEmpty(message = "Details are necessary.")
    @Column(name = "details")
    private String details;

    @Column(name = "item_url")
    private String imageURL;

    @Column(name = "type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity seller;

    @Column(name = "active")
    private Boolean active = true;

}
