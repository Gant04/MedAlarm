package concentric.medalarm;

// TODO: Is this needed?
/**
 * Created by Thom on 6/10/2015.
 */
public class AlarmPreferences {
    private boolean isRepeatable;
    private boolean[] daysToRepeat = new boolean[7];

    public boolean isRepeatable() {
        return isRepeatable;
    }

    /**
     *
     * @param isRepeatable
     */
    public void setIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    public boolean[] getDaysToRepeat() {
        return daysToRepeat;
    }

    /**
     *
     * @param daysToRepeat
     */
    public void setDaysToRepeat(boolean[] daysToRepeat) {
        this.daysToRepeat = daysToRepeat;
    }
}
