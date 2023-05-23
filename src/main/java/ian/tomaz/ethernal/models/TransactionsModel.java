package ian.tomaz.ethernal.models;

public class TransactionsModel {

    private String uuid;
    private String uuidTarget;
    private double value;

    public TransactionsModel(String uuid, String uuidTarget, double value) {
        this.uuid = uuid;
        this.uuidTarget = uuidTarget;
        this.value = value;
    }

    public TransactionsModel(String uuid, double value) {
        this.uuid = uuid;
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidTarget() {
        return uuidTarget;
    }

    public void setUuidTarget(String uuidTarget) {
        this.uuidTarget = uuidTarget;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
