package com.github.yatatsu.fusuma.lint;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiType;
import java.util.Collections;
import java.util.List;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.visitor.AbstractUastVisitor;

public final class FusumaViolationDetector extends Detector implements Detector.UastScanner {

  static Issue[] getIssues() {
    return new Issue[] { ISSUE_CALL_NOT_OPEN_CLASS };
  }

  public static final Issue ISSUE_CALL_NOT_OPEN_CLASS = Issue.create("CallFusuma",
      "Calling class annotated with Fusuma without any opening condition",
      "To avoid this violation, you consider use `@OpenFusuma` annotation to your method.",//TODO
      Category.CORRECTNESS, 5, Severity.ERROR,
      new Implementation(FusumaViolationDetector.class, Scope.JAVA_FILE_SCOPE));

  @Override public List<Class<? extends UElement>> getApplicableUastTypes() {
    return Collections.singletonList(UCallExpression.class);
  }

  @Override public UElementHandler createUastHandler(JavaContext context) {
    return new UElementHandler() {

      @Override public void visitCallExpression(UCallExpression uCallExpression) {
        uCallExpression.accept(new FusumaDetector(context));
      }
    };
  }

  private static class FusumaDetector extends AbstractUastVisitor {
    private final JavaContext context;

    FusumaDetector(JavaContext context) {
      this.context = context;
    }

    @Override public boolean visitCallExpression(UCallExpression node) {
      detectFusumaCall(context, node);
      return super.visitCallExpression(node);
    }

    private static void detectFusumaCall(JavaContext context, UCallExpression node) {
      PsiType receiverType = node.getReceiverType();
      if (receiverType != null) {
        //for (PsiAnnotation ann : receiverType.getAnnotations()) {
        //  context.report(ISSUE_CALL_NOT_OPEN_CLASS, node, context.getLocation(node),
        //      "Illegal call with @Fusuma annotation.");
        //  return;
        //}
        PsiAnnotation annotation =
            receiverType.findAnnotation("com.github.yatatsu.fusuma.annotation.Fusuma");
        if (annotation != null) {
          context.report(ISSUE_CALL_NOT_OPEN_CLASS, node, context.getLocation(node),
              "Illegal call with @Fusuma annotation.");
        }
        //context.report(ISSUE_CALL_NOT_OPEN_CLASS, node, context.getLocation(node),
        //    "Illegal call with @Fusuma annotation.");
      }
    }
  }
}
