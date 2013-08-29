package ucar.ral.gis.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.util.StringUtils;

//@Ignore
public class DataFileTest {

	@Test
	public void testFindDataFile() {
		
		
//		
		
		List<String> test = new ArrayList<String>();
		
		for (int i = 0; i < 3; i++) {
			
			test.add("Test" + i);
		}
		
		String value = StringUtils.collectionToDelimitedString(test, " AND ", "", "");
		
//		DataFileFactory dataFile = new DataFileFactory();
//		
//		File result = dataFile.findDataFile();
//		
//		assertThat(result, notNullValue());
		
	}

}
