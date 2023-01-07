/**
 * 
 */
package pack.dort_seviyeli_oncelikli_gorevlendirici;

/**
 * @author Baris
 *
 */
public class Proses {
	
	private String id;
	private int oncelik;
	private int varisZamani;
	private int islenmeSuresi;
	private int baslanmaSuresi;
	private int kalanIslenmeSuresi;
	private String renk;

	/**
	 * 
	 */
	public Proses(String id, int oncelik, int varisZamani, int islenmeSuresi, String renk) {
		this.id=id;
		this.oncelik=oncelik;
		this.varisZamani=varisZamani;
		this.islenmeSuresi=islenmeSuresi;
		this.baslanmaSuresi=-1;
		this.kalanIslenmeSuresi=islenmeSuresi;
		this.renk=renk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getOncelik() {
		return oncelik;
	}

	public void setOncelik(int oncelik) {
		this.oncelik = oncelik;
	}

	public int getVarisZamani() {
		return varisZamani;
	}

	public void setVarisZamani(int varisZamani) {
		this.varisZamani = varisZamani;
	}

	public int getIslenmeSuresi() {
		return islenmeSuresi;
	}

	public void setIslenmeSuresi(int islenmeSuresi) {
		this.islenmeSuresi = islenmeSuresi;
	}

	public int getBaslanmaSuresi() {
		return baslanmaSuresi;
	}

	public void setBaslanmaSuresi(int baslanmaSuresi) {
		this.baslanmaSuresi = baslanmaSuresi;
	}

	public int getKalanIslenmeSuresi() {
		return kalanIslenmeSuresi;
	}

	public void setKalanIslenmeSuresi(int kalanIslenmeSuresi) {
		this.kalanIslenmeSuresi = kalanIslenmeSuresi;
	}

	public String getRenk() {
		return renk;
	}

	public void setRenk(String renk) {
		this.renk = renk;
	}

	
	
}
