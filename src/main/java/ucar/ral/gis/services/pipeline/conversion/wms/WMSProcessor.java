package ucar.ral.gis.services.pipeline.conversion.wms;

import ucar.ral.gis.services.messages.ConversionRequestMessage;
import ucar.ral.gis.services.pipeline.Processor;

public class WMSProcessor implements Processor {
	
	/* Give the min / max values JSON */
	private static final String MINMAX_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/anomalies/downscaled/seasonal/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&item=minmax&request=GetMetadata&Layers=ppt&bbox=-124,24,66,49&SRS=ESPG:4326&CRS=CRS:84&width=850&height=500&TIME=2039-02-10T00:00:00.000Z";
	
	private static final String IMAGE_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/anomalies/downscaled/seasonal/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetMap&Layers=ppt&bbox=-124,24,-66,49&SRS=ESPG:4326&CRS=CRS:84&COLORSCALERANGE=-100,55&width=850&height=500&styles=BOXFILL/rainbow&format=image/png&TIME=2039-02-10T00:00:00.000Z";

	private static final String LEGEND_URL_TEMPLATE = "http://tds.gisclimatechange.ucar.edu/thredds/wms/anomalies/downscaled/seasonal/ppt_SRESB1_near_seasonal_down_anomaly.nc?" +
			"service=WMS&version=1.3.0&request=GetLegendGraphic&LAYER=ppt&LAYERS=ppt&COLORSCALERANGE=-100,55&PALETTE=rainbow";
	
	public void process(ConversionRequestMessage conversionRequest) {
		// TODO Auto-generated method stub

	}

}
