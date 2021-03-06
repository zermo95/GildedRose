package fi.oulu.tol.sqat;

import java.util.ArrayList;
import java.util.List;


public class GildedRose {

    private static List<Item> items = null;

    public static List<Item> getItems() {
        return items;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("OMGHAI!");

        items = new ArrayList<>();
        items.add(new Item("+5 Dexterity Vest", 10, 20));
        items.add(new Item("Aged Brie", 2, 0));
        items.add(new Item("Elixir of the Mongoose", 5, 7));
        items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        items.add(new Item("Conjured Mana Cake", 3, 6));
        items.add(new Item("SellInPassed", -1, 6));

        //updateQuality();
    }


    public static void updateQuality() throws ItemException {

        for (Item item : items) {
            // The Quality of an item is never negative
            if (item.getQuality() < 0) {
                throw new ItemException();
            }

            String itemName = item.getName();
            switch (itemName) {
                // Aged Brie actually increases in Quality the older it gets (initialSellIn = 2)
                case "Aged Brie":
                    int daysSpent = -(item.getSellIn() - 2);
                    item.setQuality(item.getQuality() + daysSpent);
                    break;

                // Sulfuras, being a legendary item, never has to be sold or decreases in Quality
                case "Sulfuras, Hand of Ragnaros":
                    break;

                case "Backstage passes to a TAFKAL80ETC concert":
                    //Quality drops to 0 after the concert
                    if (item.getSellIn() <= 0) {
                        item.setQuality(0);
                        break;
                    }
                    // Quality increases by 3 when there are 5 days or less
                    if (item.getSellIn() <= 5) {
                        item.setQuality(item.getQuality() + 3);
                        break;
                    }
                    // Quality increases by 2 when there are 10 days or less
                    if (item.getSellIn() <= 10) {
                        item.setQuality(item.getQuality() + 2);
                        break;
                    }
                    break;
                // Conjured items degrade in Quality twice as fast as normal items
                case "Conjured Mana Cake":
                    if (item.getSellIn() < 0) {
                        degradeQuality(item, 4);
                    }
                    fixQualityLessThanZero(item);
                    break;

                default:
                    // Once the SellIn date has passed, Quality degrades twice as fast
                    if (item.getSellIn() < 0) {
                        degradeQuality(item, 2);
                    }
                    fixQualityLessThanZero(item);
                    break;
            }
        }
    }

    private static void degradeQuality(Item item, int numberOfTimes) {
        if (item.getSellIn() < 0) {
            item.setQuality(item.getQuality() + (numberOfTimes * item.getSellIn()));
        }
    }

    private static void fixQualityLessThanZero(Item item) {
        if (item.getQuality() < 0)
            item.setQuality(0);
    }

}
