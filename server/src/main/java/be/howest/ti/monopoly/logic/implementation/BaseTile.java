package be.howest.ti.monopoly.logic.implementation;

import java.util.Objects;

public class BaseTile{

    private int position;
    private String name;
    private String type;
    private int cost;

    public BaseTile(int position, String name, String type, int cost) {
        this.position = position;
        this.name = name;
        this.type = type;
        this.cost = cost;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getNameAsPathParameter() {
       return getName().replace(' ', '_');
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTile baseTile = (BaseTile) o;
        return position == baseTile.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    public int getCost(){
        return cost;
    }
}
