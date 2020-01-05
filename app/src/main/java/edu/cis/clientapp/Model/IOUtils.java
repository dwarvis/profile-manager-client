package edu.cis.clientapp.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.cis.clientapp.Model.IORuntimeException;

public class IOUtils
{

    public static String readEntireStream(InputStream stream)
    {
        try
        {
            BufferedReader breader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder text = new StringBuilder();
            while (true)
            {
                int b = breader.read();
                if (b < 0)
                {
                    break;
                }
                text.append((char) b);
            }
            breader.close();
            stream.close();
            return text.toString();
        } catch (IOException ioe)
        {
            throw new IORuntimeException(ioe);
        }
    }
}
