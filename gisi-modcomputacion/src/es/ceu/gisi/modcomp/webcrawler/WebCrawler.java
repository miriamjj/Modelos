package es.ceu.gisi.modcomp.webcrawler;

import es.ceu.gisi.modcomp.webcrawler.jflex.JFlexScraper;
import es.ceu.gisi.modcomp.webcrawler.jsoup.JsoupScraper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta aplicación contiene el programa principal que ejecuta ambas partes del
 * proyecto de programación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class WebCrawler {

    public static void main(String[] args) {
        String PATH_PRUEBAS = new java.io.File("").getAbsolutePath()
            + "/test/es/ceu/gisi/modcomp/webcrawler/jflex/test/";

        try {
            FileWriter enlaces = new FileWriter("enlaces.txt");
            PrintWriter pw = new PrintWriter(enlaces);
            File archivo = new File(PATH_PRUEBAS + "prueba4.html");
            JFlexScraper scraper = new JFlexScraper(archivo);
            ArrayList<String> hiperenlaces = scraper.obtenerHiperenlaces();
            ArrayList<String> imagenes = scraper.obtenerHiperenlacesImagenes();
            
            for(String enlace : hiperenlaces){
                pw.println(enlace);
            }
            for(String enlace : imagenes){
                pw.println(enlace);
            }
            
            if(scraper.esDocumentoHTMLBienBalanceado()){
                System.out.println("El documento HTML está bien balanceado");
            }
            else if(!scraper.esDocumentoHTMLBienBalanceado()){
                System.out.println("ERROR! El documento HTML NO está bien balanceado");
            }
            else{}
            pw.close();
            enlaces.close();
            
            FileWriter enlacesJsoup = new FileWriter("enlacesJsoup.txt");
            PrintWriter pwj = new PrintWriter(enlacesJsoup);
            URL url = new URL("https://en.wikipedia.org/wiki/Main_Page");
            JsoupScraper jSoupScraper = new JsoupScraper(url); 
            List<String> hiperenlacesJsoup = jSoupScraper.obtenerHiperenlaces();
            List<String> imagenesJsoup = jSoupScraper.obtenerHiperenlacesImagenes();
            
            for(String enlace : hiperenlacesJsoup){
                pwj.println(enlace);
            }
            for(String enlace : imagenesJsoup){
                pwj.println(enlace);
            }
            pwj.println("Estadísticas:");
            pwj.println("a: " + jSoupScraper.estadisticasEtiqueta("a"));
            pwj.println("br: " + jSoupScraper.estadisticasEtiqueta("br"));
            pwj.println("div: " + jSoupScraper.estadisticasEtiqueta("div"));
            pwj.println("li: " + jSoupScraper.estadisticasEtiqueta("li"));
            pwj.println("ul: " + jSoupScraper.estadisticasEtiqueta("ul"));
            pwj.println("p: " + jSoupScraper.estadisticasEtiqueta("p"));
            pwj.println("span: " + jSoupScraper.estadisticasEtiqueta("span"));
            pwj.println("table: " + jSoupScraper.estadisticasEtiqueta("table"));
            pwj.println("td: " + jSoupScraper.estadisticasEtiqueta("td"));
            pwj.println("tr: " + jSoupScraper.estadisticasEtiqueta("tr"));
            

            pwj.close();
            enlacesJsoup.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
