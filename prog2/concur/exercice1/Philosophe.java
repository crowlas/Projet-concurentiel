class Baguette{
  private boolean _prise = false;

  final synchronized void prendre() {
    try 
    {
      while( _prise ) 
      {
	wait();
      }
    } 
    catch( InterruptedException e ) 
    {
      e.printStackTrace();
      System.exit( -1 );
    }
    _prise = true;
  }

  final synchronized void relacher() {
    _prise = false;
    notifyAll();
  }
  
  public boolean get_prise(){
	  return this._prise;
  }
}

public class Philosophe implements Runnable {
  private String _nom;
  private Baguette _bGauche, _bDroite;

  public Philosophe( String n, Baguette g, Baguette d ){
    _nom = n;
    _bGauche = g;
    _bDroite = d;
  }

  public void run(){
	
	int nbTour = 100;
	int tour=0;
	int cptPenser = 0;
	int cptManger = 0;
	
    recommencer : while(tour<nbTour )
    {
    tour++;
      penser();
      cptPenser++;
      _bGauche.prendre();
      if (_bDroite.get_prise()){
    	  _bGauche.relacher();
    	  continue recommencer;
      }
      _bDroite.prendre();
      manger();
      cptManger++;
      _bDroite.relacher();
      _bGauche.relacher();
    }
	
	System.out.println(this._nom+" a pensé "+ cptPenser+" fois et à mangé "+cptManger+" fois en "+nbTour+" tours d'actions.");
  }

  final void manger() {
    //System.out.println( _nom + " mange." );
    try {
		Thread.sleep(10);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  final void penser() {
    //System.out.println( _nom + " pense." );
    try {
		Thread.sleep(10);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  public static void main( String args[] ){
	  System.out.println("*********************NOUVEAU TEST*****************");
	final String[] noms = { "Platon", "Socrate", "Aristote", "Diogène", "Sénèque" };
    final Baguette[] baguettes = { new Baguette(), new Baguette(), new Baguette(), new Baguette(), new Baguette() }; 
    Philosophe[] table;

    table = new Philosophe[ 5 ];
    for( char cpt = 0 ; cpt < table.length ; ++cpt )
    {
      table[ cpt ] = new Philosophe( noms[ cpt ], baguettes[ cpt ], baguettes[ ( cpt + 1 ) % table.length ] );
      new Thread( table[ cpt ] ).start();
    }
  }
}

