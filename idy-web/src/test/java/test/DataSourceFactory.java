package test;

import java.util.List;

public interface DataSourceFactory {
	//抽象工厂
	List<Object> loadDataSource(DateSource ds);
}

class JsonDataSourceFactory implements DataSourceFactory {

	@Override
	public List<Object> loadDataSource(DateSource ds) {
		return loadFromJsonDataSource(ds);
	}
	
	public List<Object> loadFromJsonDataSource(DateSource ds){
		return null;
	}
}

class XmlDataSourceFactory implements DataSourceFactory {

	@Override
	public List<Object> loadDataSource(DateSource ds) {
		return loadFromXmlDataSource(ds);
	}
	
	public List<Object> loadFromXmlDataSource(DateSource ds){
		return null;
	}
}

class DateSource {
	
}
