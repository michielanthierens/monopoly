package be.howest.ti.monopoly.logic.implementation;

import java.util.List;

public class UtilTile extends Property{

    public UtilTile(int position, String name, int cost, int mortgage, int rent, String color) {
        super(position, name, "utility", cost, mortgage, rent, color, 2);
    }

    @Override
    public int calculateRent() {
        int amountOwned = calculateAmountOfUtilsOwned(this);
        switch (amountOwned) {
            case 0:
                throw new IllegalStateException("player does not have any utility tiles");
            case 1:
                return (4 * 250);
            case 2:
                return (10 * 50);
        }
        throw new IllegalStateException("player has too many utility tiles");
    }

    public int calculateAmountOfUtilsOwned(Property property) {
        Player owner = property.getOwner();
        List<BaseTile> listOfProperties = owner.getProperties();
        int amountOfUtils = 0;
        for (BaseTile tile : listOfProperties) {
            if (tile.getType().equals("utility")) {
                amountOfUtils++;
            }
        }
        return amountOfUtils;
    }
}
