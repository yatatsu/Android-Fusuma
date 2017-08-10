package com.github.yatatsu.fusuma.lint;

import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

public final class FusumaViolationDetector extends Detector implements Detector.JavaPsiScanner {

  static Issue[] getIssues() {
    return new Issue[] { ISSUE_CALL_NOT_OPEN_CLASS };
  }

  public static final Issue ISSUE_CALL_NOT_OPEN_CLASS =
      Issue.create("CallFusuma",
          "Calling class annotated with Fusuma without any opening condition",
          "To avoid this violation, you consider use `@OpenFusuma` annotation to your method.",//TODO
          Category.CORRECTNESS,
          5,
          Severity.ERROR,
          new Implementation(FusumaViolationDetector.class, Scope.JAVA_FILE_SCOPE));
}
