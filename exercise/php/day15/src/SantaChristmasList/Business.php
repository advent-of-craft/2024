<?php

namespace SantaChristmasList;

use SantaChristmasList\Dependencies\Factory;
use SantaChristmasList\Dependencies\Inventory;
use SantaChristmasList\Dependencies\WishList;
use SantaChristmasList\Models\Child;
use SantaChristmasList\Models\Sleigh;

class Business
{
    private Factory $factory;
    private Inventory $inventory;
    private WishList $wishList;

    public function __construct(Factory $factory, Inventory $inventory, WishList $wishList)
    {
        $this->factory = $factory;
        $this->inventory = $inventory;
        $this->wishList = $wishList;
    }

    public function loadGiftsInSleigh(Child ...$children): Sleigh
    {
        $sleigh = new Sleigh();
        foreach ($children as $child) {
            $gift = $this->wishList->identifyGift($child);
            if ($gift !== null) {
                $manufacturedGift = $this->factory->findManufacturedGift($gift);
                if ($manufacturedGift !== null) {
                    $finalGift = $this->inventory->pickUpGift($manufacturedGift->getBarCode());
                    if ($finalGift !== null) {
                        $sleigh->offsetSet($child->getName(), "Gift: {$finalGift->getName()} has been loaded!");
                    }
                }
            }
        }
        return $sleigh;
    }
}