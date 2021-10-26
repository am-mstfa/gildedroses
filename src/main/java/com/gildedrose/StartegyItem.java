package com.gildedrose;

class StrategyItem extends Item {

    public StrategyItem(Item item) {
        super(item.name, item.sellIn, item.quality);
    }

    public Item getAsItem(){
        return new Item(this.name, this.sellIn, this.quality);
    }

    public void updateSellIn(final int DELTA){
        if(!this.name.contains(GlidedRoseConstants.LEGENDARY)){
            this.sellIn -=DELTA;
        }
    }

    public void updateQuality(final int DELTA) {

        if ( this.name.contains(GlidedRoseConstants.AGED) ) {
                this.quality += DELTA;
        }

        else if ( this.name.contains(GlidedRoseConstants.LEGENDARY) ) {
            this.quality += 0;
        }

        else if ( this.name.contains(GlidedRoseConstants.PASSES) ) {

            if (this.sellIn <= 0) {
                this.quality = 0;
            } else if (this.sellIn <= 5) {
                this.quality += 3 * DELTA;
            } else if (this.sellIn <= 10) {
                this.quality += 2 * DELTA;
            }
        }

        else if ( this.name.contains(GlidedRoseConstants.CONJURED) ) {
            if (this.sellIn < 0) {
                this.quality -= 2 * 2 * DELTA;
            } else {
                this.quality -= 2 * DELTA;
            }
        }

        else  {
            if (this.sellIn < 0) {
                this.quality -= 2 * DELTA;
            } else {
                this.quality -= DELTA;
            }
        }

        if (this.quality < 0){
            this.quality = 0;
        }

        if (this.quality > 50){
            this.quality = 50;
        }

    }


}
