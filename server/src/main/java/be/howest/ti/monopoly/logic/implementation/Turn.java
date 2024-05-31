package be.howest.ti.monopoly.logic.implementation;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Turn {

    private int[] roll;
    private String player;
    private String type;
    private Map<String , String> moves; // waar je bent + waar je gaat

    private final Random rand = new SecureRandom();

    public Turn(String playerName) {
        this.roll = rollDices();
        this.player = playerName;
        this.type = "DEFAULT"; // implement type of turn
        this.moves = new HashMap<>(); // contains title of location + description (current and next tile)
    }

    public int[] getRoll() {
        return roll;
    }

    public String getPlayer() {
        return player;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getMoves() {
        return moves;
    }

    public void addMove(BaseTile tile, Player player) {
        String tileType = tile.getType();

        String tileDescription = "";
        switch (tileType)
        {
            case "Chance": {
                // Get random chance card from the list
                int listSize = ChanceTile.getChance().size();
                int randomCard = this.rand.nextInt(listSize);
                tileDescription = ChanceTile.getChance().get(randomCard);
                ChanceTile chanceTile = (ChanceTile) tile;
                chanceTile.carryOutDescription(player, tileDescription);
                break;
            }
            case "Community Chest": {
                // Get random community chest card from the list
                int listSize = CommunityChestTile.getCommunityChest().size();
                int randomCard = this.rand.nextInt(listSize);
                tileDescription = CommunityChestTile.getCommunityChest().get(randomCard);
                CommunityChestTile communityChestTile = (CommunityChestTile) tile;
                communityChestTile.carryOutDescription(player, tileDescription);
                break;
            }
            case "Street":
                tileDescription = "can buy this property in direct sale";
                break;
            case "Jail":
                tileDescription = "Just visiting.";
                break;
            default:
                tileDescription = "";
                break;
        }

        moves.put("tile", tile.getName());
        moves.put("description", tileDescription);
    }

    public int[] rollDices() {
        int[] dices = new int[2];
        dices[0] = this.rand.nextInt(6) + 1;    // 0-5 => 1-6
        dices[1] = this.rand.nextInt(6) + 1;    // 0-5 => 1-6

        return dices;
    }

    public int calculateNewTilePosition(int position, Player player) {
        int newPosition = position + (getRoll()[0] + getRoll()[1]);
        if (newPosition >= 40) {
            player.addMoney(200);
            newPosition -= 40;
        }
        return newPosition;
    }

    public Boolean calculateCanRoll(int[] roll){
        return roll[0] == roll[1];
    }
}
