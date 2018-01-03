/**
 * Copyright (c) 2016-present, RxJava Contributors.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.reactivex.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SchedulerSupport {
  /** A special value indicating the operator/class doesn't use schedulers. */
  String NONE = "none";
  /**
   * A special value indicating the operator/class requires a scheduler to be manually specified.
   */
  String CUSTOM = "custom";

  String COMPUTATION = "io.reactivex:computation";

  String IO = "io.reactivex:io";

  String NEW_THREAD = "io.reactivex:new-thread";

  String TRAMPOLINE = "io.reactivex:trampoline";

  String SINGLE = "io.reactivex:single";

  /**
   * The kind of scheduler the class or method uses.
   *
   * @return the name of the scheduler the class or method uses
   */
  String value();
}
