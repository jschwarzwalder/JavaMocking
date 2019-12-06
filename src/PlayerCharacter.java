import java.util.List;

public interface PlayerCharacter {

    public String getName();
    public CharacterClass getPlayerClass();
    public List<Armor>  getArmorProficenies();
    public Equipment getEquipment();
}
