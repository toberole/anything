
package com.zhouwei.anything.xxlog.formatter.border;


import com.zhouwei.anything.xxlog.formatter.Formatter;

/**
 * The border formatter used to wrap string segments with borders when logging.
 * <p>
 * e.g:
 * <br>
 * <br>╔════════════════════════════════════════════════════════════════════════════
 * <br>║Thread: main
 * <br>╟────────────────────────────────────────────────────────────────────────────
 * <br>║	├ com.elvishew.xlog.SampleClassB.sampleMethodB(SampleClassB.java:100)
 * <br>║	└ com.elvishew.xlog.SampleClassA.sampleMethodA(SampleClassA.java:50)
 * <br>╟────────────────────────────────────────────────────────────────────────────
 * <br>║Hear is a simple message
 * <br>╚════════════════════════════════════════════════════════════════════════════
 */
public interface BorderFormatter extends Formatter<String[]> {
}
