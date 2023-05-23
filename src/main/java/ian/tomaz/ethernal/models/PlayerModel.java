package ian.tomaz.ethernal.models;

public class PlayerModel {

    private String uuid;
    private String name;
    private String race;
    private double balance;
    private String profession;
    private String regions;

    public PlayerModel(String uuid, String name, String race, double balance, String profession, String regions) {
        this.uuid = uuid;
        this.name = name;
        this.race = race;
        this.balance = balance;
        this.profession = profession;
        this.regions = regions;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRegions() {
        return regions;
    }

    public void setRegions(String regions) {
        this.regions = regions;
    }
}
