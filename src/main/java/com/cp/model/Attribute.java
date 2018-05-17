package com.cp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Attribute.java
 *
 * @author ManhTung
 * @version 2/28/18.
 */
@Entity
@Table(name = "attributes")
public class Attribute {
    @Id
    @GeneratedValue
    @Column(name = "attribute_id")
    private int attributeId;

    @Column(name = "attribute_name")
    @NotNull
    @Size(min = 1, max = 100)
    private String attributeName;

    @Column(name = "category_id")
    private int categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private Category category;

    public Attribute() {
    }

    public Attribute(int attributeId, String attributeName, int categoryId) {
        this.attributeId = attributeId;
        this.attributeName = attributeName;
        this.categoryId = categoryId;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
