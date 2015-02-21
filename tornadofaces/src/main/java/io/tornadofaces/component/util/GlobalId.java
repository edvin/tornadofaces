package io.tornadofaces.component.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GlobalId is a tag handler that lets you assign a truly global id that can be later looked
 * up and also refered to in render and execute statements.
 */
public class GlobalId extends TagHandler {
	private static final Pattern GlobalIdPattern = Pattern.compile("#(\\w+)"); 
	public GlobalId(TagConfig config) {
		super(config);
	}

	/**
	 * Put component owning the gid tag into the GlobalIds map for this UIViewRoot
	 * 
	 * @param ctx The current FacesContext
	 * @param parent The component owning the tag
	 * @throws IOException
	 */
	public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
		Map<String, UIComponent> gids = getGlobalIds(ctx.getFacesContext());
		gids.put(tag.getAttributes().get("value").getValue(), parent);
	}

	/**
	 * Get the map of Global ids for the current UIViewRoot in the supplied FacesContext 
	 * @param context The current FacesContext
	 * @return The map global Ids, possible empty, never null
	 */
	public static Map<String, UIComponent> getGlobalIds(FacesContext context) {
		Map<String, Object> attrs = context.getViewRoot().getAttributes();
		Map<String, UIComponent> lookup = (Map<String, UIComponent>) attrs.get("GlobalIds");
		if (lookup == null) {
			lookup = new HashMap<>();
			attrs.put("GlobalIds", lookup);
		}
		return lookup;
	}

	/**
	 * Resulve a component from the given FacesContext's UIViewRoot 
	 * @param context The current FacesContext
	 * @param gid The global id to resolve
	 * @return The component that was resolved, or null if no match was found
	 */
	public static UIComponent resolve(FacesContext context, String gid) {
		return getGlobalIds(context).get(gid);
	}

	/**
	 * Resolve any global ids in a space separated string of ids, typically
	 * like the one from render and execute attributes. Global ids are any 
	 * ids prepended with the hash character.
	 * * 
	 * @param context The current FacesContext
	 * @param ids The id string to resolve global ids in
	 * @return The agumented id string with the fully qualified ids
	 */
	public static String resolveIdString(FacesContext context, String ids) {
		if (ids == null || !ids.contains("#"))
			return ids;

		Matcher matcher = GlobalIdPattern.matcher(ids);
		StringBuffer sb = new StringBuffer();
		Map<String, UIComponent> globalIds = getGlobalIds(context);
		while (matcher.find()) {
			String gid = matcher.group(1);
			UIComponent component = globalIds.get(gid);
			if (component == null)
				throw new RuntimeException(
					String.format("Unable to locate component with global id %s from string %s", gid, ids));

			matcher.appendReplacement(sb, component.getClientId(context));
		}

		return sb.toString();
	}
}
