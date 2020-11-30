package Classes;

public class Product {
    private String name, macronutrientCategory, category, unitType, unitQuantity;
    private Float kcal;
    private Float proteins;
    private Float carbs;
    private Float fats;
    private Float price;


    private Float quantity;
    private Integer wholesomenessIndex;
    public final String kcalAbbr = "K";
    public final String proteinsAbbr = "P";
    public final String carbsAbbr = "C";
    public final String fatsAbbr = "F";
    public final String priceAbbr = "zł";


    public Float getKcal() {
        return kcal;
    }

    public void setKcal(Float kcal) {
        this.kcal = kcal;
    }

    public Float getProteins() {
        return proteins;
    }

    public void setProteins(Float proteins) {
        this.proteins = proteins;
    }

    public Float getCarbs() {
        return carbs;
    }

    public void setCarbs(Float carbs) {
        this.carbs = carbs;
    }

    public Float getFats() {
        return fats;
    }

    public void setFats(Float fats) {
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Product(String name, Float kcal, Float proteins, Float carbs, Float fats, String macronutrientCategory, String category, Integer wholesomenessIndex, String unitType, String unitQuantity, Float price) {
        this.name = name;
        this.kcal = kcal;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
        this.macronutrientCategory = macronutrientCategory;
        this.category = category;
        this.wholesomenessIndex = wholesomenessIndex;
        this.unitType = unitType;
        this.unitQuantity = unitQuantity;
        this.price = price;
    }
}
