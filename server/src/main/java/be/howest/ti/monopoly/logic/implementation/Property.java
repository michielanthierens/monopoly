package be.howest.ti.monopoly.logic.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Property extends BaseTile{

    private final int cost;
    private final int mortgage;
    private final String color;
    private final int rent;
    private final int groupSize;
    private Player owner;

    public Property(int position, String name, String type, int cost, int mortgage, int rent, String color, int groupSize) {
        super(position, name, type, cost);
        this.cost = cost;
        this.mortgage = mortgage;
        this.rent = rent;
        this.groupSize = groupSize;
        this.color = color;
    }

    public void setOwner(Player player){
        this.owner = player;
    }

    public int getCost() {
        return cost;
    }

    public int getMortgage() {
        return mortgage;
    }

    public int getRent() {
        return rent;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public String getColor() {
        return color;
    }

    @JsonIgnore
    public Player getOwner() {
        return owner;
    }

    public boolean hasOwner() {
        return owner != null;
    }

    protected abstract int calculateRent();

}
