
package Skladiste;

/**
 *
 * @author toni
 */
public class Skladiste {
    
    private int id, kolicina;
    private String id_proizvoda, id_proizvodjaca;

    public Skladiste() {
        super();
    }

    public Skladiste(int id, String id_proizvoda, String id_proizvodjaca, int kolicina) {
        this.id = id;
        this.id_proizvoda = id_proizvoda;
        this.id_proizvodjaca = id_proizvodjaca;
        this.kolicina = kolicina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public String getId_proizvoda() {
        return id_proizvoda;
    }

    public void setId_proizvoda(String id_proizvoda) {
        this.id_proizvoda = id_proizvoda;
    }

    public String getId_proizvodjaca() {
        return id_proizvodjaca;
    }

    public void setId_proizvodjaca(String id_proizvodjaca) {
        this.id_proizvodjaca = id_proizvodjaca;
    }
    
    
    
}
