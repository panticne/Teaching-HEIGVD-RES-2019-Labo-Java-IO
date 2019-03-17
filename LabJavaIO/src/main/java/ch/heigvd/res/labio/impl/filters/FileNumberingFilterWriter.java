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

    private int numberLine;
    private boolean end;
    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

    public FileNumberingFilterWriter(Writer out) {
        super(out);
        end = false;
        numberLine = 1;
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        this.write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = 0; i < len; ++i) {
            write(cbuf[off + i]);
        }
    }

    //Help by Samuel Mettler
    @Override
    public void write(int c) throws IOException {

        char br = '\r';
        char bn = '\n';

        if (numberLine == 1) {
            super.write(String.valueOf(numberLine++) + '\t');
            super.write(c);
        } else if ((char) c == bn) {
            end = false;
            super.write(c);
            super.write( String.valueOf(numberLine++) + '\t');

        } else if ((char) c == br) {
            end = true;
            super.write(c);
        } else {
            if (end) {
                end = false;
                super.write(String.valueOf(numberLine++) + '\t');
            }
            super.write(c);
        }
    }
}