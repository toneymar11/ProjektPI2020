
package Narudzbe;

/**
 *
 * @author toni
 */
public class Narudzbe {
   
   private int id_narudzbe;
   private String id_kupca, odobreno, id_proizvoda, kolicina;

    public Narudzbe() {
        super();
    }

    public Narudzbe(int id_narudzbe, String id_kupca, String id_proizvoda, String kolicina, String odobreno) {
        this.id_narudzbe = id_narudzbe;
        this.id_kupca = id_kupca;
        this.id_proizvoda = id_proizvoda;
        this.kolicina = kolicina;
        this.odobreno = odobreno;
    }

    public int getId_narudzbe() {
        return id_narudzbe;
    }

    public void setId_narudzbe(int id_narudzbe) {
        this.id_narudzbe = id_narudzbe;
    }
    

    public String getId_kupca() {
        return id_kupca;
    }

    public void setId_kupca(String id_kupca) {
        this.id_kupca = id_kupca;
    }

    public String getId_proizvoda() {
        return id_proizvoda;
    }   

    public void setId_proizvoda(String id_proizvoda) {
        this.id_proizvoda = id_proizvoda;
    }

    public String getKolicina() {
        return kolicina;
    }

    public void setKolicina(String kolicina) {
        this.kolicina = kolicina;
    }


    public String getOdobreno() {
        return odobreno;
    }

    public void setOdobreno(String odobreno) {
        this.odobreno = odobreno;
    }
    
   
   
}

