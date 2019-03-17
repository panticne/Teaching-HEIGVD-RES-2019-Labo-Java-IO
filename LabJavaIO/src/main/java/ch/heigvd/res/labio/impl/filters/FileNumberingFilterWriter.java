package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;


/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private int compteur;
    private boolean isBr;
    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    public FileNumberingFilterWriter(Writer out) {
        super(out);
        isBr = false;
        compteur = 1;
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        if(off + len > str.length()){
            throw new IndexOutOfBoundsException("Longueur et offset ne sont pas corrects");
        }
        this.write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        if(off + len > cbuf.length){
            throw new IndexOutOfBoundsException("Longueur et offset ne sont pas corrects");
        }
        for (int i = 0; i < len; ++i) {
            write(cbuf[off + i]);
        }
    }

    @Override
    public void write(int c) throws IOException {

        char br = '\r';
        char bn = '\n';

        if (compteur == 1) {  //premiere ecriture
            super.write(String.valueOf(compteur++) + '\t');
            super.write(c);
        } else if ((char) c == bn) {
            isBr = false;
            super.write(c);
            super.write( String.valueOf(compteur++) + '\t');

        } else if ((char) c == br) {
            isBr = true;
            super.write(c);
        } else {
            if (isBr) {
                isBr = false;
                super.write(String.valueOf(compteur++) + '\t');
            }
            super.write(c);
        }
    }
}