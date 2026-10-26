package Piezas;

import java.util.ArrayList;
import java.util.List;

// Aquí están los personajes, sus rangos y sus imágenes, según la figura 2 del enunciado.
// El campo "archivo" guarda el nombre de la imagen en resources/personajes.
public enum Personaje {
    // Héroes
    MR_FANTASTIC(true, 10, "Mr. Fantastic", "V_R2_6"),
    CAPTAIN_AMERICA(true, 9, "Captain America", "V_R2_5"),
    PROFESSOR_X(true, 8, "Professor X", "V_R2_4"),
    NICK_FURY(true, 8, "Nick Fury", "V_R2_3"),
    SPIDER_MAN(true, 7, "Spider-Man", "V_R2_2"),
    WOLVERINE(true, 7, "Wolverine", "V_R2_1"),
    NAMOR(true, 7, "Namor", "V_R3_9"),
    DAREDEVIL(true, 6, "Daredevil", "V_R3_8"),
    SILVER_SURFER(true, 6, "Silver Surfer", "V_R3_7"),
    HULK(true, 6, "Hulk", "V_R3_6"),
    IRON_MAN(true, 6, "Iron Man", "V_R3_5"),
    THOR(true, 5, "Thor", "V_R3_4"),
    CYCLOPS(true, 5, "Cyclops", "V_R3_3"),
    INVISIBLE_WOMAN(true, 5, "Invisible Woman", "V_R3_2"),
    HUMAN_TORCH(true, 5, "Human Torch", "V_R3_1"),
    GHOST_RIDER(true, 4, "Ghost Rider", "V_R4_5"),
    BLADE(true, 4, "Blade", "V_R4_4"),
    PUNISHER(true, 4, "Punisher", "V_R4_3"),
    THING(true, 4, "Thing", "V_R4_2"),
    BEAST(true, 3, "Beast", "V_R4_1"),
    GIANT_MAN(true, 3, "Giant Man", "V_R5_4"),
    COLOSSUS(true, 3, "Colossus", "V_R5_3"),
    SHE_HULK(true, 3, "She-Hulk", "V_R5_2"),
    EMMA_FROST(true, 3, "Emma Frost", "V_R5_1"),
    ICE_MAN(true, 2, "Ice Man", "V_R6_5"),
    SPIDER_GIRL(true, 2, "Spider-Girl", "V_R6_4"),
    GAMBIT(true, 2, "Gambit", "V_R6_3"),
    PHOENIX(true, 2, "Phoenix", "V_R6_2"),
    DR_STRANGE(true, 2, "Dr. Strange", "V_R6_1"),
    STORM(true, 2, "Storm", "V_R7_1"),
    ELEKTRA(true, 2, "Elektra", "V_R8_1"),
    NIGHTCRAWLER(true, 2, "Nightcrawler", "V_R9_1"),
    BLACK_WIDOW_H(true, 1, "Black Widow", "V_R10_1"),

    // Villanos
    DR_DOOM(false, 10, "Dr. Doom", "H_R2_6"),
    GALACTUS(false, 9, "Galactus", "H_R2_5"),
    MAGNETO(false, 8, "Magneto", "H_R2_4"),
    KINGPIN(false, 8, "Kingpin", "H_R2_3"),
    VENOM(false, 7, "Venom", "H_R2_2"),
    GREEN_GOBLIN(false, 7, "Green Goblin", "H_R2_1"),
    APOCALYPSE(false, 7, "Apocalypse", "H_R3_9"),
    BULLSEYE(false, 6, "Bullseye", "H_R3_8"),
    ONSLAUGHT(false, 6, "Onslaught", "H_R3_7"),
    RED_SKULL(false, 6, "Red Skull", "H_R3_6"),
    OMEGA_RED(false, 6, "Omega Red", "H_R3_5"),
    DEADPOOL(false, 5, "Deadpool", "H_R3_4"),
    MYSTERIO(false, 5, "Mysterio", "H_R3_3"),
    DR_OCTOPUS(false, 5, "Dr. Octopus", "H_R3_2"),
    MYSTIQUE(false, 5, "Mystique", "H_R3_1"),
    SABRETOOTH(false, 4, "Sabretooth", "H_R4_5"),
    ABOMINATION(false, 4, "Abomination", "H_R4_4"),
    THANOS(false, 4, "Thanos", "H_R4_3"),
    BLACK_CAT(false, 4, "Black Cat", "H_R4_2"),
    CARNAGE(false, 3, "Carnage", "H_R4_1"),
    JUGGERNAUT(false, 3, "Juggernaut", "H_R5_4"),
    MOLE_MAN(false, 3, "Mole Man", "H_R5_3"),
    LIZARD(false, 3, "Lizard", "H_R5_2"),
    RHINO(false, 3, "Rhino", "H_R5_1"),
    ELECTRO(false, 2, "Electro", "H_R6_5"),
    VIPER(false, 2, "Viper", "H_R6_4"),
    LEADER(false, 2, "Leader", "H_R6_3"),
    SANDMAN(false, 2, "Sandman", "H_R6_2"),
    MR_SINISTER(false, 2, "Mr. Sinister", "H_R6_1"),
    ULTRON(false, 2, "Ultron", "H_R7_1"),
    SENTINEL_2(false, 2, "Sentinel 2", "H_R8_1"),
    SENTINEL_1(false, 2, "Sentinel 1", "H_R9_1"),
    BLACK_WIDOW_V(false, 1, "Black Widow", "H_R10_1");

    private final boolean esHeroe;
    private final int rango;
    private final String nombre;
    private final String archivo;

    Personaje(boolean esHeroe, int rango, String nombre, String archivo) {
        this.esHeroe = esHeroe;
        this.rango = rango;
        this.nombre = nombre;
        this.archivo = archivo;
    }

    public boolean esHeroe() {
        return esHeroe;
    }

    public int getRango() {
        return rango;
    }

    public String getNombre() {
        return nombre;
    }

    public String getArchivo() {
        return archivo;
    }

    public static List<Personaje> deBando(boolean esHeroe) {
        List<Personaje> lista = new ArrayList<>();
        for (Personaje p : values()) {
            if (p.esHeroe == esHeroe)
                lista.add(p);
        }
        return lista;
    }
}
