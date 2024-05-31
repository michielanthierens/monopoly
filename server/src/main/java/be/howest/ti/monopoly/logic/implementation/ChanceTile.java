package be.howest.ti.monopoly.logic.implementation;

import java.util.List;

public class ChanceTile extends BaseTile {

    public ChanceTile(int position, String name) {
        super(position, name, "Chance", 0);
    }

    public static List<String> getChance() {
        return List.of(
                "you won the lottery, Collect 200xp",
                "Bank pays you dividend of 50xp",
                "Get Out of the End for Free",
                "Speeding fine 15xp",
                "You have been elected Chairman of the Board. Pay 50xp",
                "Your building loan matures. Collect 150xp"
        );
    }

    public void carryOutDescription(Player player, String description) {
        switch (description) {
            case "you won the lottery, Collect 200xp":
                player.addMoney(200);
                break;
            case "Bank pays you dividend of 50xp":
                player.addMoney(50);
                break;
            case "Get Out of the End for Free":
                player.addGetOutOfJailFreeCards();
                break;
            case "Speeding fine 15xp":
                player.withdrawMoney(15);
                break;
            case "You have been elected Chairman of the Board. Pay 50xp":
                player.withdrawMoney(50);
                break;
            case "Your building loan matures. Collect 150xp":
                player.addMoney(150);
                break;
            default:
                player.addMoney(0);
        }
    }
}
