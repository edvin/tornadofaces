package io.tornadofaces.site.docs;

import lombok.Getter;
import sun.misc.IOUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Model
public class IconDescriptor {
	Pattern IconPattern = Pattern.compile("\\.(icon-[\\w|-]+):before");
	@Getter List<String> icons;
	@Inject HttpServletRequest request;

	@PostConstruct
	public void init() {
		try (InputStream input = request.getServletContext().getResourceAsStream("/resources/tornadofaces-tornado/components/_icons.scss")) {
			byte[] data = IOUtils.readFully(input, -1, false);
			String css = new String(data);
			Matcher m = IconPattern.matcher(css);
			icons = new ArrayList<>();
			while (m.find())
				icons.add(m.group(1));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
