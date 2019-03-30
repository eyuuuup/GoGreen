package server;

public class OnLoadValues {

    private boolean electricCar;
    private boolean solarCar;

    public OnLoadValues(boolean electricCar, boolean solarCar) {
        this.electricCar = electricCar;
        this.solarCar = solarCar;
    }

    public OnLoadValues()
    {}

    public boolean isElectricCar() {
        return electricCar;
    }

    public boolean isSolarCar() {
        return solarCar;
    }

    public void setElectricCar(boolean electricCar) {
        this.electricCar = electricCar;
    }

    public void setSolarCar(boolean solarCar) {
        this.solarCar = solarCar;
    }

}
