package be.howest.ti.monopoly.logic.implementation;

import java.util.List;

public class CommunityChestTile extends BaseTile {

    public CommunityChestTile(int position, String name) {
        super(position, name, "Community Chest", 0);
    }

    public static List<String> getCommunityChest() {
        return List.of(
                "Bank error in your favor. Collect 200xp",
                "Doctor's fee. Pay 50xp",
                "From sale of stock you get 50xp",
                "Get Out of the End Free",
                "Holiday fund matures. Receive 100xp",
                "Income tax refund. Collect 20xp",
                "Life insurance matures. Collect 150xp",
                "Pay hospital fees of 100xp",
                "Pay school fees of 50xp",
                "Receive 25xp consultancy fee ",
                "You have won second prize in a beauty contest. Collect 10xp",
                "You inherit 100xp"
        );
    }

    public void carryOutDescription(Player player, String description) {
        switch (description) {
            case "Bank error in your favor. Collect 200xp":
                player.addMoney(200);
                break;
            case "Doctor's fee. Pay 50xp":
            case "Pay school fees of 50xp":
                player.withdrawMoney(50);
                break;
            case "From sale of stock you get 50xp":
                player.addMoney(50);
                break;
            case "Get Out of the End Free":
                player.addGetOutOfJailFreeCards();
                break;
            case "Holiday fund matures. Receive 100xp":
            case "You inherit 100xp":
                player.addMoney(100);
                break;
            case "Income tax refund. Collect 20xp":
                player.addMoney(20);
                break;
            case "Life insurance matures. Collect 150xp":
                player.addMoney(150);
                break;
            case "Pay hospital fees of 100xp":
                player.withdrawMoney(100);
                break;
            case "Receive 25xp consultancy fee ":
                player.addMoney(25);
                break;
            case "You have won second prize in a beauty contest. Collect 10xp":
                player.addMoney(10);
                break;
            default:
                player.addMoney(0);
        }
    }
}
