package gabriel.yuppiewall.marketdata.repository;

import gabriel.yuppiewall.marketdata.domain.StockDailySummary_;

import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HbaseStockDailySummaryRepository implements
		StockDailySummaryRepository {

	@Autowired
	private HbaseTemplate hbaseTemplate;

	private String tableName = "stockdailysummary";

	public static byte[] CF_PRICE = Bytes.toBytes("cfprice");
	public static byte[] CF_VOLUME = Bytes.toBytes("cfvolume");
	public static byte[] CF_DATE = Bytes.toBytes("cfdate");
	

	private byte[] qUser = Bytes.toBytes("user");
	private byte[] qEmail = Bytes.toBytes("email");
	private byte[] qPassword = Bytes.toBytes("password");

	

	

	@Override
	public void saveStockDailySummary(StockDailySummary_ tradingService) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveStockDailySummary(StockDailySummary_[] tradingService) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveStockDailySummary(String csvStockDailySummary) {
		// TODO Auto-generated method stub

	}

}
