
public class Item {

    private String name;
    private Armor armorType;
    private EquipSlot slot;
    private StatSet stats;
    private double durability;

    public Item (String name, Armor armorType,EquipSlot slot, StatSet statSet ){
        this.name = name;
        this.armorType = armorType;
        this.slot = slot;
        this.stats = statSet;
        this.durability = 100.0;

    }

    public String getName() {
        return name;
    }

    public Armor getArmorType() {
        return armorType;
    }

    public Integer getIntelligenceModifier() {
        return  stats.calculateIntelligence();
    }

    public Integer getStrengthModifier() {
        return  stats.calculatestrength();
    }

    public Integer getDexterityModifier() {
        return  stats.calculatedexterity();
    }

    public void takeDamage(double damageTaken) throws EquipmentBrokenException {
        if (damageTaken < 0){
            throw new IllegalArgumentException();
        }
        if (durability < damageTaken) {
            throw new EquipmentBrokenException;
        }

        durability = durability - damageTaken;

    }


}
