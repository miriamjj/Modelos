package es.ceu.gisi.modcomp.webcrawler.jsoup;

import es.ceu.gisi.modcomp.webcrawler.jsoup.JsoupScraper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Clase que testea y muestra el uso del analizador de árboles DOM Jsoup.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class JsoupScraperTest {

    private static final String HTML = ""
                                       + "<HTML>"
                                       + "    <HEAD>"
                                       + "        <TITLE>My first webpage</TITLE>"
                                       + "    </HEAD>"
                                       + "    <BODY>"
                                       + "        <P>"
                                       + "            This is"
                                       + "            some text<BR />"
                                       + "            and a"
                                       + "            <A href=\"http://www.bbc.co.uk\">link</A>"
                                       + "        </P>"
                                       + "        <IMG src=\"brushedsteel.jpg\"/>"
                                       + "    </BODY>"
                                       + "</HTML>";
    private final JsoupScraper scraper1;
    private final JsoupScraper scraper3;
    private final JsoupScraper scraper6;
    private final JsoupScraper scraper7;
    
    

    /**
     * Se va a crear un analizador léxico, a partir de uno de los ficheros de
     * prueba.
     */
    public JsoupScraperTest() throws IOException {
        String PATH_PRUEBAS = new java.io.File("").getAbsolutePath()
            + "/test/es/ceu/gisi/modcomp/webcrawler/jflex/test/";
        scraper1 = new JsoupScraper(HTML);
        scraper3 = new JsoupScraper(new File(PATH_PRUEBAS + "prueba3.html"));
        scraper6 = new JsoupScraper(new File(PATH_PRUEBAS + "prueba6.html"));
        scraper7 = new JsoupScraper(new File(PATH_PRUEBAS + "prueba7.html"));
    }

    /**
     * El test recupera el nombre de la primera imagen insertada con la etiqueta
     * IMG .
     */
    @Test
    public void recuperaNombrePrimeraImagen() {
        assertEquals(scraper1.obtenerContenidoImg(),"brushedsteel.jpg");
    }
    
    /**
     * El test comprueba que se obtiene el número de enlaces y los hiperenlaces correctamente 
     */
    @Test
    public void obtieneHiperenlacesCorrectamente() {
        assertEquals(scraper3.obtenerHiperenlaces().size(),3);
        assertEquals(scraper3.obtenerHiperenlaces().get(0),"http://www.bbc.co.uk");
        assertEquals(scraper3.obtenerHiperenlaces().get(1),"https://www.google.es/");
        assertEquals(scraper3.obtenerHiperenlaces().get(2),"https://www.youtube.com/");
    }
    
    /**
     * El test comprueba que se obtienen los enlaces de las imagenes correctamente 
     */
    @Test
    public void obtieneImagenesCorrectamente() {
        assertEquals(scraper3.obtenerHiperenlacesImagenes().get(0),"ceu.jpg");
    }
    
    
     //Fichero Prueba 6 
    
    /**
     * El test recupera el nombre de la primera imagen insertada con la etiqueta
     * IMG .
     */
    @Test
    public void PrimeraImagenPrueba6() {
        assertEquals(scraper6.obtenerHiperenlacesImagenes().get(0),"http://images.usatoday.com/inetart/brib.gif");

    }
    
    /**
     * El test comprueba que se obtiene el número de hiperenlaces correctamente 
     */
    @Test
    public void HiperenlacesPrueba6() {
        assertEquals(scraper6.obtenerHiperenlaces().size(),49);
        assertEquals(scraper6.obtenerHiperenlaces().get(0),"/leadpage/fantrack/fantrack.htm");
    }
    
    /**
     * El test comprueba que se obtienen las etiquetas "a" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaA(){
        assertEquals(scraper6.estadisticasEtiqueta("a"),49);
    }
        
        /**
     * El test comprueba que se obtienen las etiquetas "br" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaBR(){
        assertEquals(scraper6.estadisticasEtiqueta("br"),2);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "div" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaDIV(){
        assertEquals(scraper6.estadisticasEtiqueta("div"),1);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "li" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaLI(){
        assertEquals(scraper6.estadisticasEtiqueta("li"),0);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "ul" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaUL(){
        assertEquals(scraper6.estadisticasEtiqueta("ul"),5);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "p" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaP(){
        assertEquals(scraper6.estadisticasEtiqueta("p"),6);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "span" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaSPAN(){
        assertEquals(scraper6.estadisticasEtiqueta("span"),1);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "table" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaTABLE(){
        assertEquals(scraper6.estadisticasEtiqueta("table"),2);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "td" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaTD(){
        assertEquals(scraper6.estadisticasEtiqueta("td"),26);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "tr" correctamente 
     */
    @Test
    public void p6obtieneEtiquetaTR(){
        assertEquals(scraper6.estadisticasEtiqueta("tr"),15);
    }
    
    //Fichero Prueba 7 
    
    /**
     * El test recupera el nombre de la primera imagen insertada con la etiqueta
     * IMG .
     */
    @Test
    public void PrimeraImagenPrueba7() {
        assertEquals(scraper7.obtenerHiperenlacesImagenes().get(0),"//upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Arab-Sasanian_coin_issued_by_Yazid_I_ibn_Mu%27awiya_in_the_year_of_the_Battle_of_Karbala.jpg/199px-Arab-Sasanian_coin_issued_by_Yazid_I_ibn_Mu%27awiya_in_the_year_of_the_Battle_of_Karbala.jpg");

    }
    
    /**
     * El test comprueba que se obtiene el número de hiperenlaces correctamente 
     */
    @Test
    public void HiperenlacesPrueba7() {
        assertEquals(scraper7.obtenerHiperenlaces().size(),327);
        assertEquals(scraper7.obtenerHiperenlaces().get(0),"");
    }
    
    /**
     * El test comprueba que se obtienen las etiquetas "a" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaA(){
        assertEquals(scraper7.estadisticasEtiqueta("a"),327);
    }
        
        /**
     * El test comprueba que se obtienen las etiquetas "br" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaBR(){
        assertEquals(scraper7.estadisticasEtiqueta("br"),12);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "div" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaDIV(){
        assertEquals(scraper7.estadisticasEtiqueta("div"),119);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "li" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaLI(){
        assertEquals(scraper7.estadisticasEtiqueta("li"),232);
    }
    
    /**
     * El test comprueba que se obtienen las etiquetas "ul" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaUL(){
        assertEquals(scraper7.estadisticasEtiqueta("ul"),35);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "p" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaP(){
        assertEquals(scraper7.estadisticasEtiqueta("p"),6);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "span" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaSPAN(){
        assertEquals(scraper7.estadisticasEtiqueta("span"),86);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "table" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaTABLE(){
        assertEquals(scraper7.estadisticasEtiqueta("table"),2);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "td" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaTD(){
        assertEquals(scraper7.estadisticasEtiqueta("td"),5);
    }
    
        /**
     * El test comprueba que se obtienen las etiquetas "tr" correctamente 
     */
    @Test
    public void p7obtieneEtiquetaTR(){
        assertEquals(scraper7.estadisticasEtiqueta("tr"),2);
    }
}
