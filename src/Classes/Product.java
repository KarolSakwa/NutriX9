package Classes;

public class Product {
    private String name, macronutrientCategory, category, unitType, unitQuantity;
    private Double kcal;
    private Double proteins;
    private Double carbohydrates;
    private Double fats;
    private Double price;


    private Double quantity;
    private Integer wholesomenessIndex;
    public final String kcalAbbr = "K";
    public final String proteinsAbbr = "P";
    public final String carbsAbbr = "C";
    public final String fatsAbbr = "F";
    public final String priceAbbr = "zł";


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
        return carbohydrates;
    }

    public void setCarbs(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public String getMacronutrientCategory() {
        return macronutrientCategory;
    }

    public void setMacronutrientCategory(String macroNutrientCategory) {
        this.macronutrientCategory = macroNutrientCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getWholesomenessIndex() {
        return wholesomenessIndex;
    }

    public void setWholesomenessIndex(Integer wholesomenessIndex) {
        this.wholesomenessIndex = wholesomenessIndex;
    }

    public String getName() {
        return name;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getUnitType() { return unitType; }

    public void setUnitQuantity(String unitQuantity) { this.unitQuantity = unitQuantity; }

    public String getUnitQuantity() { return unitQuantity; }

    public String getShorterUnit() {
        switch (unitType) {
            case "gram":
                return "gr";
            case "milliliter":
                return "ml";
            case "unit":
                return "U";
            default:
                return "";
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Product(String name, Double kcal, Double proteins, Double carbohydrates, Double fats, String macronutrientCategory, String category, Integer wholesomenessIndex, String unitType, String unitQuantity, Double price) {
        this.name = name;
        this.kcal = kcal;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.macronutrientCategory = macronutrientCategory;
        this.category = category;
        this.wholesomenessIndex = wholesomenessIndex;
        this.unitType = unitType;
        this.unitQuantity = unitQuantity;
        this.price = price;
    }
}
