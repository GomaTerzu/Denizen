package net.aufdemrand.denizen.objects;

public interface Adjustable {

    /**
     * Gets a specific attribute using this object to fetch the necessary data.
     *
     * @param mechanism  the name of mechanism to change
     * @param value      the value to input into the mechanism
     */
    public void adjust(Mechanism mechanism, Element value);
    // TODO?: * @return  a string result of the fetched attribute
}
