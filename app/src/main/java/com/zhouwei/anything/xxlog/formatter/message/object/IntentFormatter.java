
package com.zhouwei.anything.xxlog.formatter.message.object;

import android.content.Intent;

import com.zhouwei.anything.xxlog.internal.util.ObjectToStringUtil;


/**
 * Format an Intent object to a string.
 *
 * @since 1.4.0
 */
public class IntentFormatter implements ObjectFormatter<Intent> {

  /**
   * Format an Intent object to a string.
   *
   * @param data the Intent object to format
   * @return the formatted string
   */
  @Override
  public String format(Intent data) {
    return ObjectToStringUtil.intentToString(data);
  }
}
