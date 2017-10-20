class Baguette
{
  private boolean _prise = false;

  final synchronized void prendre() 
  {
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

  final synchronized void relacher() 
  {
    _prise = false;
    notifyAll();
  }
}

public class Philosophe implements Runnable
{
  private String _nom;
  private Baguette _bGauche, _bDroite;

  public Philosophe( String n, Baguette g, Baguette d )
  {
    _nom = n;
    _bGauche = g;
    _bDroite = d;
  }

  public void run()
  {
    while( true )
    {
      penser();
      _bGauche.prendre();
      _bDroite.prendre();
      manger();
      _bDroite.relacher();
      _bGauche.relacher();
    }
  }

  final void manger() 
  {
    System.out.println( _nom + " mange." );
  }

  final void penser() 
  {
    System.out.println( _nom + " pense." );
  }

  public static void main( String args[] )
  {
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

