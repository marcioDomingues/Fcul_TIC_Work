package persistence.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import model.EMediumAttribute;
import model.EMediumProperties;

import com.google.gson.Gson;

@Converter
public class EMediumPropertiesConverter implements AttributeConverter<EMediumProperties, String> {

	@Override
	public String convertToDatabaseColumn(EMediumProperties attr) {
		String props = new Gson().toJson(attr, EMediumProperties.class);
		return props;
	}

	@Override
	public EMediumProperties convertToEntityAttribute(String s) {
		EMediumProperties props = new Gson().fromJson(s, EMediumProperties.class);
		return fixLicencesToInt(props);
	}

	private EMediumProperties fixLicencesToInt(EMediumProperties props) {
		Object value = props.getAttribute(EMediumAttribute.LICENSES);
		if (value instanceof Double)
			props.addAttribute(EMediumAttribute.LICENSES, ((Double)value).intValue());
		return props;
	}
}
