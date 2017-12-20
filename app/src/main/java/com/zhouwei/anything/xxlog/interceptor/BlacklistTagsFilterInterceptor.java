

package com.zhouwei.anything.xxlog.interceptor;


import com.zhouwei.anything.xxlog.LogItem;

import java.util.Arrays;

/**
 * Filter out the logs with a tag that is in the blacklist.
 *
 * @since 1.3.0
 */
public class BlacklistTagsFilterInterceptor extends AbstractFilterInterceptor {

  private Iterable<String> blacklistTags;

  /**
   * Constructor
   *
   * @param blacklistTags the blacklist tags, the logs with a tag that is in the blacklist will be
   *                      filtered out
   */
  public BlacklistTagsFilterInterceptor(String... blacklistTags) {
    this(Arrays.asList(blacklistTags));
  }

  /**
   * Constructor
   *
   * @param blacklistTags the blacklist tags, the logs with a tag that is in the blacklist will be
   *                      filtered out
   */
  public BlacklistTagsFilterInterceptor(Iterable<String> blacklistTags) {
    if (blacklistTags == null) {
      throw new NullPointerException();
    }
    this.blacklistTags = blacklistTags;
  }

  /**
   * {@inheritDoc}
   *
   * @return true if the tag of the log is in the blacklist, false otherwise
   */
  @Override
  protected boolean reject(LogItem log) {
    if (blacklistTags != null) {
      for (String disabledTag : blacklistTags) {
        if (log.tag.equals(disabledTag)) {
          return true;
        }
      }
    }
    return false;
  }
}
