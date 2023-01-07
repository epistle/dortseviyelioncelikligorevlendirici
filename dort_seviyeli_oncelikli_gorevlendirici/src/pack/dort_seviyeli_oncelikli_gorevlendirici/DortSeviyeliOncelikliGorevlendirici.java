/**
 * 
 */
package pack.dort_seviyeli_oncelikli_gorevlendirici;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Baris
 *
 */
public class DortSeviyeliOncelikliGorevlendirici {


	public DortSeviyeliOncelikliGorevlendirici() {
		// TODO Auto-generated constructor stub
	}

	//kuyrukları tanımlıyoruz
	public static Kuyruk fcfsKuyruk= new Kuyruk();
	public static Kuyruk birinciSeviyeKuyruk= new Kuyruk();
	public static Kuyruk ikinciSeviyeKuyruk= new Kuyruk();
	public static Kuyruk roundRobinKuyruk= new Kuyruk();
	
	//varış zamanına göre işlem yapılması için zamanı tanımlıyoruz
	public static int zaman = -1;
	
	//proses özelinde renk tanımlanacağı için standart renk tanımlıyoruz
	public static final String ANSI_RESET  = "\u001B[0m";
	
	//son işlenen proses bilgisi tutulur
	public static Proses sonIslenenProses; 

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		//dosya okuma işlemleri
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Tam dosya yolunu giriniz örn: C:\\Users\\Baris\\Downloads\\ek\\giris.txt  :");
		String filename = keyboard.nextLine();
		Scanner inputFile = new Scanner(new File(filename));   
		
		//dosyadan okunan proses bilgileri için dizi listesi oluşturuyoruz
		List<Proses> pList=new ArrayList<Proses>();
		
		//proses degerleri için tanımlıyoruz
		String degerler[] = new String[] {"","",""};
		
		
		//proses id 
        int pid = 0;
        
        //proses renk id
        int cid = 0;
		int cidd =1;
		
