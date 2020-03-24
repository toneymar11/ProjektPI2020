
package Proizvodi;

/**
 *
 * @author toni
 */
public class Proizvodi {
    
    private int id, cijena;
    private String naziv;

    public Proizvodi() {
        
        super();
    }

    public Proizvodi(int id, String naziv, int cijena) {
        this.id = id;
        this.naziv = naziv;
        this.cijena = cijena;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCijena() {
        return cijena;
    }

    public void setCijena(int cijena) {
        this.cijena = cijena;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    
    
    
}
