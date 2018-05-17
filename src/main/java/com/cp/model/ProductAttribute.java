package com.cp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * ProductAttribute.java
 *
 * @author ManhTung
 * @version 2/28/18.
 */
@Entity
@Table(name = "product_attributes")
public class ProductAttribute {
    @Id
    @GeneratedValue
    @Column(name = "product_attribute_id")
    private int productAttributeId;

    @Column(name = "attribute_id")
    private int attributeId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "attribute_value")
    @NotNull
    @Size(min = 1, max = 200)
    private String attributeValue;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "attribute_id", referencedColumnName = "attribute_id", insertable = false, updatable = false)
    private Attribute attribute;

    public ProductAttribute() {
    }

    public ProductAttribute(int productAttributeId, int attributeId, int productId, String attributeValue) {
        this.productAttributeId = productAttributeId;
        this.attributeId = attributeId;
        this.productId = productId;
        this.attributeValue = attributeValue;
    }

    public int getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(int productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
}
