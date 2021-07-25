package ucf.assignments;

public class Item {
    String name;
    String serial;
    double value;

    public Item(String name, String serial, double value) {
        this.name = name;
        this.serial = serial;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
