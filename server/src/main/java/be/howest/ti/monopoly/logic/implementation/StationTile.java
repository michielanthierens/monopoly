package be.howest.ti.monopoly.logic.implementation;

import java.util.List;

public class StationTile extends Property{

    public StationTile(int position, String name, int cost, int mortgage, int rent, String color) {
        super(position, name, "railroad", cost, mortgage, rent, color, 4);
    }


    @Override
    public int calculateRent() {
        Player owner = super.getOwner();
        List<BaseTile> listOfProperties = owner.getProperties();
        int totalRent = super.getRent();
        for (BaseTile tile : listOfProperties) {
            if (tile.getType().equals("railroad")) {
                totalRent = totalRent * 2;
            }
        }
        return totalRent;
    }
}
