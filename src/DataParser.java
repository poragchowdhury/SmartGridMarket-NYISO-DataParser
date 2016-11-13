import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class DataParser {
	public static void main(String [] args) throws IOException{
		
		File folder = new File("pricedata");
		File[] listOfFiles = folder.listFiles();
		
		FileWriter fwOutputPrices = new FileWriter("prices.csv");
		PrintWriter pwOutputPrices = new PrintWriter(new BufferedWriter(fwOutputPrices));
		for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	System.out.println("File " + listOfFiles[i].getName());
	        parseZonePriceDataToFile("pricedata/"+listOfFiles[i].getName(), pwOutputPrices);
	      }
	    }
		pwOutputPrices.close();
		fwOutputPrices.close();
		
		
		folder = new File("loaddata");
		File[] loadListOfFiles = folder.listFiles();
		
		FileWriter fwOutputLoads = new FileWriter("loads.csv");
		PrintWriter pwOutputLoads = new PrintWriter(new BufferedWriter(fwOutputLoads));
		for (int i = 0; i < loadListOfFiles.length; i++) {
	      if (loadListOfFiles[i].isFile()) {
	        System.out.println("File " + loadListOfFiles[i].getName());
	        parseZoneLoadDataToFile("loaddata/" + loadListOfFiles[i].getName(), pwOutputLoads);
	      }
	    }
		pwOutputPrices.close();
		fwOutputPrices.close();
		
		pwOutputLoads.close();
		fwOutputLoads.close();
	}
	
	/* Zonal Load Commitment */
	public static void parseZoneLoadDataToFile(String inputFile, PrintWriter pwOutputLoads){
        try
        {
            File gFile = new File(inputFile);
            if(!gFile.exists()){
                System.out.println("File doesn't exist");
            	return;
            }
            
			CSVParser parser = CSVParser.parse(gFile, StandardCharsets.US_ASCII, CSVFormat.DEFAULT);
            for (CSVRecord csvRecord : parser) {
                Iterator<String> itr = csvRecord.iterator();
                // Time Stamp	
                String strTimeStamp = itr.next();
                // Time Zone	
                String strTimeZone = itr.next();
                // Name
                String strName = itr.next();
                // PTID	
                String strPTID = itr.next();
                // LBMP ($/MWHr)
                String strEnergyBidLoad = itr.next();
                
                String subHour = strTimeStamp.substring(Math.max(strTimeStamp.length() - 5, 0));
                
                if(strName.equalsIgnoreCase("N.Y.C.") && subHour.equalsIgnoreCase("00:00")){
                	System.out.println(strTimeStamp + " " + strName + " " + strPTID + " " + strEnergyBidLoad);
                	pwOutputLoads.println(strTimeStamp + "," + strName + "," + strEnergyBidLoad);
        		}
            }
            parser.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}

	/* Real Time Market Price Data */
	public static void parseZonePriceDataToFile(String inputFile, PrintWriter pwOutputPrices){
        try
        {
            File gFile = new File(inputFile);
            if(!gFile.exists()){
                System.out.println("File doesn't exist");
            	return;
            }
            
			CSVParser parser = CSVParser.parse(gFile, StandardCharsets.US_ASCII, CSVFormat.DEFAULT);
            for (CSVRecord csvRecord : parser) {
                Iterator<String> itr = csvRecord.iterator();
                // Time Stamp	
                String strTimeStamp = itr.next();
                // Name
                String strName = itr.next();
                // PTID	
                String strPTID = itr.next();
                // LBMP ($/MWHr)
                String strLBMP = itr.next();
                
                String subHour = strTimeStamp.substring(Math.max(strTimeStamp.length() - 5, 0));
                
                if(strName.equalsIgnoreCase("N.Y.C.") && subHour.equalsIgnoreCase("00:00")){
                	//System.out.println(strTimeStamp + " " + strName + " " + strPTID + " " + strLBMP);
                	pwOutputPrices.println(strTimeStamp + "," + strName + "," + strLBMP);
        		}
            }
            parser.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
	}
	
}
