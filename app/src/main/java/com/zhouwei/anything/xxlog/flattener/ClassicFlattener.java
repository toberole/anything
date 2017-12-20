package com.zhouwei.anything.xxlog.flattener;

public class ClassicFlattener extends PatternFlattener {

  private static final String DEFAULT_PATTERN = "{d} {l}/{t}: {m}";

  public ClassicFlattener() {
    super(DEFAULT_PATTERN);
  }
}
