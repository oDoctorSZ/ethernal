package ian.tomaz.ethernal.models;

public class RegionModel {

    private String name;
    private double tax;
    private double price;
    private boolean occupied;
    private String payment;
    private String owner_uuid;
    private String members;

    public RegionModel(String name, double tax, double price, boolean occupied, String payment, String owner_uuid, String members) {
        this.name = name;
        this.tax = tax;
        this.price = price;
        this.occupied = occupied;
        this.payment = payment;
        this.owner_uuid = owner_uuid;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getOwner_uuid() {
        return owner_uuid;
    }

    public void setOwner_uuid(String owner_uuid) {
        this.owner_uuid = owner_uuid;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