		//okunan dosyada yeni satır olduğu sürece işleme devam et
		while (inputFile.hasNextLine()) {
	        Scanner s2 = new Scanner(inputFile.nextLine());
	        int i=0;
	        //satırı virgüle göre parçalayarak proses değerlerini alıyoruz
	        while (s2.hasNext()) {
	            String s = s2.next();
	            s = s.replaceAll(", $", "");
	            s = s.replaceAll(",$", "");
	            degerler[i]=s;
	            i++;
	       }
	        
	        //proses id oluştur
			String prosesId = String.format("%04d", pid);
			
			//proses renk işlemleri
			String prosesColor;
			
			
			if(cidd<8) {
				prosesColor = "\u001B[3"+cid+"m";
			}
			else if(cidd>7 && cidd<14) {
				prosesColor = "\u001B[9"+cid+"m";
	        }else if(cidd>13 && cidd<21) {
	        	prosesColor = "\u001B[10"+cid+"m";
	        }else {
	        	prosesColor = "\u001B[4"+cid+"m";
	        }
	        
			//yeni proses oluşturulur
	        Proses p1 = new Proses(prosesId, Integer.parseInt(degerler[1]), Integer.parseInt(degerler[0]), Integer.parseInt(degerler[2]), prosesColor);
	        
	        
	        pid++;
	        
	        //proses renk işlemleri
	        cid++;
	        cidd++;
	        if(cid==7) {
	        	cid=0;
	        };
	        
	        //proses öncelik değerine göre ilgili kuyruğa aktarılır
	        if(Integer.parseInt(degerler[1])==0) {
	        	fcfsKuyruk.addKuyruk(p1);
	        }else if(Integer.parseInt(degerler[1])==1) {
	        	birinciSeviyeKuyruk.addKuyruk(p1);
	        }else if(Integer.parseInt(degerler[1])==2) {
	        	ikinciSeviyeKuyruk.addKuyruk(p1);
	        }else {
	        	roundRobinKuyruk.addKuyruk(p1);
	        }
		        
		 }
		 		 
		
		//kuyruklar boşalana kadar işlemlere devam edilir
	    while (fcfsKuyruk.getKuyrukList().size()>0 || 
	    		  birinciSeviyeKuyruk.getKuyrukList().size()>0 || 
	    		  ikinciSeviyeKuyruk.getKuyrukList().size()>0 || 
	    		  roundRobinKuyruk.getKuyrukList().size()>0) {
	    	  
    	 	kuyrukIsle();
	    }
	       
	}
	
	public static int kuyrukIsle() {
		
  	  	zaman++;
  	  	
  	  	//fcfs kuyruğundaki prosesler işlenir
        int sizeFcfs = fcfsKuyruk.getKuyrukList().size();
        //kuyruktaki tüm prosesler incelenir
        for (int j = 0; j < sizeFcfs; j++) {
        	//proses bilgileri alınır
            Proses p = fcfsKuyruk.getKuyrukList().get(j);
            
            //proses varış zamanı zaman ile karşılaştırılır
            if(p.getVarisZamani()<=zaman) {
            	//zaman değeri küçük veya eşitse proses işlenir
            	fcfsProsesIsle(p);
            	//işlenen proses kuyruktan silinir
            	fcfsKuyruk.getKuyrukList().remove(j);
            	//tekrar kuyruk işle çağrılması için fonksiyondan çıkılır
            	return 1;
            }
        }
        
        //birinci seviye kuyruktaki prosesler işlenir
        int sizeBirinci = birinciSeviyeKuyruk.getKuyrukList().size();
        //kuyruktaki tüm prosesler incelenir
        for (int j = 0; j < sizeBirinci; j++) {
        	//proses bilgileri alınır
            Proses p = birinciSeviyeKuyruk.getKuyrukList().get(j);
            
            //proses varış zamanı zaman ile karşılaştırılır
            if(p.getVarisZamani()<=zaman) {
            	//zaman değeri küçük veya eşitse proses işlenir
            	birinciSeviyeProsesIsle(p);
            	//işlenen proses kuyruktan silinir
            	birinciSeviyeKuyruk.getKuyrukList().remove(j);
            	//tekrar kuyruk işle çağrılması için fonksiyondan çıkılır
            	return 1;
            }
        }
        
        //ikinci seviye kuyruktaki prosesler işlenir
        int sizeIkinci = ikinciSeviyeKuyruk.getKuyrukList().size();
        //kuyruktaki tüm prosesler incelenir
        for (int j = 0; j < sizeIkinci; j++) {
        	//proses bilgileri alınır
            Proses p = ikinciSeviyeKuyruk.getKuyrukList().get(j);
            
            //proses varış zamanı zaman ile karşılaştırılır
            if(p.getVarisZamani()<=zaman) {
            	//zaman değeri küçük veya eşitse proses işlenir
            	ikinciSeviyeProsesIsle(p);
            	//işlenen proses kuyruktan silinir
            	ikinciSeviyeKuyruk.getKuyrukList().remove(j);
            	//tekrar kuyruk işle çağrılması için fonksiyondan çıkılır
            	return 1;
            }
        }
		
        //round robin kuyruğundaki prosesler işlenir
        int sizeRoundRobin = roundRobinKuyruk.getKuyrukList().size();
        //kuyruktaki tüm prosesler incelenir
        for (int j = 0; j < sizeRoundRobin; j++) {
        	//proses bilgileri alınır
            Proses p = roundRobinKuyruk.getKuyrukList().get(j);
            
            //proses varış zamanı zaman ile karşılaştırılır
            if(p.getVarisZamani()<=zaman) {
            	//zaman değeri küçük veya eşitse proses işlenir, proses silinmesi için sırası gönderilir
            	roundRobinProsesIsle(p,j);
            	//tekrar kuyruk işle çağrılması için fonksiyondan çıkılır
            	return 1;
            }
        }
        
    	//tekrar kuyruk işle çağrılması için fonksiyondan çıkılır 	
        return 1;
	}

	//fcfs kuyruğundaki proses çalıştırılır
	public static void fcfsProsesIsle(Proses p) {
		//proses askı kontrolü
		if(sonIslenenProses!=null && sonIslenenProses.getId()!=p.getId() && sonIslenenProses.getKalanIslenmeSuresi()>0) {
			System.out.println(sonIslenenProses.getRenk()+zaman+"sn proses askıda  	(id:"+sonIslenenProses.getId()+"	öncelik:"+sonIslenenProses.getOncelik()+"	kalan sure:"+(sonIslenenProses.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}
		
		//proses çıktısı
		System.out.println(p.getRenk()+zaman+"sn proses başladı  	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		
    	//kalan süre azaltılır
		p.setKalanIslenmeSuresi(p.getKalanIslenmeSuresi()-1);
		
		//proses bitene kadar işlenir
        for (int j = 0; j < p.getIslenmeSuresi()-2; j++) {
        	//zaman artırılır
        	zaman++;
        	
        	//kalan süre azaltılır
    		p.setKalanIslenmeSuresi(p.getKalanIslenmeSuresi()-1);
    		//proses çıktısı
    		System.out.println(p.getRenk()+zaman+"sn proses yürütülüyor	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+p.getKalanIslenmeSuresi()+" sn)"+ANSI_RESET);

        }
        
        //proses sonlanır
        p.setKalanIslenmeSuresi(0);
		System.out.println(p.getRenk()+zaman+"sn proses sonlandı  	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:0 sn)"+ANSI_RESET);
		
		//askı kontrolünde kullanılır
		sonIslenenProses = p;

	}
	
	
	//birinci seviye kuyruğundaki proses çalıştırılır
	public static int birinciSeviyeProsesIsle(Proses p) {
		//zaman aşımı kontrolü
		if(p.getBaslanmaSuresi()>-1 && zaman-p.getBaslanmaSuresi()>20) {
			System.out.println(p.getRenk()+zaman+"sn proses zamanaşımı	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
			return 1;
		}
		
		//proses askı kontrolü
		if(sonIslenenProses!=null && sonIslenenProses.getId()!=p.getId() && sonIslenenProses.getKalanIslenmeSuresi()>0) {
			System.out.println(sonIslenenProses.getRenk()+zaman+"sn proses askıda  	(id:"+sonIslenenProses.getId()+"	öncelik:"+sonIslenenProses.getOncelik()+"	kalan sure:"+(sonIslenenProses.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}
		
		//proses çıktısı
		System.out.println(p.getRenk()+zaman+"sn proses başladı  	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		
		//kalan süre azaltılır
		p.setKalanIslenmeSuresi(p.getKalanIslenmeSuresi()-1);
		
		//proses bir alt seviyeye düşürülür
		p.setOncelik(2);
		
		//baslangıc zamanı ayarlanır
		if(p.getBaslanmaSuresi()==-1) {
			p.setBaslanmaSuresi(zaman);
		}
		
		if(p.getKalanIslenmeSuresi()>0) {
			//proses bitmesine kalan süre var ise proses bir alt seviyeye düşürülür
			ikinciSeviyeKuyruk.addKuyruk(p);
			//System.out.println(p.getRenk()+zaman+"sn proses bir alt seviyeye düşürüldü  	(id:"+p.getId()+" burst time:"+p.getIslenmeSuresi()+"sn	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}else {
			
	        //proses sonlanır
			System.out.println(p.getRenk()+zaman+"sn proses sonlandı  	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:0 sn)"+ANSI_RESET);

		}

		//askı kontrolünde kullanılır
		sonIslenenProses = p;

		return 1;
		
	}
	
	//ikinci seviye kuyruğundaki proses çalıştırılır
	public static int ikinciSeviyeProsesIsle(Proses p) {
		//zaman aşımı kontrolü
		if(p.getBaslanmaSuresi()>-1 && zaman-p.getBaslanmaSuresi()>20) {
			System.out.println(p.getRenk()+zaman+"sn proses zamanaşımı	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
			return 1;
		}
		
		//proses askı kontrolü
		if(sonIslenenProses!=null && sonIslenenProses.getId()!=p.getId() && sonIslenenProses.getKalanIslenmeSuresi()>0) {
			System.out.println(sonIslenenProses.getRenk()+zaman+"sn proses askıda  	(id:"+sonIslenenProses.getId()+"	öncelik:"+sonIslenenProses.getOncelik()+"	kalan sure:"+(sonIslenenProses.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}
				
		if(p.getIslenmeSuresi()==p.getKalanIslenmeSuresi()) {
			//proses çıktısı
			System.out.println(p.getRenk()+zaman+"sn proses başladı  	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}else {
			//proses çıktısı
			System.out.println(p.getRenk()+zaman+"sn proses yürütülüyor	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}
		
		//kalan süre azaltılır
		p.setKalanIslenmeSuresi(p.getKalanIslenmeSuresi()-1);
		//proses bir alt seviyeye düşürülür
		p.setOncelik(3);
		
		//baslangıc zamanı ayarlanır
		if(p.getBaslanmaSuresi()==-1) {
			p.setBaslanmaSuresi(zaman);
		}

		if(p.getKalanIslenmeSuresi()>0) {
			//proses bitmesine kalan süre var ise proses bir alt seviyeye düşürülür
			roundRobinKuyruk.addKuyruk(p);
			//System.out.println(p.getRenk()+zaman+"sn proses bir alt seviyeye round robin düşürüldü  	(id:"+p.getId()+" burst time:"+p.getIslenmeSuresi()+"sn	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}else {
			//proses sonlanır
			System.out.println(p.getRenk()+zaman+"sn proses sonlandı  	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:0 sn)"+ANSI_RESET);

		}
		
		//askı kontrolünde kullanılır
		sonIslenenProses = p;

		return 1;
		
	}
	
	//round robin kuyruğundaki proses çalıştırılır
	public static int roundRobinProsesIsle(Proses p, int j) {
		
		//zaman aşımı kontrolü
		if(p.getBaslanmaSuresi()>-1 && zaman-p.getBaslanmaSuresi()>20) {
			System.out.println(p.getRenk()+zaman+"sn proses zamanaşımı	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
			roundRobinKuyruk.getKuyrukList().remove(j);
			return 1;
		}
		
		//proses askı kontrolü
		if(sonIslenenProses!=null && sonIslenenProses.getId()!=p.getId() && sonIslenenProses.getKalanIslenmeSuresi()>0) {
			System.out.println(sonIslenenProses.getRenk()+zaman+"sn proses askıda  	(id:"+sonIslenenProses.getId()+"	öncelik:"+sonIslenenProses.getOncelik()+"	kalan sure:"+(sonIslenenProses.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}
				
		//baslangıc zamanı ayarlanır
		if(p.getBaslanmaSuresi()==-1) {
			p.setBaslanmaSuresi(zaman);
		}
		
		if(p.getIslenmeSuresi()==p.getKalanIslenmeSuresi()) {
			//proses çıktısı
			System.out.println(p.getRenk()+zaman+"sn proses başladı  	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}else {
			//proses çıktısı
			System.out.println(p.getRenk()+zaman+"sn proses yürütülüyor	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:"+(p.getKalanIslenmeSuresi())+" sn)"+ANSI_RESET);
		}		
		
		//kalan süre azaltılır
		p.setKalanIslenmeSuresi(p.getKalanIslenmeSuresi()-1);

		//proses sonlandırılır
		if(p.getKalanIslenmeSuresi()==0) {
			System.out.println(p.getRenk()+zaman+"sn proses sonlandı  	(id:"+p.getId()+"	öncelik:"+p.getOncelik()+"	kalan sure:0 sn)"+ANSI_RESET);
			roundRobinKuyruk.getKuyrukList().remove(j);
		}
		
		//askı kontrolünde kullanılır
		sonIslenenProses = p;

		return 1;
		
	}
}
