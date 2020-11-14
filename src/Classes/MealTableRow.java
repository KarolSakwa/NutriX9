package Classes;

import javafx.scene.layout.Pane;

public class MealTableRow {
    private Double quantity, kcal, proteins, carbs, fats, price;
    private String name;
    private Integer WI, rowIndex;
    private Boolean isEmpty;

    public void create(MealTable mealTable, Product product, Double quantity, Integer rowNum, Pane paneName) {
        System.out.println("Row" + mealTable.currentProductsNum + "added");
    }
    public void delete(MealTable mealTable, Pane paneName, MealTableRow mealTableRow) {
        System.out.println("Row " + mealTable.currentProductsNum + " deleted");
        //paneName.getChildren().remove(mealTableRow);
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getKcal() {
        return kcal;
    }

    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    public Double getProteins() {
        return proteins;
    }

    public void setProteins(Double proteins) {
        this.proteins = proteins;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWI() {
        return WI;
    }

    public void setWI(Integer WI) {
        this.WI = WI;
    }

    public Integer getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(Integer rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }
}
