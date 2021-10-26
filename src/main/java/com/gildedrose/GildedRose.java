package com.gildedrose;


class GildedRose {
    Item[] items;
    static final int DELTA = 1;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            StrategyItem strategyItem = new StrategyItem(items[i]);
            strategyItem.updateSellIn(DELTA);
            strategyItem.updateQuality(DELTA);
            items[i] = strategyItem.getAsItem();
        }
    }
}
