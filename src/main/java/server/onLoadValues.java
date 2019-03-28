package server;

public class onLoadValues {

    private boolean electricCar;
    private boolean solarCar;

    public onLoadValues(boolean electricCar, boolean solarCar)
    {
        this.electricCar=electricCar;
        this.solarCar=solarCar;
    }

    public onLoadValues()
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
