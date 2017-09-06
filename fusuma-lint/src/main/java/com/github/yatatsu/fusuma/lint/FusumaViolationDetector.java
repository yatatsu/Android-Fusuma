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
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.visitor.AbstractUastVisitor;

public final class FusumaViolationDetector extends Detector implements Detector.UastScanner {

  static Issue[] getIssues() {
    return new Issue[] { ISSUE };
  }

  private static final String FUSUMA_ANNOTATION_NAME = "com.github.yatatsu.fusuma.annotation.Fusuma";
  private static final String FUSUMA_OPEN_IF_ATTRIBUTE = "openIf";

  private static final String ISSUE_ID = "CallFusuma";
  private static final String LINT_ERROR_TITLE = "Illegal call with @Fusuma annotation.";
  private static final String LINT_ERROR_BODY = "Annotated @Fusuma means that should not be called.";

  static final Issue ISSUE = Issue.create(ISSUE_ID, LINT_ERROR_TITLE,
      LINT_ERROR_BODY, Category.CORRECTNESS, 5, Severity.ERROR,
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
      PsiMethod method = node.resolve();
      if (method != null
          && method.getContainingClass() != null
          && method.getContainingClass().getModifierList() != null) {
        PsiModifierList modifierList = method.getContainingClass().getModifierList();
        // check annotated with @Fusuma
        PsiAnnotation annotation =
            modifierList.findAnnotation(FUSUMA_ANNOTATION_NAME);
        if (annotation != null) {
          // check enabled or is suppressed with comment
          if (!context.isEnabled(ISSUE) || context.isSuppressedWithComment(node, ISSUE)) {
            return;
          }
          // check openIf option
          PsiAnnotationMemberValue openIf = annotation.findAttributeValue(FUSUMA_OPEN_IF_ATTRIBUTE);
          if (openIf != null && openIf.getText().equals("true")) {
            return;
          }
          context.report(ISSUE, node, context.getLocation(node), LINT_ERROR_BODY);
        }

      }
    }
  }
}
