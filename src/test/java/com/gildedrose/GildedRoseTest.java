package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GildedRoseTest {

    @Test
    void foo() {
        String name = "foo";
        Item[] items = new Item[]{new Item(name, 0, 0)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(name, app.items[0].name);
    }

    @Test
    void sellInDegrade(){
        int sellIn=12;
        Item[] items = new Item[]{new Item("foo-zero", sellIn, 10),};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(sellIn - app.DELTA, items[0].sellIn, "SellIn Didn't Degrade!");
    }

    @Test
    void sellInConstantForLegenderyItems(){
        int sellIn=12;
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", sellIn, 10),};
        GildedRose app = new GildedRose(items);

        app.updateQuality();

        assertEquals(sellIn, items[0].sellIn, "SellIn Did Changed For Legendary Item!");
    }


    @Test
        // Once the sell by date has passed, Quality degrades twice as fast
    void qualityDegradesTwiceWhenSellInEqualsSmallerThanZero() {


        int quality = 12;
        Item[] items = new Item[]{new Item("foo-zero", 0, quality),
            new Item("foo-negative", -1, quality)};
        GildedRose app = new GildedRose(items);


        app.updateQuality();


        assertEquals(quality - 2 * app.DELTA, items[0].quality, "Item With Zero SellIn Doesn't Decrement Twice ");
        assertEquals(quality - 2 * app.DELTA, items[1].quality, "Item With Negative SellIn Doesn't Decrement Twice ");
    }

    @Test
        // Once the sell by date has passed, Quality degrades twice as fast
    void qualityDegradesOnceWhenSellInGreaterThanZero() {


        int quality = 12;
        Item[] items = new Item[]{new Item("foo-zero", 2, quality)};
        GildedRose app = new GildedRose(items);


        app.updateQuality();


        assertEquals(quality - app.DELTA, items[0].quality, "Item With Positive SellIn Doesn't Decrement Once ");
    }


    @Test
        // The Quality of an item is never negative
    void qualityIsNeverNegative() {


        Item[] items = new Item[]{new Item("foo-zero", 0, 2),
            new Item("foo-zero", 3, 1)};

        GildedRose app = new GildedRose(items);


        app.updateQuality();
        app.updateQuality();
        app.updateQuality();

        assertTrue(items[0].quality >= 0, "Item have a negative value");
        assertTrue(items[1].quality >= 0, "Item have a negative value");
    }

    @Test
        // "Aged Brie" actually increases in Quality the older it gets
    void agedBrieQualityIncreaseWithTime() {


        int quality = 2;
        Item[] items = new Item[]{new Item("Aged Brie", 2, quality),
            new Item("Aged Brie", -1, quality),
            new Item("Aged Brie", 0, quality),
        };

        GildedRose app = new GildedRose(items);


        app.updateQuality();


        assertTrue(items[0].quality > quality, "Aged Brie Quality Didn't Increase");
        assertTrue(items[1].quality > quality, "Aged Brie Quality Didn't Increase");
        assertTrue(items[2].quality > quality, "Aged Brie Quality Didn't Increase");
    }


    @Test
        // The Quality of an item is never more than 50
    void qualityOfItemEverLessThan50() {


        int quality = 50;
        Item[] items = new Item[]{new Item("Aged Brie", 2, quality),
            new Item("Aged Brie", -1, quality),
            new Item("Aged Brie", 0, quality),
            new Item("Backstage passes to a TAFKAL80ETC concert", 2, quality),
            new Item("Backstage passes to a TAFKAL80ETC concert", -1, quality),
            new Item("Backstage passes to a TAFKAL80ETC concert", 0, quality),
            new Item("Sulfuras, Hand of Ragnaros", 2, quality),
            new Item("Sulfuras, Hand of Ragnaros", -1, quality),
            new Item("Sulfuras, Hand of Ragnaros", 0, quality),
            new Item("Conjured Mana Cake", 2, quality),
            new Item("Conjured Mana Cake", -1, quality),
            new Item("Conjured Mana Cake", 0, quality),
        };

        GildedRose app = new GildedRose(items);


        app.updateQuality();
        app.updateQuality();


        assertTrue(items[0].quality <= 50, "Item Quality More Than 50");
        assertTrue(items[1].quality <= 50, "Item Quality More Than 50");
        assertTrue(items[2].quality <= 50, "Item Quality More Than 50");
    }

    @Test
        // "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
    void sulfurasNeverDecreaseInQuality() {

        int quality = 2;
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 2, quality),
            new Item("Sulfuras, Hand of Ragnaros", 0, quality),
            new Item("Sulfuras, Hand of Ragnaros", -1, quality),
        };

        GildedRose app = new GildedRose(items);


        app.updateQuality();


        assertEquals(quality, items[0].quality, "The Legendary Sulfuras Decreased in Quality!");
        assertEquals(quality, items[1].quality, "The Legendary Sulfuras Decreased in Quality!");
        assertEquals(quality, items[2].quality, "The Legendary Sulfuras Decreased in Quality!");
    }

    @Test
    void backstagePassesQualityIncreaseWhenSellInApproaches() {


        int quality = 10;
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 12, quality),

            new Item("Backstage passes to a TAFKAL80ETC concert", 10, quality),
            new Item("Backstage passes to a TAFKAL80ETC concert", 9, quality),

            new Item("Backstage passes to a TAFKAL80ETC concert", 5, quality),
            new Item("Backstage passes to a TAFKAL80ETC concert", 4, quality),
        };

        GildedRose app = new GildedRose(items);


        app.updateQuality();



        assertEquals( quality, items[0].quality, "Backstage passes Quality Increased While Concert Haven't Approached");
        assertEquals(quality + 2 * app.DELTA, items[1].quality, "Backstage passes Quality Didn't Increase With Time x2");
        assertEquals(quality + 2 * app.DELTA, items[2].quality, "Backstage passes Quality Didn't Increase With Time x2");
        assertEquals(quality + 3 * app.DELTA, items[3].quality, "Backstage passes Quality Didn't Increase With Time x3");
        assertEquals(quality + 3 * app.DELTA, items[4].quality, "Backstage passes Quality Didn't Increase With Time x3");
    }

    @Test
    void backstagePassesQualityZerosAfterConcert() {


        int quality = 10;
        Item[] items = new Item[]{
            new Item("Backstage passes to a TAFKAL80ETC concert", 0, quality),
            new Item("Backstage passes to a TAFKAL80ETC concert", -1, quality),
        };

        GildedRose app = new GildedRose(items);


        app.updateQuality();


        assertEquals(0, items[0].quality, "Backstage passes Quality Didn't Zero After Concert");
        assertEquals(0, items[1].quality, "Backstage passes Quality Didn't Zero After Concert");
    }

    @Test
        // "Conjured" items degrade in Quality twice as fast as normal items
    void conjuredDegradeInQualityAsTwiceAsNormal() {


        int quality = 10;
        Item[] items = new Item[]{
            new Item("Conjured Mana Cake", 3, quality),
            // if sellIn passed normal items degrade twice then conjured should degrade quadrice
            new Item("Conjured Mana Cake", 0, quality),
            new Item("Conjured Mana Cake", -1, quality),
        };
        GildedRose app = new GildedRose(items);

        //when
        app.updateQuality();

        //then
        assertEquals(quality - 2 * app.DELTA, items[0].quality, "Conjured didn't degrade in quality as twice as normal items");
        assertEquals(quality - 2 * 2 * app.DELTA, items[1].quality, "Conjured didn't degrade in quality as twice as normal items");
        assertEquals(quality - 2 * 2 * app.DELTA, items[2].quality, "Conjured didn't degrade in quality as twice as normal items");
    }


}
