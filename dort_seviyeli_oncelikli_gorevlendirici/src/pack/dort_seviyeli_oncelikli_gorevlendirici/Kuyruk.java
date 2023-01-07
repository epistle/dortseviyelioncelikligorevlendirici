/**
 * 
 */
package pack.dort_seviyeli_oncelikli_gorevlendirici;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Baris
 *
 */
public class Kuyruk {
	private List<Proses> kuyrukList=new ArrayList<Proses>();
	
	/**
	 * 
	 */
	public Kuyruk() {
		// TODO Auto-generated constructor stub
	}

	public List<Proses> getKuyrukList() {
		return kuyrukList;
	}

	public void setKuyrukList(List<Proses> kuyrukList) {
		this.kuyrukList = kuyrukList;
	}


	public void addKuyruk(Proses proses) {
		this.kuyrukList.add(proses);
	}
	

	public void clear() {
		this.kuyrukList.clear();
	}
	
	public void sort() {
		this.kuyrukList.sort(null);
	}

	public void get(int i) {
		this.kuyrukList.get(i);
	}


	public void remove(int i) {
		this.kuyrukList.remove(i);
	}


}
