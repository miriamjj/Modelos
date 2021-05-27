package es.ceu.gisi.modcomp.webcrawler.jflex;

import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Esta clase encapsula toda la lógica de interacción con el parser HTML.
 * Contiene el autómatá que analizará el código
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class JFlexScraper {

    HTMLParser analizador;
    ArrayList<String> hiperenlace;
    ArrayList<String> imagenes;
    Stack<String> pila;

    public JFlexScraper(File fichero) throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);
        hiperenlace = new ArrayList<String>(); //Esta variable guarda el valor de los hiperenlaces
        imagenes = new ArrayList<String>(); //Esta variable guarda el valor de los enlaces de las imágenes 
        pila = new Stack<String>(); //creamos la pila 

        Token miToken;
        boolean a = true;
        boolean enlaceA = false; //Booleano que pasa a true en caso de encontrar un hiperenlace
        boolean enlaceIMG = false; //Booleano que pasa a true en caso de encontrar una imagen
        boolean enlaceHREF = false; //Booleano que pasa a true en caso de encontrar un hiperenlace
        boolean enlaceSRC = false; //Booleano que pasa a true en caso de encontrar una imagen
        int estado = 0;
        while (a) {
            try {
                miToken = analizador.nextToken();
                if (miToken != null) {
                    switch (estado) { //
                        case 0: { //estado 0
                            if (miToken.getTipo() == Tipo.OPEN) {
                                estado = 1;
                            }
                            break;
                        }
                        case 1: { //estado 1
                            switch (miToken.getTipo()) {
                                case PALABRA: {
                                    pila.push(miToken.getValor());
                                    estado = 2; //En caso de encontrar una palabra transita al estado 2 
                                    if (miToken.getValor().equalsIgnoreCase("a")) {
                                        enlaceA = true;
                                    } //Si la palabra es "a" es un posible hiperenlace, por lo tanto enlaceA es True
                                    if (miToken.getValor().equalsIgnoreCase("img")) {
                                        enlaceIMG = true;
                                    }//Si la palabra es "img" es una posible imagen, por lo tanto enlaceIMG es True
                                    break;
                                }
                                case SLASH: { //En caso de encontar un "/" transita al estado 6 y desapila
                                    estado = 5;
                                    pila.pop();
                                    break;
                                }
                            }
                            break;
                        }
                        case 2: { //estado 2
                            switch (miToken.getTipo()) {
                                case PALABRA: {
                                    estado = 3;
                                    if (enlaceA && miToken.getValor().equalsIgnoreCase("href")) {
                                        enlaceHREF = true;
                                    } //Si la palabra que sigue es "href" es un hiperenlace, por lo tanto enlaceA es True
                                    if (enlaceIMG && miToken.getValor().equalsIgnoreCase("src")) {
                                        enlaceSRC = true;
                                    }//Si la palabra que sigue es "src" es una imagen, por lo tanto enlaceIMG es True
                                    break;
                                }
                                case SLASH: {
                                    estado = 6;
                                    pila.pop();
                                    break;
                                } //En caso de encontrar un SLASH desapila
                                case CLOSE: {
                                    estado = 0;
                                    break;
                                }
                            }
                            break;
                        }
                        case 3: { //estado 3
                            switch (miToken.getTipo()) {
                                case IGUAL: {
                                    estado = 4;
                                    break;
                                }
                                case PALABRA: {
                                    estado = 3;
                                    if (enlaceA && miToken.getValor().equalsIgnoreCase("href")) {
                                        enlaceHREF = true;
                                    } //Si la palabra que sigue es "href" es un hiperenlace, por lo tanto enlaceA es True
                                    if (enlaceIMG && miToken.getValor().equalsIgnoreCase("src")) {
                                        enlaceSRC = true;
                                    }//Si la palabra que sigue es "src" es una imagen, por lo tanto enlaceIMG es True
                                    break;
                                }
                                case SLASH: {
                                    estado = 6;
                                    pila.pop();
                                    break;
                                } //En caso de encontrar un SLASH desapila
                                case CLOSE: {
                                    estado = 0;
                                    break;
                                }
                            }

                            break;
                        }

                        case 4: { //estado 4
                            if (miToken.getTipo() == Tipo.VALOR) {
                                estado = 2;
                                if (enlaceHREF) {
                                    hiperenlace.add(miToken.getValor());
                                    enlaceA = false;
                                    enlaceHREF = false;
                                } //Si enlaceA es True, obtiene el valor, lo guarda en hiperenlace y cambia enlaceA a false
                                if (enlaceSRC) {
                                    imagenes.add(miToken.getValor());
                                    enlaceIMG = false;
                                    enlaceSRC = false;
                                } //Si enlaceIMG es True, obtiene el valor, lo guarda en imagenes y cambia enlaceA a false
                            } else {
                                estado = 2;
                            }
                            break;
                        }


                        case 5: { //estado 5
                            if (miToken.getTipo() == Tipo.PALABRA) {
                                estado = 6;
                            } else {

                            }
                            break;
                        }

                        case 6: { //estado 6
                            if (miToken.getTipo() == Tipo.CLOSE) {
                                estado = 0;
                            }
                            break;
                        }
                    }
                } else {
                    a = false;
                }
            } catch (Exception e) {

            }

        }
    }

    /**
     * Este método devuelve los hiperenlaces obtenidos por el autómata.
     */
    public ArrayList<String> obtenerHiperenlaces() {
        return hiperenlace;
    }

    /**
     * Este método devuelve los enlaces de las imágenes obtenidos por el
     * autómata.
     */
    public ArrayList<String> obtenerHiperenlacesImagenes() {
        return imagenes;
    }

    /**
     * Este método comprueba si la pila está vacía.
     */
    public boolean esDocumentoHTMLBienBalanceado() {
        if (pila.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
