package es.ceu.gisi.modcomp.webcrawler.jflex.test;

import es.ceu.gisi.modcomp.webcrawler.jflex.HTMLParser;
import es.ceu.gisi.modcomp.webcrawler.jflex.JFlexScraper;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Clase que testea y muestra el uso del analizador léxico creado con JFlex.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class HTMLParserTest {

    private final static String PATH_PRUEBAS = new java.io.File("").getAbsolutePath()
            + "/test/es/ceu/gisi/modcomp/webcrawler/jflex/test/";

    private File ficheroPrueba1 = new File(PATH_PRUEBAS + "prueba1.html");
    private File ficheroPrueba2 = new File(PATH_PRUEBAS + "prueba2.html");
    private File ficheroPrueba3 = new File(PATH_PRUEBAS + "prueba3.html");
    private File ficheroPrueba4 = new File(PATH_PRUEBAS + "prueba4.html");
    private File ficheroPrueba5 = new File(PATH_PRUEBAS + "prueba5.html");
    private File ficheroPrueba6 = new File(PATH_PRUEBAS + "prueba6.html");
    private Reader reader1;
    private Reader reader2;
    HTMLParser analizador;
    
    /**
     * Se va a crear un analizador léxico, a partir de uno de los ficheros de
     * prueba.
     */
    public HTMLParserTest() {
        try {
            reader1 = new BufferedReader(new FileReader(ficheroPrueba1));
            reader2 = new BufferedReader(new FileReader(ficheroPrueba2));
            analizador = new HTMLParser(reader1);
        } catch (FileNotFoundException fnfe) {
            System.out.println("No se pudo abrir alguno de los ficheros");
            fnfe.printStackTrace(System.out);
            throw new RuntimeException();
        }
    }

    /**
     * El test comprueba que el analizador léxico reconoce los tres primeros
     * tokens de un fichero HTML y que corresponden con "<HTML>".
     */
    @Test
    public void compruebaEtiquetaInicioHTML() {
        try {
            Token token1 = analizador.nextToken();
            Token token2 = analizador.nextToken();
            Token token3 = analizador.nextToken();
            assertEquals(token1.getTipo(), Tipo.OPEN);
            assertEquals(token2.getValor().toLowerCase(), "html");
            assertEquals(token3.getTipo(), Tipo.CLOSE);
        } catch (IOException ex) {
            Logger.getLogger(HTMLParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
     * El test comprueba que el analizador léxico reconoce los tres primeros
     * tokens de un fichero HTML y que corresponden con "<HTML>".
     */
    @Test
    public void compruebaInicioYFinEtiquetaHTML() {
        try {
            analizador.yyreset(reader1);
            //El inicio de una etiqueta HTML es: <NOMBREETIQUETA>
            Token token1 = analizador.nextToken();
            Token token2 = analizador.nextToken();
            Token token3 = analizador.nextToken();
            assertEquals(token1.getTipo(), Tipo.OPEN);
            assertEquals(token2.getValor().toLowerCase(), "html");
            assertEquals(token3.getTipo(), Tipo.CLOSE);

            // El final de una etiqueta HTML es: </NOMBREETIQUETA>
            Token token4 = analizador.nextToken();
            Token token5 = analizador.nextToken();
            Token token6 = analizador.nextToken();
            Token token7 = analizador.nextToken();
            assertEquals(token4.getTipo(), Tipo.OPEN);
            assertEquals(token5.getTipo(), Tipo.SLASH);
            assertEquals(token6.getValor().toLowerCase(), "html");
            assertEquals(token7.getTipo(), Tipo.CLOSE);
        } catch (IOException ex) {
            Logger.getLogger(HTMLParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
        }
    }

    /**
     * El test comprueba que existe una etiqueta BR y que es una etiqueta sin
     * contenido, que se abren y cierran en una misma sentencia: <BR/>
     */
    @Test
    public void compruebaInicioYFinEtiquetaBR() {
        try {
            analizador.yyreset(reader2);
            //Una etiqueta BR tiene la forma: <BR /> (incluye inicio y fin de etiqueta)
            boolean encontradoBR = false;
            while (!encontradoBR) {
                Token token1 = analizador.nextToken();
                while (token1.getTipo() == Tipo.OPEN) {
                    //Si encuentro un token OPEN puede ser el inicio de una etiqueta BR...
                    Token token2 = analizador.nextToken();
                    if (token2.getValor().toLowerCase().equals("br")) {
                        //Es una etiqueta BR:
                        encontradoBR = true;
                        Token token4 = analizador.nextToken();
                        Token token5 = analizador.nextToken();
                        assertEquals(token4.getTipo(), Tipo.SLASH);
                        assertEquals(token5.getTipo(), Tipo.CLOSE);
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HTMLParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
        }
    }

        //Los siguientes tests corresponden al Fichero de pruebas 1
    /**
     * El test comprueba que no se obtiene ningún hiperenlace porque no hay
     */
    @Test
    public void noObtieneHiperenlaces1() throws FileNotFoundException {
        JFlexScraper F1 = new JFlexScraper(ficheroPrueba1);
        assertEquals(F1.obtenerHiperenlaces().size(),0);
    }
    
    /**
     * El test comprueba que no se obtiene ningún enlace de imagen porque no hay
     */
    @Test
    public void noObtieneHiperenlacesImagenes1() throws FileNotFoundException {
        JFlexScraper F1 = new JFlexScraper(ficheroPrueba1);
        assertEquals(F1.obtenerHiperenlacesImagenes().size(),0);

    }
    
   //Los siguientes tests corresponden al Fichero de pruebas 2

    /**
     * El test comprueba que se obtienen correctamente los hiperenalaces
     */
    @Test
    public void obtieneHiperenlacesCorrectamente2() throws FileNotFoundException{
        JFlexScraper F2 = new JFlexScraper(ficheroPrueba2);
        assertEquals(F2.obtenerHiperenlaces().size(),1);
        assertEquals(F2.obtenerHiperenlaces().get(0),"http://www.bbc.co.uk");
    }
    
    /**
     * El test comprueba que se obtienen correctamente los enlaces de las imágenes
     */
    @Test
    public void obtieneImagenesCorrectamente2() throws FileNotFoundException{
        JFlexScraper F2 = new JFlexScraper(ficheroPrueba2);
        assertEquals(F2.obtenerHiperenlacesImagenes().size(),1);
        assertEquals(F2.obtenerHiperenlacesImagenes().get(0),"brushedsteel.jpg");
    }
    
    /**
     * El test comprueba que el código está bien balanceado
     */
    @Test
    public void estaBalanceado2() throws FileNotFoundException {
        JFlexScraper F2 = new JFlexScraper(ficheroPrueba2);
        assertTrue(F2.esDocumentoHTMLBienBalanceado());
    }
    
    
     //Los siguientes tests corresponden al Fichero de pruebas 3

    /**
     * El test comprueba que se obtienen todos los hiperenlaces que contiene el fichero3,
     * de esta forma también comprueba que no confunde el "a href" del texto con las 
     * etiquetas de los hiperenlaces.  
     */
    @Test
    public void obtieneHiperenlacesCorrectamente3() throws FileNotFoundException {
        JFlexScraper F3 = new JFlexScraper(ficheroPrueba3);
        assertEquals(F3.obtenerHiperenlaces().size(),3);
        assertEquals(F3.obtenerHiperenlaces().get(0),"http://www.bbc.co.uk");
        assertEquals(F3.obtenerHiperenlaces().get(1),"https://www.google.es/");
        assertEquals(F3.obtenerHiperenlaces().get(2),"https://www.youtube.com/");
    }
    
    /**
     * El test comprueba que se obtienen todos los enlaces de las imágenes que contiene el fichero3,
     * de esta forma también comprueba que no confunde el "img src" del texto con las 
     * etiquetas de las imágenes. 
     */
    @Test
    public void obtieneImagenesCorrectamente3() throws FileNotFoundException {
        JFlexScraper F3 = new JFlexScraper(ficheroPrueba3);
        assertEquals(F3.obtenerHiperenlacesImagenes().size(),1);
        assertEquals(F3.obtenerHiperenlacesImagenes().get(0),"ceu.jpg");
    }
    
    /**
     * El test comprueba que el código está bien balanceado
     */
    @Test
    public void estaBalanceado3() throws FileNotFoundException {
        JFlexScraper F3 = new JFlexScraper(ficheroPrueba3);
        assertTrue(F3.esDocumentoHTMLBienBalanceado());
    }
    
    //El siguiente test corresponde al Fichero de pruebas 4

    /**
     * El test comprueba que el código no está bien balanceado
     */
    @Test
    public void noEstaBalanceado4() throws FileNotFoundException {
        JFlexScraper F4 = new JFlexScraper(ficheroPrueba4);
        assertFalse(F4.esDocumentoHTMLBienBalanceado());
    }
   
     //Los siguientes tests corresponden al Fichero de pruebas 5
    
    /**
     * El test comprueba que se obtienen todos los hiperenlaces que contiene el fichero5,
     * aunque entre "a" y "href" exista otra etiqueta   
     */
    @Test
    public void obtieneHiperenlacesCorrectamente5() throws FileNotFoundException {
        JFlexScraper F5 = new JFlexScraper(ficheroPrueba5);
        assertEquals(F5.obtenerHiperenlaces().size(),1);
        assertEquals(F5.obtenerHiperenlaces().get(0),"https://intranet.ceu.es/es-es/Paginas/inicio.aspx");

    }
    
    /**
     * El test comprueba que se obtienen todos los enlaces de las imágenes que contiene el fichero5,
     * aunque entre "img" y "src" exista otra etiqueta   
     */
    @Test
    public void obtieneImagenesCorrectamente5() throws FileNotFoundException {
        JFlexScraper F5 = new JFlexScraper(ficheroPrueba5);
        assertEquals(F5.obtenerHiperenlacesImagenes().size(),1);
        assertEquals(F5.obtenerHiperenlacesImagenes().get(0),"practica.jpg");
    }
    
     //Los siguientes tests corresponden al Fichero de pruebas 6

    /**
     * El test comprueba que se obtienen correctamente los hiperenalaces en un código más complejo
     */
    @Test
    public void obtieneHiperenlacesCorrectamente6() throws FileNotFoundException {
        JFlexScraper F6 = new JFlexScraper(ficheroPrueba6);
        assertEquals(F6.obtenerHiperenlaces().size(),48);
        assertEquals(F6.obtenerHiperenlaces().get(0),"/leadpage/fantrack/fantrack.htm");
    }
    
    /**
     * El test comprueba que se obtienen correctamente los enlaces de las imágenes
     */
    @Test
    public void obtieneImagenesCorrectamente6() throws FileNotFoundException {
        JFlexScraper F6 = new JFlexScraper(ficheroPrueba6);
        assertEquals(F6.obtenerHiperenlacesImagenes().size(),2);
        assertEquals(F6.obtenerHiperenlacesImagenes().get(0),"http://images.usatoday.com/inetart/brib.gif");
                assertEquals(F6.obtenerHiperenlacesImagenes().get(1),"http://images.usatoday.com/inetart/botrib.gif");
    }
    
    /**
     * El test comprueba que el código está bien balanceado
     */
    @Test
    public void estaBalanceado6() throws FileNotFoundException {
        JFlexScraper F6 = new JFlexScraper(ficheroPrueba6);
        assertTrue(F6.esDocumentoHTMLBienBalanceado());
    }

}
