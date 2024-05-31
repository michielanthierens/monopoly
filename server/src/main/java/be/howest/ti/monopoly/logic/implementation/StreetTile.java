package be.howest.ti.monopoly.logic.implementation;

public class StreetTile extends Property {

    private final int rentWithOneHouse;
    private final int rentWithTwoHouses;
    private final int rentWithThreeHouses;
    private final int rentWithFourHouses;
    private final int rentWithHotel;
    private final int housePrice;
    private final String streetColor;
    private int amountOfHouses;

    public StreetTile(int position, String name, int cost, int mortgage, int rent, String color, int rentWithOneHouse, int rentWithTwoHouses, int rentWithThreeHouses, int rentWithFourHouses, int rentWithHotel, int housePrice, int amountOfHouses, int groupsize) {
        super(position, name, "street", cost, mortgage, rent, color, groupsize);
        this.rentWithOneHouse = rentWithOneHouse;
        this.rentWithTwoHouses = rentWithTwoHouses;
        this.rentWithThreeHouses = rentWithThreeHouses;
        this.rentWithFourHouses = rentWithFourHouses;
        this.rentWithHotel = rentWithHotel;
        this.housePrice = housePrice;
        this.streetColor = color;
        this.amountOfHouses = amountOfHouses;
    }

    @Override
    public int calculateRent() {
        switch (this.amountOfHouses) {
            case 0:
                return super.getRent();
            case 1:
                return rentWithOneHouse;
            case 2:
                return rentWithTwoHouses;
            case 3:
                return rentWithThreeHouses;
            case 4:
                return rentWithFourHouses;
            case 5:
                return rentWithHotel;
        }
        throw new IllegalStateException("rent can not be calculated, is the amount of houses >= 0 and < 5?");
    }

    public int getRentWithOneHouse() {
        return rentWithOneHouse;
    }

    public int getRentWithTwoHouses() {
        return rentWithTwoHouses;
    }

    public int getRentWithThreeHouses() {
        return rentWithThreeHouses;
    }

    public int getRentWithFourHouses() {
        return rentWithFourHouses;
    }

    public int getRentWithHotel() {
        return rentWithHotel;
    }

    public String getStreetColor() {
        return streetColor;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public void incr(){
        amountOfHouses++;
    }

    public void decr(){
        amountOfHouses--;
    }

    public int getAmountOfHouses(){
        return amountOfHouses;
    }


}
