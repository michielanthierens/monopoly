package be.howest.ti.monopoly.logic.implementation;

import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements Comparable<Player>{

    private String name;
    private int money;
    private boolean jailed;
    private boolean bankrupt;
    private int getOutOfJailFreeCards;
    private String taxSystem;
    private List<BaseTile> properties;
    private int debt;
    private String currentTile;
    private boolean alreadyPaidDebt;


    public Player(String name) {
        this.name = name;
        this.money = 1500;
        this.jailed = false;
        this.bankrupt = false;
        this.getOutOfJailFreeCards = 0;
        this.debt = 0;
        properties = new ArrayList<>();
        this.currentTile = "Spawner";
        this.taxSystem = "ESTIMATE";
        alreadyPaidDebt = false;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public String getCurrentTile() {
        return currentTile;
    }

    public boolean isJailed() {
        return jailed;
    }

    public void setJailed(boolean jailed) {
        this.jailed = jailed;
    }

    public boolean isBankrupt() {
        return bankrupt;
    }

    public int getGetOutOfJailFreeCards() {
        return getOutOfJailFreeCards;
    }

    public void addGetOutOfJailFreeCards() {
        this.getOutOfJailFreeCards++;
    }

    public void useGetOutOfJailFreeCards() {
        if (getGetOutOfJailFreeCards() > 0){
            this.getOutOfJailFreeCards--;
        }
        else throw new IllegalStateException("this player has no get-out-of-jail cards!");
    }

    public String getTaxSystem() {
        return taxSystem;
    }

    public List<BaseTile> getProperties() {
        return properties;
    }

    public void addProperty(BaseTile tile){
        properties.add(tile);
    }

    public int getDebt() {
        return debt;
    }

    public void setCurrentTile(String currentTile) {
        this.currentTile = currentTile;
    }

    public void addMoney(int amount){
        if(amount < 0){
            throw new IllegalArgumentException("amount cannot be negative");
        }
        else{
            money += amount;
        }
    }

    public void withdrawMoney(int amount){
        if(amount < 0 || amount > this.getMoney()){
            throw new IllegalArgumentException("amount cannot be negative, or player:" + this.getName() +  " does not have enough xp");
        }
        else{
            money -= amount;
        }
    }

    public void changeTaxSystem(String taxSystem){
        if(taxSystem.equals("ESTIMATE")){
            this.taxSystem = "ESTIMATE";
        }
        else if(taxSystem.equals("COMPUTE")){
            this.taxSystem = "COMPUTE";
        }
        else{
            throw new IllegalArgumentException("the tax system can only be ESTIMATE or COMPUTE");
        }
    }

    public void setBankrupt() {
        this.bankrupt = true;
    }

    public void setJailed(Boolean jailed) {
        this.jailed = jailed;
    }

    public void deleteProperties(){
        properties.clear();
    }

    public int calculateTotalNetWorth(){
        int total = getMoney();

        for(BaseTile tile : getProperties()){
            total += tile.getCost();
        }

        return total;
    }

    public int getTotalNetWorth() {
        return calculateTotalNetWorth();
    }

    public boolean hasProperty(String propertyName){
        for(BaseTile baseTile : getProperties()){
            if(propertyName.equals(baseTile.getNameAsPathParameter())){
                return true;
            }
        }
        return false;
    }

    public BaseTile getStreetTile(String propertyName){
        for(BaseTile streetTile : getProperties())
        {
            if(propertyName.equals(streetTile.getName()))
            {
                return streetTile;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare( o.calculateTotalNetWorth(), calculateTotalNetWorth());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return money == player.money && jailed == player.jailed && bankrupt == player.bankrupt && getOutOfJailFreeCards == player.getOutOfJailFreeCards && debt == player.debt && alreadyPaidDebt == player.alreadyPaidDebt && name.equals(player.name) && Objects.equals(taxSystem, player.taxSystem) && Objects.equals(properties, player.properties) && currentTile.equals(player.currentTile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, money, jailed, bankrupt, getOutOfJailFreeCards, taxSystem, properties, debt, currentTile, alreadyPaidDebt);
    }

    public boolean getAlreadyPaidDebt() {
        return alreadyPaidDebt;
    }

    public void setAlreadyPaidDebt(boolean b) {
        alreadyPaidDebt = b;
    }
}
