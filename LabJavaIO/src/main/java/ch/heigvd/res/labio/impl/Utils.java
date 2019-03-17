package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    String[] res = new String[2];

    //On parcourt tout
    for(int i = 0 ; i < lines.length() ; ++i){
      //Si la ligne se termine par \r on va vérifier si il est suivi de \n afin de prendre le bon nombre de char(décaler
      //de 1 ou de 2 char)
      if(lines.charAt(i)=='\r'){
        //Si en plus de \r on a \n on décale de 2 chars
        if(i+1 < lines.length()&& lines.charAt(i+1)=='\n'){
          res[0] = lines.substring(0,i+2);
          res[1] = lines.substring(i+2, lines.length());
          break;
          //Sinon on décale que pour \r
        }else{
          res[0] = lines.substring(0,i+1);
          res[1] = lines.substring(i+1, lines.length());
          break;
        }
      //Si le test précédent à raté, on vérifie si il y a juste \n
      }else if(lines.charAt(i)=='\n'){
        res[0] = lines.substring(0, i+1);
        res[1] = lines.substring(i+1, lines.length());
        break;
      //Si il n'y a rien
      }else{
        res[0]="";
        res[1] = lines;
      }
    }
    return res;
  }
}


