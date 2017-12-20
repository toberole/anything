
package com.zhouwei.anything.xxlog.interceptor;


import com.zhouwei.anything.xxlog.LogItem;

import java.util.Arrays;

/**
 * Filter out the logs with a tag that is NOT in the whitelist.
 *
 */
public class WhitelistTagsFilterInterceptor extends AbstractFilterInterceptor {

  private Iterable<String> whitelistTags;

  /**
   * Constructor
   *
   * @param whitelistTags the whitelist tags, the logs with a tag that is NOT in the whitelist
   *                      will be filtered out
   */
  public WhitelistTagsFilterInterceptor(String... whitelistTags) {
    this(Arrays.asList(whitelistTags));
  }

  /**
   * Constructor
   *
   * @param whitelistTags the whitelist tags, the logs with a tag that is NOT in the whitelist
   *                      will be filtered out
   */
  public WhitelistTagsFilterInterceptor(Iterable<String> whitelistTags) {
    if (whitelistTags == null) {
      throw new NullPointerException();
    }
    this.whitelistTags = whitelistTags;
  }

  /**
   * {@inheritDoc}
   *
   * @return true if the tag of the log is NOT in the whitelist, false otherwise
   */
  @Override
  protected boolean reject(LogItem log) {
    if (whitelistTags != null) {
      for (String enabledTag : whitelistTags) {
        if (log.tag.equals(enabledTag)) {
          return false;
        }
      }
    }
    return true;
  }
}
