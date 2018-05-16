package ucar.gis.netcdf.converter;

import java.io.File;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import edu.ucar.gis.ipcc.ConversionRequest;
import edu.ucar.gis.ipcc.converter.Converter;
import edu.ucar.gis.ipcc.model.netcdf2gis.transform.ShapeFileTransformer;
import edu.ucar.gis.ipcc.model.netcdf2gis.transform.Transformer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        
    	ConversionRequest conversionRequest = new ConversionRequestImpl();
    	CmdLineParser parser = new CmdLineParser(conversionRequest);
    	parser.setUsageWidth(80); // width of the error display area

    	try {
    		parser.parseArgument(args);
    	} catch (CmdLineException e) {
    		System.err.println(e.getMessage());
    		System.err.println("java -jar netcdf-converter.jar [options...] arguments...");
    		// print the list of available options
    		parser.printUsage(System.err);
    		System.err.println();
    		System.exit(1);
    	}
    	
    	File outputFile = new File("/home/wilhelmi/shape/test.shp");
    	
    	Transformer transformer = new ShapeFileTransformer();
    	
    	Converter converter = new Converter(conversionRequest, outputFile, transformer);
		
		converter.execute();
		
		System.out.println("Completed file conversion.");
    }
}
