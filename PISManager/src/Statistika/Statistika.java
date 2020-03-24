
package Statistika;

/**
 *
 * @author toni
 */
public class Statistika {
    
    
    String id_proizvoda;
    int kolicina;

    public Statistika() {
        
        super();
        
    }

    public Statistika(String id_proizvoda, int kolicina) {
        
        this.id_proizvoda = id_proizvoda;
        this.kolicina = kolicina;
    }

    public String getId_proizvoda() {
        return id_proizvoda;
    }

    public void setId_proizvoda(String id_proizvoda) {
        this.id_proizvoda = id_proizvoda;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
    
    
    
    
    
}
