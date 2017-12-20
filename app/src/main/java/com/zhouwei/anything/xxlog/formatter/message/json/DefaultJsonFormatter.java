
package com.zhouwei.anything.xxlog.formatter.message.json;


import com.zhouwei.anything.xxlog.formatter.FormatException;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Simply format the JSON using {@link JSONObject} and {@link JSONArray}.
 */
public class DefaultJsonFormatter implements JsonFormatter {

  private static final int JSON_INDENT = 4;

  @Override
  public String format(String json) {
    String formattedString = null;
    if (json == null || json.trim().length() == 0) {
      throw new FormatException("JSON empty.");
    }
    try {
      if (json.startsWith("{")) {
        JSONObject jsonObject = new JSONObject(json);
        formattedString = jsonObject.toString(JSON_INDENT);
      } else if (json.startsWith("[")) {
        JSONArray jsonArray = new JSONArray(json);
        formattedString = jsonArray.toString(JSON_INDENT);
      } else {
        throw new FormatException("JSON should start with { or [, but found " + json);
      }
    } catch (Exception e) {
      throw new FormatException("Parse JSON error. JSON string:" + json, e);
    }
    return formattedString;
  }
}
