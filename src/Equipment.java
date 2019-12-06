import java.util.HashMap;

public class Equipment {

    PlayerCharacter character;
    HashMap<EquipSlot, Item> equipment;

    public Equipment(PlayerCharacter character) {
        this.character = character;
        equipment = new HashMap<>();

    }

    public int getDexterityModifiers() {
        int dexModifier = 0;
        for (Item item : equipment.values()) {
            dexModifier += item.getDexterityModifier();
        }
        return dexModifier;
    }

    public int getStrengthModifiers() {
        int strengthModifier = 0;
        for (Item item : equipment.values()) {
            strengthModifier += item.getStrengthModifier();
        }
        return strengthModifier;
    }

    public int getIntelligenceModifiers() {
        int intelligenceModifier = 0;
        for (Item item : equipment.values()) {
            intelligenceModifier += item.getIntelligenceModifier();
        }
        return intelligenceModifier;
    }

    public void takeDamage(int damage) {
        double damagePerItem = damage / equipment.size();

        for (Item item : equipment.values()) {
            try {
                item.takeDamage(damagePerItem);

            } catch (EquipmentBrokenException e) {
                equipment.remove(item.getArmorType());
            }
        }
    }

    public boolean equipItem(Item item) {
        if (character.getArmorProficenies().contains(item.getArmorType())) {
            return true;
        }
         return false;
    }
}

