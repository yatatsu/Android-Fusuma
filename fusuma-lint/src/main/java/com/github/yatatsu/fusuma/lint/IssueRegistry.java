package com.github.yatatsu.fusuma.lint;

import com.android.tools.lint.detector.api.Issue;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public final class IssueRegistry extends com.android.tools.lint.client.api.IssueRegistry {
  @Override public List<Issue> getIssues() {
    return Arrays.asList(FusumaViolationDetector.getIssues());
  }
}
