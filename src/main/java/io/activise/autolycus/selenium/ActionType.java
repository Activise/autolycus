package io.activise.autolycus.selenium;

public enum ActionType {
  BY_SIMPLE_CLASSNAME, BY_XPATH, CLICK_ROOT, CLICK_TARGET;

  public static ActionType determine(String action) {
    if (action.equals("#!clickRoot")) {
      return CLICK_ROOT;
    } else if (action.startsWith("#!click")) {
      return CLICK_TARGET;
    } else if (action.startsWith("./") || action.startsWith("/") || action.startsWith("(/") || action.startsWith(".(/")) {
      return BY_XPATH;
    } else {
      return BY_SIMPLE_CLASSNAME;
    }
  }

}
