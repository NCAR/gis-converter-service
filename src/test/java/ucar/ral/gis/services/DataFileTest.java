package ucar.ral.gis.services;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;

import org.junit.Test;

public class DataFileTest {

	@Test
	public void testFindDataFile() {
		
		DataFile dataFile = new DataFile();
		
		File result = dataFile.findDataFile();
		
		assertThat(result, notNullValue());
		
	}

}
