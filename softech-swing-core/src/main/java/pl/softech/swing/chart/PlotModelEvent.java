package pl.softech.swing.chart;

public class PlotModelEvent {

    public enum Type { DATA_CHANGE }
    
    private Type type;

    public PlotModelEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
    
}
