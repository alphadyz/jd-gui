/*
 * Copyright (c) 2008-2015 Emmanuel Dupuy
 * This program is made available under the terms of the GPLv3 License.
 */

package jd.gui.util.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class NewlineOutputStream extends FilterOutputStream {
    private static byte[] lineSeparator;

    public NewlineOutputStream(OutputStream os) {
        super(os);

        if (lineSeparator == null) {
            String s = System.getProperty("line.separator");

            if ((s == null) || (s.length() <= 0))
                s = "\n";

            lineSeparator = s.getBytes(Charset.forName("UTF-8"));
        }
    }

    public void write(int b) throws IOException {
        if (b == '\n') {
            out.write(lineSeparator);
        } else {
            out.write(b);
        }
    }

    public void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException {
        int first = 0;
        int i;

        for (i=0; i<len; i++) {
            if (b[i] == '\n') {
                out.write(b, first, i-first);
                out.write(lineSeparator);
                first = i+1;
            }
        }

        out.write(b, first, i-first);
    }
}
