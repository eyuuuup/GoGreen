package server;

/**
 * This class is for the on load values.
 */
public class OnLoadValues {

    private boolean electricCar;
    private boolean solarCar;

    /**
     * Constructor.
     *
     * @param electricCar
     * @param solarCar
     */

    public OnLoadValues(boolean electricCar, boolean solarCar) {
        this.electricCar = electricCar;
        this.solarCar = solarCar;
    }


    /**
     * Empty constructor.
     */
    public OnLoadValues() {
    }


    /**
     * Getter for electric car.
     *
     * @return if present or not
     */
    public boolean isElectricCar() {
        return electricCar;
    }


    /**
     * setter for solarcCar.
     *
     * @return if presernt or not
     */
    public boolean isSolarCar() {
        return solarCar;
    }


    /**
     * Settter for electricCar.
     *
     * @param electricCar
     */
    public void setElectricCar(boolean electricCar) {
        this.electricCar = electricCar;
    }


    /**
     * Setter for SolarCar.
     *
     * @param solarCar
     */
    public void setSolarCar(boolean solarCar) {
        this.solarCar = solarCar;
    }

}
