package net.sf.rails.game.special;

import java.util.List;

import net.sf.rails.common.parser.ConfigurationException;
import net.sf.rails.common.parser.Tag;
import net.sf.rails.game.MapHex;
import net.sf.rails.game.RailsItem;
import net.sf.rails.game.RailsRoot;
import net.sf.rails.game.state.GenericState;
import net.sf.rails.game.state.IntegerState;
import net.sf.rails.game.state.Owner;
import net.sf.rails.util.Util;


public class SellBonusToken extends SpecialProperty {

    private String locationCodes = null;
    private List<MapHex> locations = null;
    private final GenericState<Owner> seller = new GenericState<>(this, "seller");
    private String name;
    private int price;
    private int value;
    private int maxNumberToSell;
    private final IntegerState numberSold = IntegerState.create(this, "numberSold");

    /**
     * Used by Configure (via reflection) only
     */
    public SellBonusToken(RailsItem parent, String id) {
        super(parent, id);
    }

    @Override
    public void configureFromXML(Tag tag) throws ConfigurationException {
        super.configureFromXML(tag);

        Tag sellBonusTokenTag = tag.getChild("SellBonusToken");
        if (sellBonusTokenTag == null) {
            throw new ConfigurationException("<SellBonusToken> tag missing");
        }

        locationCodes = sellBonusTokenTag.getAttributeAsString("location");
        if (!Util.hasValue(locationCodes))
            throw new ConfigurationException("SellBonusToken: location missing");

        name = sellBonusTokenTag.getAttributeAsString("name");

        value = sellBonusTokenTag.getAttributeAsInteger("value", 0);
        if (value <= 0)
            throw new ConfigurationException("Value invalid [" + value + "] or missing");

        price = sellBonusTokenTag.getAttributeAsInteger("price", 0);
        if (price <= 0)
            throw new ConfigurationException("Price invalid [" + price + "] or missing");

        maxNumberToSell = sellBonusTokenTag.getAttributeAsInteger("amount", 1);

    }

    @Override
    public void finishConfiguration(RailsRoot root) throws ConfigurationException {
        locations = root.getMapManager().parseLocations(locationCodes);
    }

    @Override
    public void setExercised() {
        numberSold.add(1);
    }

    public void makeResellable() {
        numberSold.add(-1);
    }

    @Override
    public boolean isExercised() {
        return maxNumberToSell >= 0 && numberSold.value() >= maxNumberToSell;
    }

    public boolean isExecutionable() {
        return true;
    }

    public List<MapHex> getLocations() {
        return locations;
    }

    public String getLocationNameString() {
        return locationCodes;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getValue() {
        return value;
    }

    public Owner getSeller() {
        return seller.value();
    }

    public void setSeller(Owner seller) {
        this.seller.set(seller);
    }

    @Override
    public String toText() {
        return "SellBonusToken comp=" + originalCompany.getId() + " hex="
                + locationCodes + " value=" + value + " price=" + price
                + " max=" + maxNumberToSell + " sold=" + numberSold.value();
    }

}
